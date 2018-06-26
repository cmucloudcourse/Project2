package edu.cmu.cs.cloud.samples.aws.jobs;


import edu.cmu.cs.cloud.samples.aws.log.LogMonitor;
import org.quartz.*;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class LogMonitoringJob {

    private static final int DURATION = 60;

    public LogMonitoringJob() {
    }

    public static void startMonitoring(String lgDNSName, String testID, int repeatCount) throws SchedulerException {
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

        Scheduler sched = schedFact.getScheduler();

        sched.start();

        // define the job and tie it to our HelloJob class
        JobDetail job = newJob(LogMonitor.class)
                .withIdentity("myJob", "group1")
                .usingJobData("lgDNSName", lgDNSName)
                .usingJobData("testID", testID)
                .build();

        // Trigger the job to run now, and then every 40 seconds
        Trigger trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(DURATION)
                        .withRepeatCount(48))
                .build();

        // Tell quartz to schedule the job using our trigger
        sched.scheduleJob(job, trigger);

    }

}


