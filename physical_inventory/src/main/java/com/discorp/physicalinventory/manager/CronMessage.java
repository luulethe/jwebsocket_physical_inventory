package com.discorp.physicalinventory.manager;

import com.discorp.physicalinventory.config.Constant;
import com.discorp.physicalinventory.entity.Message;
import com.discorp.physicalinventory.server.BaseTokenPlugIn;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * User: luult
 * Date: 3/14/14
 */
public class CronMessage implements Job
{
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException
    {
        List<Message> allMessage = ManageMessage.getAllMessages();
        Long currentTime = System.currentTimeMillis();
        for (Message message : allMessage)
        {
            System.out.println("resending....");
            System.out.println(message.getToken());
            if (message.getNumberSent() == Constant.NUMBER_RESEND_MAX)
            {
                disconnect(message);
                ManageMessage.remove(message);
                System.out.println("disconnect this connector because number of sent exceeds 3");
            }
            if (message.getSentTime() + Constant.RESEND_TIME > currentTime)
            {
                resend(message);
            }
            else
            {
                break;
            }
        }
    }

    private void disconnect(Message message)
    {

    }

    private void resend(Message message)
    {
        BaseTokenPlugIn baseTokenPlugIn = message.getBaseTokenPlugIn();
        baseTokenPlugIn.sendToken(message.getWebSocketConnector(), message.getToken());
    }
}
