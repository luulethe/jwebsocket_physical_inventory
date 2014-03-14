package com.discorp.physicalinventory.server;

import com.discorp.physicalinventory.manager.ManageMessage;
import org.jwebsocket.api.PluginConfiguration;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.kit.PlugInResponse;
import org.jwebsocket.token.Token;

/**
 * User: luult
 * Date: 3/14/14
 */
public class HandleResponse extends BaseTokenPlugIn
{
    private final static String NAME_SPACE = "com.discorp.physicalInventory.handleResponse";

    public HandleResponse(PluginConfiguration aConfiguration)
    {
        super(aConfiguration);
        this.setNamespace(NAME_SPACE);
    }
    @Override
    public void processToken(PlugInResponse response, WebSocketConnector connector, Token token)
    {
        String lType = token.getType();
        String lNS = token.getNS();
        System.out.println("starting process ...");
        System.out.println(token);
        if (lType != null && lNS != null && lNS.equals(getNamespace()))
        {
            if (lType.equals("ackResponse"))
            {
                Integer utid = token.getInteger("utidResponse");
                ManageMessage.remove(utid);
            }
        }
    }
}

