package com.discorp.physicalinventory.server;

import com.discorp.model.dto.LocationDTO;
import com.discorp.physicalinventory.middletierservices.BaseService;
import com.discorp.wholegoods.WholeGoods;
import com.discorp.wholegoods.model.ImageDTO;
import com.discorp.wholegoods.model.InventoryResponseDTO;
import com.google.gson.Gson;
import org.jwebsocket.api.PluginConfiguration;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.kit.PlugInResponse;
import org.jwebsocket.logging.Logging;
import org.jwebsocket.plugins.TokenPlugIn;
import org.jwebsocket.token.Token;

/**
 * User: luult
 * Date: 2/28/14
 */
public class Equipment extends TokenPlugIn
{
    private static org.apache.log4j.Logger log = Logging.getLogger(Audit.class);

    private final static String NAME_SPACE = "com.discorp.physicalInventory.equipment";

    private static WholeGoods wholeGoods = BaseService.getWholeGoods();
    private static Gson gson = new Gson();

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

    private void addNote(WebSocketConnector connector, Token token)
    {
        InventoryResponseDTO inventoryResponseDTO =
                wholeGoods.addNote(token.getString("token"), token.getLong("auditHistoryId"),
                        token.getLong("equipmentWebId"), token.getString("note"));

        Token response = createResponse(token);
        String result = gson.toJson(inventoryResponseDTO);
        response.setString("msg", result);
        response.setString("reqType", "responseScan");

        broadCastMessage(token.getLong("auditHistoryId"), response);

    }

    private void addHours(WebSocketConnector connector, Token token)
    {
        InventoryResponseDTO inventoryResponseDTO =
                wholeGoods.addHours(token.getString("token"), token.getLong("auditHistoryId"),
                        token.getLong("equipmentWebId"), token.getString("hours"));

        Token response = createResponse(token);
        String result = gson.toJson(inventoryResponseDTO);
        response.setString("msg", result);
        response.setString("reqType", "responseScan");

        broadCastMessage(token.getLong("auditHistoryId"), response);
    }

    private void addUnknown(WebSocketConnector connector, Token token)
    {
        ImageDTO image = new ImageDTO();

        LocationDTO locationDTO = (new Gson()).fromJson(token.getString("locationDTO"), LocationDTO.class);
        InventoryResponseDTO inventoryResponseDTO =
                wholeGoods.addUnknownEquipment(token.getString("token"), token.getLong("auditHistoryId"),
                        token.getString("description"), image, locationDTO, token.getBoolean("identified"));

        Token response = createResponse(token);
        String result = gson.toJson(inventoryResponseDTO);
        response.setString("msg", result);
        response.setString("reqType", "responseAddUnknown");

        broadCastMessage(token.getLong("auditHistoryId"), response);

    }

    private void scan(WebSocketConnector connector, Token token)
    {
        LocationDTO locationDTO = (new Gson()).fromJson(token.getString("locationDTO"), LocationDTO.class);
        InventoryResponseDTO inventoryResponseDTO =
                wholeGoods.scanEquipment(token.getString("token"), token.getLong("auditHistoryId"), token.getString("qrCode"), locationDTO);
        Token response = createResponse(token);

        String result = gson.toJson(inventoryResponseDTO);
        response.setString("msg", result);
        response.setString("reqType", "responseScan");

        broadCastMessage(token.getLong("auditHistoryId"), response);
    }


    private void markOff(WebSocketConnector connector, Token token)
    {
        InventoryResponseDTO inventoryResponseDTO =
                wholeGoods.markOff(token.getString("token"), token.getLong("auditHistoryId"),
                        token.getLong("equipmentWebId"), token.getString("markOffReason"));

        Token response = createResponse(token);
        String result = gson.toJson(inventoryResponseDTO);
        response.setString("msg", result);
        response.setString("reqType", "responseMarkOff");

        broadCastMessage(token.getLong("auditHistoryId"), response);
    }

    private void unMarkOff(WebSocketConnector connector, Token token)
    {
        InventoryResponseDTO inventoryResponseDTO =
                wholeGoods.unMarkOff(token.getString("token"), token.getLong("auditHistoryId"),
                        token.getLong("equipmentWebId"));

        Token response = createResponse(token);
        String result = gson.toJson(inventoryResponseDTO);
        response.setString("msg", result);
        response.setString("reqType", "responseUnMarkOff");

        broadCastMessage(token.getLong("auditHistoryId"), response);

    }
    private void broadCastMessage(Long sessionId, Token response)
    {
        for(WebSocketConnector connector : User.getUsersInAnAuditSession(sessionId))
            sendToken(connector, response);
    }
}
