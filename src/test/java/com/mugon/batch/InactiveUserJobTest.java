package com.mugon.batch;

import com.mugon.batch.domain.User;
import com.mugon.batch.domain.enums.UserStatus;
import com.mugon.batch.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
@Import(TestJobConfig.class)
@Slf4j
public class InactiveUserJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void 임포트_sql_실행_확인() {
        List<User> users = userRepository.findAll();
        assertEquals(11, users.size());
    }

    @Test
    public void 휴면_회원_전환_테스트() throws Exception {

        List<User> users = userRepository.findAll();
        assertEquals(11, users.size());
        int count = users.stream().filter(user -> user.getUpdatedDate().isAfter(LocalDateTime.now().minusYears(1))).collect(Collectors.toList()).size();
        assertEquals(0, count);

        Date nowDate = new Date();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(
                new JobParametersBuilder().addDate("nowDate", nowDate).toJobParameters()
        );

        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
        assertEquals(11, userRepository.findAll().size());
        assertEquals(0, userRepository.findByUpdatedDateBeforeAndStatusEquals(
                LocalDateTime.now().minusYears(1), UserStatus.ACTIVE).size());
    }
}
