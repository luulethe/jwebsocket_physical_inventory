//import com.discorp.model.dto.LocationDTO;
//import com.discorp.physicalinventory.middletierservices.BaseService;
//import com.discorp.wholegoods.WholeGoods;
//import com.discorp.wholegoods.model.inventory.InventoryProcessDTO;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import java.text.DateFormat;
//import java.util.Date;
//
///**
// * User: luult
// * Date: 3/4/14
// */
//public class Test
//{
//    private static WholeGoods wholeGoods = BaseService.getWholeGoods();
//    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
////    private static Gson gson = new GsonBuilder().setDateFormat(DateFormat.LONG).create();
//
//    public static void main(String[] args)
//    {
////        String token = "0d17f011-d85a-4854-886b-77b59886a420";
////        long auditHistoryId = 6;
////        String qrCode = "http://disprism.com/page/equipment/670CA1D087751014A7FDC01AD01C6F22";
////        LocationDTO locationDTO = (new Gson()).fromJson("{longtitude:105.788727,lattitude:21.032143,'street':'Tran Thai Tong'}", LocationDTO.class);
//
////        LocationDTO locationDTO = (new Gson()).fromJson("{'street':'Tran Thai Tong'}", LocationDTO.class);
////        System.out.println(wholeGoods.addUnknownEquipment(token,auditHistoryId,"rrr", null, locationDTO, null));
//        InventoryProcessDTO inventoryProcessDTO = new InventoryProcessDTO();
//        inventoryProcessDTO.setRequestedTime(new Date());
//        System.out.println(gson.toJson(inventoryProcessDTO));
//    }
//}
