package com.selab.springbootblueprints.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Configuration
public class TaskSchedulerConfig {

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setDaemon(true);
        threadPoolTaskScheduler.setPoolSize(3);
        threadPoolTaskScheduler.setThreadNamePrefix("task-scheduler-");
        threadPoolTaskScheduler.initialize();

        setSampleTask(threadPoolTaskScheduler);

        return threadPoolTaskScheduler;
    }

    private void setSampleTask(TaskScheduler scheduler) {
        // 'sec min hour dayOfMonth month dayOfWeek (year / optional)'
        String cronPattern = "0 0 * * * ?";

        ScheduledFuture scheduledFuture = scheduler.schedule(() -> {
            String logMessage = String.format("Task schedule sample time-signal log: %s o'clock", LocalDateTime.now().getHour());
            log.trace(logMessage);
        }, new CronTrigger(cronPattern));

        // if you want to stop this
//        boolean cancelSuccessFlag = scheduledFuture.cancel(false);
//        if (!cancelSuccessFlag) {
//            log.warn("scheduled task cancel fail: maybe already canceled");
//        }
    }
}
