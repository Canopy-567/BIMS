package com.example.bims;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class TaskletConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean(name="BIMSTasklet")
    Tasklet tasklet() {
        return new BIMSTasklet();
    }

    @Bean
    protected Step readLines() {
        return steps
                .get("readLines")
                .tasklet(tasklet())
                .build();
    }



    @Bean
    public Job job() {
        return jobs
                .get("taskletsJob")
                .start(readLines())
                .build();
    }

}