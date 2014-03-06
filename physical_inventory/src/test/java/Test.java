import com.discorp.model.dto.LocationDTO;
import com.discorp.physicalinventory.middletierservices.BaseService;
import com.discorp.wholegoods.WholeGoods;
import com.google.gson.Gson;

/**
 * User: luult
 * Date: 3/4/14
 */
public class Test
{
    private static WholeGoods wholeGoods = BaseService.getWholeGoods();

    public static void main(String[] args)
    {
        String token = "0d17f011-d85a-4854-886b-77b59886a420";
        long auditHistoryId = 6;
        String qrCode = "http://disprism.com/page/equipment/670CA1D087751014A7FDC01AD01C6F22";
        LocationDTO locationDTO = (new Gson()).fromJson("{longtitude:105.788727,lattitude:21.032143,'street':'Tran Thai Tong'}", LocationDTO.class);

//        LocationDTO locationDTO = (new Gson()).fromJson("{'street':'Tran Thai Tong'}", LocationDTO.class);
        System.out.println(wholeGoods.addUnknownEquipment(token,auditHistoryId,"rrr", null, locationDTO, null));
    }
}
