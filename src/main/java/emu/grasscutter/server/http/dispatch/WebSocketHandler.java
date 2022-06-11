package emu.grasscutter.server.http.dispatch;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.server.http.Router;
import emu.grasscutter.server.http.objects.RPCRequest;
import emu.grasscutter.server.http.objects.RPCResponse;
import express.Express;
import io.javalin.Javalin;
import org.eclipse.jetty.util.ajax.JSON;

import java.util.ArrayList;

import static emu.grasscutter.Configuration.*;

public final class WebSocketHandler implements Router {
    @Override
    public void applyRoutes(Express express, Javalin handle) {
        express.ws("/websocket",wsHandler -> {
            wsHandler.onConnect(wsConnectContext -> {
                String key = wsConnectContext.queryParam("key");
                ArrayList<String> keys = new ArrayList<>();
                for (Region region:SERVER.dispatch.regions){
                    keys.add(region.Key);
                }
                if (!keys.contains(key)){
                    Grasscutter.getLogger().warn("Game server with ip :"+wsConnectContext.host()+" and key : "+key+" unable to connect.");
                    wsConnectContext.session.disconnect();
                }
            });
            wsHandler.onMessage(wsMessageContext -> {
                RPCRequest request = wsMessageContext.message(RPCRequest.class);
                if (request.method.equals("getAccountById")){
                    Account account = DatabaseHelper.getAccountById((String) request.params.get("id"));
                    if (account!=null){
                        RPCResponse.RPCResponseSuccess<Account> responseSuccess = new RPCResponse.RPCResponseSuccess<>();
                        responseSuccess.result = account;
                        responseSuccess.id = request.id;
                        wsMessageContext.send(responseSuccess);
                    }
                }
            });
        });
    }
}
