package com.discorp.physicalinventory.server;

import org.jwebsocket.api.PluginConfiguration;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.kit.PlugInResponse;
import org.jwebsocket.logging.Logging;
import org.jwebsocket.plugins.TokenPlugIn;
import org.jwebsocket.token.Token;

/**
 * User: luult
 * Date: 2/28/14
 */
public class Equipment extends TokenPlugIn
{
    private static org.apache.log4j.Logger log = Logging.getLogger(Audit.class);

    private final static String NAME_SPACE = "com.discorp.physicalInventory.equipment";

    public Equipment(PluginConfiguration aConfiguration)
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
            if (lType.equals("addNote"))
            {
                addNote(connector, token);
            }
            else if (lType.equals("addHours"))
            {
                addHours(connector, token);
            }
            else if (lType.equals("addUnknown"))
            {
                addUnknown(connector, token);
            }
            else if (lType.equals("scan"))
            {
                scan(connector, token);
            }
            else if (lType.equals("markOff"))
            {
                markOff(connector, token);
            }
            else if (lType.equals("unMarkOff"))
            {
                unMarkOff(connector, token);
            }
        }
    }

    private void addNote(WebSocketConnector aConnector, Token aToken)
    {

    }

    private void addHours(WebSocketConnector aConnector, Token aToken)
    {

    }

    private void addUnknown(WebSocketConnector connector, Token token)
    {

    }

    private void scan(WebSocketConnector connector, Token token)
    {

    }


    private void markOff(WebSocketConnector connector, Token token)
    {

    }

    private void unMarkOff(WebSocketConnector connector, Token token)
    {

    }
}
