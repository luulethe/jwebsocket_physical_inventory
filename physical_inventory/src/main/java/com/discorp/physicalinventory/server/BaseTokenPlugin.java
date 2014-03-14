package com.discorp.physicalinventory.server;

import org.jwebsocket.api.PluginConfiguration;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.plugins.TokenPlugIn;
import org.jwebsocket.token.Token;

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

    @Override
    public void sendToken(WebSocketConnector connector, Token aToken)
    {
        aToken.setString("timeSend", System.currentTimeMillis() + "");
        System.out.println(aToken);
        super.sendToken(connector, aToken);

//        Integer utid = (Integer) aToken.getMap().get("utid");
//        if (ManageMessage.get(utid) == null)
//        {
//            Message message = new Message(connector, aToken, 0);
//            ManageMessage.add(message);
//        }
    }
}
