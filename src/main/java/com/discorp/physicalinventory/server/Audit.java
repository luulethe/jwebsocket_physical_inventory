package com.discorp.physicalinventory.server;

import org.jwebsocket.api.PluginConfiguration;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.kit.PlugInResponse;
import org.jwebsocket.logging.Logging;
import org.jwebsocket.plugins.TokenPlugIn;
import org.apache.log4j.Logger;
import org.jwebsocket.token.Token;


/**
 * User: luult
 * Date: 2/28/14
 */
public class Audit extends TokenPlugIn
{
    private static org.apache.log4j.Logger log = Logging.getLogger(Audit.class);

    private final static String NAME_SPACE = "com.discorp.physicalInventory.audit";

    public Audit(PluginConfiguration aConfiguration)
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
            if (lType.equals("start"))
            {
                startAudit(connector, token);
            }
            else if (lType.equals("end"))
            {
                endAudit(connector, token);
            }
        }
    }

    private void endAudit(WebSocketConnector aConnector, Token aToken)
    {

    }

    private void startAudit(WebSocketConnector aConnector, Token aToken)
    {

    }
}
