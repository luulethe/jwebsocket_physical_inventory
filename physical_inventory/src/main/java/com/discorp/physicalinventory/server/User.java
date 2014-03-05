package com.discorp.physicalinventory.server;

import org.jwebsocket.api.WebSocketConnector;
import org.springframework.data.domain.Pageable;

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
        System.out.println("adding " + connector.generateUID());
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
        printAllConnectors();
    }

    public static void removeUserFromAuditSession(Long auditHistoryId, WebSocketConnector connector) throws JWebSocketException
    {
        System.out.println("bb1");
        List<WebSocketConnector> webSocketConnectorList = userMap.get(auditHistoryId);
        System.out.println("bb2");
        printAllConnectors();
        if (webSocketConnectorList == null)
        {
            throw new JWebSocketException("Error logical");
        }
        System.out.println("bb3");
        webSocketConnectorList.remove(connector);
        if (webSocketConnectorList.isEmpty())
        {
            userMap.remove(auditHistoryId);
        }
        System.out.println("bb4");
    }

    public static List<WebSocketConnector> getUsersInAnAuditSession(Long auditHistoryId)
    {

        if (userMap.get(auditHistoryId) == null)
        {
            return new ArrayList<WebSocketConnector>();
        }
        else
        {
            return userMap.get(auditHistoryId);
        }
    }

    public static void printAllConnectors()
    {
        System.out.println("all connectors ... ");
        for (Long sessionId : userMap.keySet())
        {
            for (WebSocketConnector connector : userMap.get(sessionId))
                System.out.println(connector.generateUID());
        }
    }
}
