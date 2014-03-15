package com.discorp.physicalinventory.manager;

/**
 * User: luult
 * Date: 3/14/14
 */
public class ID
{
    private static Integer id = 0;
    public static Integer getNextTokenId()
    {
        return id++;
    }
}
