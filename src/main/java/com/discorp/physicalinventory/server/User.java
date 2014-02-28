package com.discorp.physicalinventory.server;

import org.jwebsocket.api.WebSocketConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: luult
 * Date: 2/28/14
 */
public class User
{
    private static HashMap<Long, List<WebSocketConnector>> userMap = new HashMap<Long, List<WebSocketConnector>>();

    public static void addUserToAuditSession(Long auditHistoryId, WebSocketConnector connector)
    {
        if (userMap.get(auditHistoryId) != null)
        {
            userMap.get(auditHistoryId).add(connector);
        }
        else
        {
            List<WebSocketConnector> webSocketConnectorList = new ArrayList<WebSocketConnector>();
            webSocketConnectorList.add(connector);
            userMap.put(auditHistoryId, webSocketConnectorList);
        }
    }

    public static void removeUserFromAuditSession(Long auditHistoryId, WebSocketConnector connector) throws JWebSocketException
    {
        List<WebSocketConnector> webSocketConnectorList = userMap.get(auditHistoryId);
        if (webSocketConnectorList == null)
        {
            throw new JWebSocketException("Error logical");
        }
        webSocketConnectorList.remove(connector);
        if (webSocketConnectorList.isEmpty())
        {
            userMap.remove(auditHistoryId);
        }
    }

    public static List<WebSocketConnector> getUsersInAnAuditSession(Long auditHistoryId)
    {
        return userMap.get(auditHistoryId);
    }
}
