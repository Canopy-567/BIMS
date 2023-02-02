package com.example.bims;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BIMSJobLauncher {
    @Autowired
    Job job;

    @Autowired
    JobLauncher jobLauncher;

    @Scheduled(cron="0 0 0 1 * *")
    public void SchedulerJobRunner() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobInstanceAlreadyCompleteException, JobRestartException {
        jobLauncher.run(job, newExecution());
    }

    private JobParameters newExecution() {
        return new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
    }

}
