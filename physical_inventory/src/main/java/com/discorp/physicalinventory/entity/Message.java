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
    private Long sentTime;
    private BaseTokenPlugIn baseTokenPlugIn;

    public Message(WebSocketConnector connector, Token token, int numberSent, Long sentTime, BaseTokenPlugIn baseTokenPlugIn)
    {
        this.token = token;
        this.webSocketConnector = connector;
        this.numberSent = numberSent;
        this.sentTime = sentTime;
        this.baseTokenPlugIn = baseTokenPlugIn;
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

    public Long getSentTime()
    {
        return sentTime;
    }

    public void setSentTime(Long sentTime)
    {
        this.sentTime = sentTime;
    }

    public BaseTokenPlugIn getBaseTokenPlugIn()
    {
        return baseTokenPlugIn;
    }

    public void setBaseTokenPlugIn(BaseTokenPlugIn baseTokenPlugIn)
    {
        this.baseTokenPlugIn = baseTokenPlugIn;
    }

    public void increaseNumberSent()
    {
        System.out.println("incresing.......................");
        System.out.println(webSocketConnector);
        System.out.println(token);
        this.numberSent++;
    }
}
