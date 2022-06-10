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

public final class WebSocketHandler implements Router {
    @Override
    public void applyRoutes(Express express, Javalin handle) {
        express.ws("/websocket/accounts",wsHandler -> {
            wsHandler.onConnect(wsConnectContext -> {
                String key = wsConnectContext.queryParam("key");
                if (!key.equals("inikey")){
                    wsConnectContext.session.disconnect();
                }else{
                    Grasscutter.getLogger().info("Connected!");
                }
            });
            wsHandler.onMessage(wsMessageContext -> {
                RPCRequest request = wsMessageContext.message(RPCRequest.class);
                if (request.method.equals("get")){
                    Account account = DatabaseHelper.getAccountById((String) request.params.get("uid"));
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
