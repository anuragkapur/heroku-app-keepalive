package com.anuragkapur.heroku.keepalive;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: anuragkapur
 * @since: 06/03/2014
 */

public class SchedulerMain {

    final static Logger LOGGER = LoggerFactory.getLogger(SchedulerMain.class);

    public static void main(String[] args) {

        Scheduler scheduler;

        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .startNow()
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(2))
                .build();

        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public static class HelloJob implements Job {

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            LOGGER.info("HelloJob executed");
        }
    }
}
