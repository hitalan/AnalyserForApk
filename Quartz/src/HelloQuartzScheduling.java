import java.sql.Date; 

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
public class HelloQuartzScheduling {
    public static void main(String[] args)throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobDetail jobDetail = new JobDetail ("helloQuartzJob", 
                Scheduler.DEFAULT_GROUP, HelloQuartzJob.class);
        SimpleTrigger simpleTrigger = new SimpleTrigger("simpleTrigger", 
                Scheduler.DEFAULT_GROUP);
        simpleTrigger.setStartTime(new Date(System.currentTimeMillis()));
        simpleTrigger.setRepeatInterval(600000);
        simpleTrigger.setRepeatCount(1000);
        scheduler.scheduleJob(jobDetail, simpleTrigger);
        scheduler.start();
    }
}