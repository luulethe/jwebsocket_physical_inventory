package com.discorp.physicalinventory.manager;

import com.discorp.physicalinventory.server.JWebSocketException;
import com.mongodb.util.Hash;
import org.jwebsocket.api.WebSocketConnector;
import org.springframework.data.domain.Pageable;

import java.util.*;

/**
 * User: luult
 * Date: 2/28/14
 */
public class User
{
    private static HashMap<Long, HashSet<WebSocketConnector>> userMap = new HashMap<Long, HashSet<WebSocketConnector>>();
    private static HashMap<WebSocketConnector, HashSet<Long>> connectorMap = new HashMap<WebSocketConnector, HashSet<Long>>();

    public static void addUserToAuditSession(Long auditHistoryId, WebSocketConnector connector)
    {
        System.out.println("adding " + connector.generateUID());
        if (userMap.get(auditHistoryId) != null)
        {
            userMap.get(auditHistoryId).add(connector);
        }
        else
        {
            HashSet<WebSocketConnector> webSocketConnectorList = new HashSet<WebSocketConnector>();
            webSocketConnectorList.add(connector);
            userMap.put(auditHistoryId, webSocketConnectorList);
        }

        if (connectorMap.get(connector) != null)
        {
            connectorMap.get(connector).add(auditHistoryId);
        }
        else
        {
            HashSet<Long> sessionIdList = new HashSet<Long>();
            sessionIdList.add(auditHistoryId);
            connectorMap.put(connector, sessionIdList);
        }
    }

    public static void removeConnectorInUserMap(Long auditHistoryId, WebSocketConnector connector) throws JWebSocketException
    {
        HashSet<WebSocketConnector> webSocketConnectorList = userMap.get(auditHistoryId);

        if (webSocketConnectorList == null)
        {
            throw new JWebSocketException("Error. Client has to start audit first. ");
        }
        webSocketConnectorList.remove(connector);
        if (webSocketConnectorList.isEmpty())
        {
            userMap.remove(auditHistoryId);
        }

    }

    public static void removeAuditHistoryIdForAConnector(Long auditHistoryId, WebSocketConnector connector) throws JWebSocketException
    {
        HashSet<Long> auditHistoryIdList = connectorMap.get(auditHistoryId);
        if (auditHistoryIdList == null)
        {
            throw new JWebSocketException("Error. Client has to start audit first. ");
        }
        auditHistoryIdList.remove(auditHistoryId);
        if (auditHistoryIdList.isEmpty())
        {
            connectorMap.remove(auditHistoryId);
        }

    }

    public static void removeUserFromAuditSession(Long auditHistoryId, WebSocketConnector connector) throws JWebSocketException
    {
        removeConnectorInUserMap(auditHistoryId, connector);
        removeAuditHistoryIdForAConnector(auditHistoryId, connector);
    }

    public static HashSet<WebSocketConnector> getUsersInAnAuditSession(Long auditHistoryId)
    {

        if (userMap.get(auditHistoryId) == null)
        {
            return new HashSet<WebSocketConnector>();
        }
        else
        {
            return userMap.get(auditHistoryId);
        }
    }

    public static void deleteConnector(WebSocketConnector connector) throws JWebSocketException
    {
        HashSet<Long> auditHistoryIdList = connectorMap.get(connector);
        for (Long sessionId : auditHistoryIdList)
        {
            removeConnectorInUserMap(sessionId, connector);
        }
        connectorMap.remove(connector);
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
