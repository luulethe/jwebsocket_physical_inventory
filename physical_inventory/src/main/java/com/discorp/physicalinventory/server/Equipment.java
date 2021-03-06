package com.discorp.physicalinventory.server;

import com.discorp.model.dto.LocationDTO;
import com.discorp.physicalinventory.dto.EquipmentDTO;
import com.discorp.physicalinventory.manager.User;
import com.discorp.physicalinventory.middletierservices.BaseService;
import com.discorp.wholegoods.WholeGoods;
import com.discorp.wholegoods.constant.ResponseStatus;
import com.discorp.wholegoods.model.ImageDTO;
import com.discorp.wholegoods.model.InventoryResponseDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.geronimo.mail.util.Base64;
import org.jwebsocket.api.PluginConfiguration;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.kit.PlugInResponse;
import org.jwebsocket.logging.Logging;
import org.jwebsocket.token.Token;

/**
 * User: luult
 * Date: 2/28/14
 */
public class Equipment extends BaseTokenPlugIn
{
    private static org.apache.log4j.Logger log = Logging.getLogger(Audit.class);

    private final static String NAME_SPACE = "com.discorp.physicalInventory.equipment";

    private static WholeGoods wholeGoods = BaseService.getWholeGoods();
    private static Gson gson =

            new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();


    public Equipment(PluginConfiguration aConfiguration)
    {

        super(aConfiguration);
        this.setNamespace(NAME_SPACE);
    }

    @Override
    public void processToken(PlugInResponse response, WebSocketConnector connector, Token token)
    {
        String lType = token.getType();
        String lNS = token.getNS();
        System.out.println("starting process ...");
        System.out.println(token);
        try
        {
            if (lType != null && lNS != null && lNS.equals(getNamespace()))
            {
                if (lType.equals("addNote"))
                {
                    addNote(connector, token);
                }
                else if (lType.equals("addHours"))
                {
                    addHours(connector, token);
                }
                else if (lType.equals("addUnknown"))
                {
                    addUnknown(connector, token);
                }
                else if (lType.equals("updateUnknown"))
                {
                    updateUnknown(connector, token);
                }
                else if (lType.equals("scan"))
                {
                    scan(connector, token);
                }
                else if (lType.equals("markOff"))
                {
                    markOff(connector, token);
                }
                else if (lType.equals("unMarkOff"))
                {
                    unMarkOff(connector, token);
                }
            }
        }
        catch (Exception jWebSocketException)
        {
            jWebSocketException.printStackTrace();
            Token lResponse = createResponse(token);
            lResponse.setString("msg", "error server");
            lResponse.setString("reqType", "response" + token.getType());

            sendErrorMessage(connector, token);
        }
    }

    private void updateUnknown(WebSocketConnector connector, Token token)
    {
        ImageDTO image = new ImageDTO();
        if (token.getString("fileName") != null)
        {
            image.setFileName(token.getString("fileName"));
            image.setFileType(token.getString("fileType"));
//            String fileContent = token.getString("fileContent");

            image.setFileContent(token.getObject("fileContent").toString().getBytes());
        }
        LocationDTO locationDTO = (new Gson()).fromJson(token.getString("locationDTO"), LocationDTO.class);
        InventoryResponseDTO inventoryResponseDTO =
                wholeGoods.updateUnknownEquipment(token.getString("token"), Long.parseLong(token.getString("unknownEquipmentId")),
                        image, token.getString("description"), locationDTO, token.getBoolean("identified"));
        sendToToken(connector, token, gson.toJson(inventoryResponseDTO), "responseUpdateUnknown");
        String resultForOther = gson.toJson(inventoryResponseDTO.getResultDTO());
        broadCastMessage(Long.parseLong(token.getString("auditHistoryId")), connector, token, resultForOther, "responseUpdateUnknownFromOther");
    }

    private void sendErrorMessage(WebSocketConnector connector, Token token)
    {
        sendToken(connector, token);
    }

    private void addNote(WebSocketConnector connector, Token token)
    {
        InventoryResponseDTO inventoryResponseDTO =
                wholeGoods.addNote(token.getString("token"), Long.parseLong(token.getString("auditHistoryId")),
                        Long.parseLong(token.getString("equipmentWebId")), token.getString("note"));
        String result = gson.toJson(inventoryResponseDTO);
        sendToToken(connector, token, result, "responseAddNote");
        if ((inventoryResponseDTO.getInventoryStatusDTO() != null) &&
                (inventoryResponseDTO.getInventoryStatusDTO().getStatus().equals(ResponseStatus.SUCCESS)))
        {
            sendAddNoteForOther(connector, token);
        }
    }

    private void sendAddNoteForOther(WebSocketConnector connector, Token token)
    {
        EquipmentDTO equipmentDTO = new EquipmentDTO();
        equipmentDTO.setAuditHistoryId(Long.parseLong(token.getString("auditHistoryId")));
        equipmentDTO.setEquipmentWebId(Long.parseLong(token.getString("equipmentWebId")));
        equipmentDTO.setNote(token.getString("note"));

        broadCastMessage(Long.parseLong(token.getString("auditHistoryId")), connector, token, gson.toJson(equipmentDTO), "responseAddNoteFromOther");
    }

    private void addHours(WebSocketConnector connector, Token token)
    {
        InventoryResponseDTO inventoryResponseDTO =
                wholeGoods.addHours(token.getString("token"), Long.parseLong(token.getString("auditHistoryId")),
                        Long.parseLong(token.getString("equipmentWebId")), token.getString("hours"));

        String result = gson.toJson(inventoryResponseDTO);
        sendToToken(connector, token, result, "responseAddHours");
        if ((inventoryResponseDTO.getInventoryStatusDTO() != null) &&
                (inventoryResponseDTO.getInventoryStatusDTO().getStatus().equals(ResponseStatus.SUCCESS)))
        {
            sendAddHoursForOther(connector, token);
        }
    }

