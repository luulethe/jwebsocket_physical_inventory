package com.discorp.physicalinventory.server;

import com.discorp.physicalinventory.manager.User;
import com.discorp.physicalinventory.middletierservices.BaseService;
import com.discorp.wholegoods.WholeGoods;
import com.discorp.wholegoods.constant.ResponseStatus;
import com.discorp.wholegoods.model.InventoryResponseDTO;
import com.discorp.wholegoods.model.inventory.InventoryProcessDTO;
import com.discorp.xml.LoginStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jwebsocket.api.PluginConfiguration;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.kit.PlugInResponse;
import org.jwebsocket.logging.Logging;
import org.jwebsocket.token.Token;


/**
 * User: luult
 * Date: 2/28/14
 */
public class Audit extends BaseTokenPlugIn
{
    private static org.apache.log4j.Logger log = Logging.getLogger(Audit.class);
    private final static String NAME_SPACE = "com.discorp.physicalInventory.audit";

    private static WholeGoods wholeGoods = BaseService.getWholeGoods();
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

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
        catch (Exception jWebSocketException)
        {
            jWebSocketException.printStackTrace();
            Token lResponse = createResponse(token);
            lResponse.setString("msg", "error server");
            lResponse.setString("reqType", "response" + token.getType());

            sendErrorMessage(connector, token);
        }
    }

    private void sendErrorMessage(WebSocketConnector connector, Token token)
    {
        sendToken(connector, token);
    }

    private void endAudit(WebSocketConnector connector, Token token) throws JWebSocketException
    {
        System.out.println(token.getBoolean("isFinish"));
        System.out.println(Long.parseLong(token.getString("auditHistoryId")));
        InventoryResponseDTO responseDTO = wholeGoods.endAudit(token.getString("token"), Long.parseLong(token.getString("auditHistoryId")), token.getBoolean("isFinish"));
        if (responseDTO.getLoginResult().getLoginStatus().equals(LoginStatus.PASS))
        {
            User.removeUserFromAuditSession(Long.parseLong(token.getString("auditHistoryId")), connector);
        }
        Token lResponse = createResponse(token);

        String result = gson.toJson(responseDTO);
        lResponse.setString("msg", result);
        lResponse.setString("reqType", "responseEnd");
        sendTokenHasGenId(connector, lResponse);
        System.out.println("end audit successfully");
    }

    private void startAudit(WebSocketConnector connector, Token token)
    {
        System.out.println("start audit");
        InventoryResponseDTO responseDTO = wholeGoods.startAudit(token.getString("token"), Long.parseLong(token.getString("branchId")));
        if (responseDTO.getLoginResult().getLoginStatus().equals(LoginStatus.PASS) && (responseDTO.getInventoryStatusDTO().getStatus().equals(ResponseStatus.SUCCESS)))
        {

            User.addUserToAuditSession(((InventoryProcessDTO) responseDTO.getResultDTO()).getAuditHistoryId(), connector);
        }
        Token lResponse = createResponse(token);

        String result = gson.toJson(responseDTO);
        System.out.println(result);
        lResponse.setString("msg", result);
        lResponse.setString("reqType", "responseStart");

        sendTokenHasGenId(connector, lResponse);
        System.out.println("success start");
    }
}
