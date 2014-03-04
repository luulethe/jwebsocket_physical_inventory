import com.discorp.physicalinventory.middletierservices.BaseService;
import com.discorp.wholegoods.WholeGoods;

/**
 * User: luult
 * Date: 3/4/14
 */
public class Test
{
    private static WholeGoods wholeGoods = BaseService.getWholeGoods();

    public static void main(String[] args)
    {
        System.out.println(wholeGoods.endAudit("0d17f011-d85a-4854-886b-77b59886a420",6l, null));
    }
}
