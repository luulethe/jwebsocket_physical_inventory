package com.discorp.physicalinventory.manager;

import com.discorp.physicalinventory.server.JWebSocketException;
import org.jwebsocket.api.PluginConfiguration;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.config.JWebSocketServerConstants;
import org.jwebsocket.kit.CloseReason;
import org.jwebsocket.plugins.ActionPlugIn;

/**
 * User: luult
 * Date: 3/14/14
 */
public class ManageConnectorPlugIn extends ActionPlugIn
{
    public static final String NS_CHANNELS = "com.discorp.physicalInventory.ManageConnectorPlugIn";

    public ManageConnectorPlugIn(PluginConfiguration aConfiguration)
    {
        super(aConfiguration);
        this.setNamespace(NS_CHANNELS);
    }

    @Override
    public void connectorStopped(WebSocketConnector aConnector, CloseReason aCloseReason)
    {
        super.connectorStopped(aConnector, aCloseReason);
        try
        {
            User.deleteConnector(aConnector);
        }
        catch (JWebSocketException e)
        {
            e.printStackTrace();
        }
    }

}
