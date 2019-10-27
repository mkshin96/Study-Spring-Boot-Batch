package com.mugon.batch.jobs.inactive.listener;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InactiveStepListener {

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        log.info("Before Step");
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution) {
        log.info("After Step");
    }
}
