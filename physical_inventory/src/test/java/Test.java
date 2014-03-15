import com.discorp.model.dto.LocationDTO;
import com.discorp.physicalinventory.middletierservices.BaseService;
import com.discorp.wholegoods.WholeGoods;
import com.discorp.wholegoods.model.inventory.InventoryProcessDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

/**
* User: luult
* Date: 3/4/14
*/
public class Test
{
    private static WholeGoods wholeGoods = BaseService.getWholeGoods();
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
//    private static Gson gson = new GsonBuilder().setDateFormat(DateFormat.LONG).create();

    public static void main(String[] args)
    {

    }
}
