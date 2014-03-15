package com.discorp.physicalinventory.server;

import com.discorp.physicalinventory.entity.Message;
import com.discorp.physicalinventory.manager.ID;
import com.discorp.physicalinventory.manager.ManageMessage;
import org.jwebsocket.api.PluginConfiguration;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.api.WebSocketEngine;
import org.jwebsocket.plugins.TokenPlugIn;
import org.jwebsocket.token.Token;
import org.quartz.SchedulerException;

/**
 * User: luult
 * Date: 3/13/14
 */

public class BaseTokenPlugIn extends TokenPlugIn
{
    public BaseTokenPlugIn(PluginConfiguration aConfiguration)
    {
        super(aConfiguration);
    }

    public void sendTokenHasGenId(WebSocketConnector connector, Token aToken)
    {
        aToken.setInteger("id", ID.getNextTokenId());
        sendToken(connector, aToken);
    }
    @Override
    public void sendToken(WebSocketConnector connector, Token aToken)
    {
        Long currentTime = System.currentTimeMillis();
        aToken.setString("timeSend", currentTime + "");
        System.out.println(aToken);
        super.sendToken(connector, aToken);

        Integer id = aToken.getInteger("id");
        Message message = ManageMessage.get(id);
        if (message == null)
        {
            message = new Message(connector, aToken, 0, currentTime, this);
            ManageMessage.add(message);
        }
        else
        {
            message.increaseNumberSent();
            message.setSentTime(currentTime);
        }
    }

    @Override
    public void engineStarted(WebSocketEngine aEngine)
    {
        super.engineStarted(aEngine);
        try
        {
            if (!ManageMessage.isCreatedCronJob())
            {
                ManageMessage.createCronJob();
            }
        }
        catch (SchedulerException e)
        {
            e.printStackTrace();
        }
        System.out.println(aEngine);
        System.out.println("Engine started");
    }
}
