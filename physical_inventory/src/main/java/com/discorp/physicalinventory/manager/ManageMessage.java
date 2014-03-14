package com.discorp.physicalinventory.manager;

import com.discorp.physicalinventory.entity.Message;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * User: luult
 * Date: 3/13/14
 */
public class ManageMessage
{
    private static HashMap<Integer, Message> messages = new HashMap<Integer, Message>();
    private static List<Message> allMessage = new ArrayList<Message>();
    public static List<Message> getAllMessages()
    {
        return allMessage;
    }
    private static boolean isCreatedCronJob = false;

    public static boolean isCreatedCronJob()
    {
        return isCreatedCronJob;
    }

    public static void setCreatedCronJob(boolean isCreatedCronJob)
    {
        isCreatedCronJob = isCreatedCronJob;
    }

    public static void add(Message message)
    {
        messages.put((Integer) (message.getToken().getMap().get("utid")), message);
        allMessage.add(message);
    }

    public static void remove(Integer utid)
    {
        Message message = messages.remove(utid);
        allMessage.remove(message);
    }
    public static void remove(Message message)
    {
        Integer utid = (Integer) message.getToken().getMap().get("utid");
        remove(utid);
    }
    public static Message get(Integer utid)
    {
        return messages.get(utid);
    }
    public static void createCronJob() throws SchedulerException
    {
        JobDetail job = new JobDetail();
        job.setName("resend message");
        job.setJobClass(CronMessage.class);

        //configure the scheduler time
        SimpleTrigger trigger = new SimpleTrigger();
        trigger.setName("dummyTriggerName");
        trigger.setStartTime(new Date(System.currentTimeMillis() + 3000));
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        trigger.setRepeatInterval(3000);

        //schedule it
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
        isCreatedCronJob = true;
    }
}