    private void sendAddHoursForOther(WebSocketConnector connector, Token token)
    {
        EquipmentDTO equipmentDTO = new EquipmentDTO();
        equipmentDTO.setAuditHistoryId(Long.parseLong(token.getString("auditHistoryId")));
        equipmentDTO.setEquipmentWebId(Long.parseLong(token.getString("equipmentWebId")));
        equipmentDTO.setHours(token.getString("hours"));

        broadCastMessage(Long.parseLong(token.getString("auditHistoryId")), connector, token, gson.toJson(equipmentDTO), "responseAddHoursFromOther");
    }

    private void addUnknown(WebSocketConnector connector, Token token)
    {
        ImageDTO image = new ImageDTO();
        if (token.getString("fileName") != null)
        {
            image.setFileName(token.getString("fileName"));
            image.setFileType(token.getString("fileType"));
            String fileContent = token.getString("fileContent");

            image.setFileContent(Base64.decode(fileContent));
        }
        LocationDTO locationDTO = (new Gson()).fromJson(token.getString("locationDTO"), LocationDTO.class);
        InventoryResponseDTO inventoryResponseDTO =
                wholeGoods.addUnknownEquipment(token.getString("token"), Long.parseLong(token.getString("auditHistoryId")),
                        token.getString("description"), image, locationDTO, token.getBoolean("identified"));
        sendToToken(connector, token, gson.toJson(inventoryResponseDTO), "responseAddUnknown");
        String resultForOther = gson.toJson(inventoryResponseDTO.getResultDTO());
        broadCastMessage(Long.parseLong(token.getString("auditHistoryId")), connector, token, resultForOther, "responseAddUnknownFromOther");
    }

    private void scan(WebSocketConnector connector, Token token)
    {
        LocationDTO locationDTO = (new Gson()).fromJson(token.getString("locationDTO"), LocationDTO.class);
        System.out.println(locationDTO.getStreet());
        System.out.println(locationDTO.getLattitude());
        System.out.println(locationDTO.getLongtitude());

        InventoryResponseDTO inventoryResponseDTO =
                wholeGoods.scanEquipment(token.getString("token"), Long.parseLong(token.getString("auditHistoryId")),
                        token.getString("qrCode"), locationDTO);

        String result = gson.toJson(inventoryResponseDTO);
        sendToToken(connector, token, result, "responseScan");
        String resultForOther = gson.toJson(inventoryResponseDTO.getResultDTO());
        broadCastMessage(Long.parseLong(token.getString("auditHistoryId")), connector, token, resultForOther, "responseScanFromOther");
    }

    private void markOff(WebSocketConnector connector, Token token)
    {
        InventoryResponseDTO inventoryResponseDTO =
                wholeGoods.markOff(token.getString("token"), Long.parseLong(token.getString("auditHistoryId")),
                        Long.parseLong(token.getString("equipmentWebId")), token.getString("markOffReason"));

        String result = gson.toJson(inventoryResponseDTO);
        sendToToken(connector, token, result, "responseMarkOff");
        if ((inventoryResponseDTO.getInventoryStatusDTO() != null) &&
                (inventoryResponseDTO.getInventoryStatusDTO().getStatus().equals(ResponseStatus.SUCCESS)))
        {
            String resultForOther = gson.toJson(inventoryResponseDTO.getResultDTO());
            broadCastMessage(Long.parseLong(token.getString("auditHistoryId")), connector, token, resultForOther, "responseMarkOffFromOther");
        }
    }

    private void unMarkOff(WebSocketConnector connector, Token token)
    {
        InventoryResponseDTO inventoryResponseDTO =
                wholeGoods.unMarkOff(token.getString("token"), Long.parseLong(token.getString("auditHistoryId")),
                        Long.parseLong(token.getString("equipmentWebId")));

        String result = gson.toJson(inventoryResponseDTO);
        sendToToken(connector, token, result, "responseUnMarkOff");
        if ((inventoryResponseDTO.getInventoryStatusDTO() != null) &&
                (inventoryResponseDTO.getInventoryStatusDTO().getStatus().equals(ResponseStatus.SUCCESS)))
        {
            sendUnMarkOffForOther(connector, token);
        }
    }

    private void sendUnMarkOffForOther(WebSocketConnector connector, Token token)
    {
        EquipmentDTO equipmentDTO = new EquipmentDTO();
        equipmentDTO.setAuditHistoryId(Long.parseLong(token.getString("auditHistoryId")));
        equipmentDTO.setEquipmentWebId(Long.parseLong(token.getString("equipmentWebId")));

        broadCastMessage(Long.parseLong(token.getString("auditHistoryId")), connector, token, gson.toJson(equipmentDTO), "responseUnMarkOffFromOther");
    }

    private void broadCastMessage(Long sessionId, WebSocketConnector exceptConnector, Token token, String message, String reqType)
    {
        Token response = createResponse(token);
        response.setString("msg", message);
        response.setString("reqType", reqType);
        for (WebSocketConnector connector : User.getUsersInAnAuditSession(sessionId))
            if (connector != exceptConnector)
            {
                sendTokenHasGenId(connector, response);
            }
    }

    private void sendToToken(WebSocketConnector connector, Token token, String message, String reqType)
    {
        Token response = createResponse(token);
        response.setString("msg", message);
        response.setString("reqType", reqType);
        sendTokenHasGenId(connector, response);
    }
}
