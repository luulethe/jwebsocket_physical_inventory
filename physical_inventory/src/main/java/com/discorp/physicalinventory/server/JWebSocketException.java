package com.discorp.physicalinventory.server;

/**
 * User: luult
 * Date: 2/28/14
 */
public class JWebSocketException extends Exception
{
    public JWebSocketException(String s)
    {
        super(s);
        System.out.println("error.................");
    }
}
