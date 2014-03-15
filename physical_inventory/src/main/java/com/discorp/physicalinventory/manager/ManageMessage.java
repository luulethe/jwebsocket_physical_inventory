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
    private static List<Message> newMessageList = new ArrayList<Message>();
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
        messages.put((Integer) (message.getToken().getInteger("id")), message);
        allMessage.add(message);
    }

    public static void remove(Integer id)
    {
        Message message = messages.remove(id);
        allMessage.remove(message);
    }
    public static void remove(Message message)
    {
        Integer id = (Integer) message.getToken().getInteger("id");
        remove(id);
    }
    public static Message get(Integer id)
    {
        return messages.get(id);
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
        trigger.setRepeatInterval(4001);

        //schedule it
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
        isCreatedCronJob = true;
    }
}
