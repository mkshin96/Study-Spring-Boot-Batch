package com.mugon.batch.repository;

import com.mugon.batch.domain.User;
import com.mugon.batch.domain.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUpdatedDateBeforeAndStatusEquals(LocalDateTime localDateTime, UserStatus status);
}
