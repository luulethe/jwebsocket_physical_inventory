package com.discorp.physicalinventory.entity;

import com.discorp.physicalinventory.server.BaseTokenPlugIn;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.token.Token;

/**
 * User: luult
 * Date: 3/13/14
 */
public class Message
{
    private WebSocketConnector webSocketConnector;
    private Token token;
    private Integer numberSent;
    private BaseTokenPlugIn baseTokenPlugIn;

    public Message(WebSocketConnector connector, Token aToken, int numberSent)
    {
        this.token = token;
        this.webSocketConnector = connector;
        this.numberSent = numberSent;
    }

    public Integer getNumberSent()
    {
        return numberSent;
    }

    public void setNumberSent(Integer numberSent)
    {
        this.numberSent = numberSent;
    }

    public WebSocketConnector getWebSocketConnector()
    {
        return webSocketConnector;
    }

    public void setWebSocketConnector(WebSocketConnector webSocketConnector)
    {
        this.webSocketConnector = webSocketConnector;
    }

    public Token getToken()
    {
        return token;
    }

    public void setToken(Token token)
    {
        this.token = token;
    }
}
