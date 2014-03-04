package com.discorp.physicalinventory.middletierservices;

import com.caucho.hessian.client.HessianProxyFactory;
import com.discorp.wholegoods.WholeGoods;

import java.net.MalformedURLException;

/**
* User: luult
* Date: 12/11/13
* Time: 2:43 PM
*/
public class BaseService
{

    private static WholeGoods wholeGoods;

    private static String wholeGoodsURL = "http://localhost:8181/wholegoodsremoting";

    public static WholeGoods getWholeGoods()
    {
        if (null == wholeGoods)
        {
            HessianProxyFactory factory = new HessianProxyFactory();
            try
            {
                wholeGoods = (WholeGoods) factory.create(WholeGoods.class, wholeGoodsURL);
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return null;
            }
        }
        return wholeGoods;
    }
}
