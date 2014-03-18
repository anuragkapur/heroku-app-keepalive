package com.anuragkapur.heroku.keepalive;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
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

        JobDetail jobDetail = JobBuilder.newJob(HttpRequestJob.class).build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .startNow()
                .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(10))
                .build();

        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            LOGGER.error("SchedulerException {}", e);
        }
    }
}
