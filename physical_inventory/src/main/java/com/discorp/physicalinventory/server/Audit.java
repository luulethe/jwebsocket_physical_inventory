package com.discorp.physicalinventory.server;

import com.discorp.physicalinventory.middletierservices.BaseService;
import com.discorp.wholegoods.WholeGoods;
import com.discorp.wholegoods.constant.ResponseStatus;
import com.discorp.wholegoods.model.InventoryResponseDTO;
import com.discorp.wholegoods.model.inventory.InventoryProcessDTO;
import com.discorp.xml.LoginStatus;
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
public class Audit extends TokenPlugIn
{
    private static org.apache.log4j.Logger log = Logging.getLogger(Audit.class);
    private final static String NAME_SPACE = "com.discorp.physicalInventory.audit";

    private static WholeGoods wholeGoods = BaseService.getWholeGoods();
    private static Gson gson = new Gson();

    public Audit(PluginConfiguration aConfiguration)
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
                if (lType.equals("start"))
                {
                    startAudit(connector, token);
                }
                else if (lType.equals("end"))
                {
                    endAudit(connector, token);
                }
            }
        }
        catch (JWebSocketException jWebSocketException)
        {

        }
    }

    private void endAudit(WebSocketConnector connector, Token token) throws JWebSocketException
    {
        InventoryResponseDTO responseDTO = wholeGoods.endAudit(token.getString("token"), token.getLong("auditHistoryId"), token.getBoolean("isFinish"));
        if (responseDTO.getLoginResult().getLoginStatus().equals(LoginStatus.PASS))
        {
            User.removeUserFromAuditSession(token.getLong("auditHistoryId"), connector);
        }
        Token lResponse = createResponse(token);

        String result = gson.toJson(responseDTO);
        lResponse.setString("msg", result);
        lResponse.setString("reqType", "responseEnd");

        sendToken(connector, lResponse);

    }

    private void startAudit(WebSocketConnector connector, Token token)
    {
        InventoryResponseDTO responseDTO = wholeGoods.startAudit(token.getString("token"), token.getLong("branchId"));
        if (responseDTO.getLoginResult().getLoginStatus().equals(LoginStatus.PASS) && (responseDTO.getInventoryStatusDTO().getStatus().equals(ResponseStatus.SUCCESS)))
        {
            User.addUserToAuditSession(((InventoryProcessDTO) responseDTO.getResultDTO()).getAuditHistoryId(), connector);
        }
        Token lResponse = createResponse(token);

        String result = gson.toJson(responseDTO);
        lResponse.setString("msg", result);
        lResponse.setString("reqType", "responseStart");

        sendToken(connector, lResponse);
    }
}
