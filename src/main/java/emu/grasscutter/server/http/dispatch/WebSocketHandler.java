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
                switch (request.method) {
                    case "getAccountById" -> {
                        Account account = DatabaseHelper.getAccountById((String) request.params.get("id"));
                        if (account != null) {
                            RPCResponse.RPCResponseSuccess<Account> responseSuccess = new RPCResponse.RPCResponseSuccess<>();
                            responseSuccess.result = account;
                            responseSuccess.id = request.id;
                            wsMessageContext.send(responseSuccess);
                        }
                    }
                    case "createAccountWithUid" -> {
                        if (request.params.get("username") == null || request.params.get("uid") == null) {
                            RPCResponse.RPCResponseError<String> responseError = new RPCResponse.RPCResponseError<>();
                            responseError.error = new RPCResponse.RPCError<>(-32602, "Invalid params", null);
                            responseError.id = request.id;
                            break;
                        }
                        String username = (String) request.params.get("username");
                        int reservedUid = (int) request.params.get("uid");
                        Account accountWithUid = DatabaseHelper.createAccountWithUid(username, reservedUid);
                        if (accountWithUid != null) {
                            RPCResponse.RPCResponseSuccess<Account> responseSuccess = new RPCResponse.RPCResponseSuccess<>();
                            responseSuccess.result = accountWithUid;
                            responseSuccess.id = request.id;
                            wsMessageContext.send(responseSuccess);
                        } else {
                            RPCResponse.RPCResponseError<String> responseError = new RPCResponse.RPCResponseError<>();
                            responseError.error = new RPCResponse.RPCError<>(-32000, "Unable to create account with uid : " + reservedUid, null);
                            responseError.id = request.id;
                            wsMessageContext.send(responseError);
                        }
                    }
                    case "createAccount" -> {
                        if (request.params.get("username") == null) {
                            RPCResponse.RPCResponseError<String> responseError = new RPCResponse.RPCResponseError<>();
                            responseError.error = new RPCResponse.RPCError<>(-32602, "Invalid params", null);
                            responseError.id = request.id;
                            break;
                        }
                        String username = (String) request.params.get("username");
                        Account account = DatabaseHelper.createAccount(username);
                        if (account != null) {
                            RPCResponse.RPCResponseSuccess<Account> responseSuccess = new RPCResponse.RPCResponseSuccess<>();
                            responseSuccess.result = account;
                            responseSuccess.id = request.id;
                            wsMessageContext.send(responseSuccess);
                        } else {
                            RPCResponse.RPCResponseError<String> responseError = new RPCResponse.RPCResponseError<>();
                            responseError.error = new RPCResponse.RPCError<>(-32000, "Unable to create account with username : " + username, null);
                            responseError.id = request.id;
                            wsMessageContext.send(responseError);
                        }
                    }
                    case "createAccountWithPassword" -> {
                        if (request.params.get("username") == null || request.params.get("password") == null) {
                            RPCResponse.RPCResponseError<String> responseError = new RPCResponse.RPCResponseError<>();
                            responseError.error = new RPCResponse.RPCError<>(-32602, "Invalid params", null);
                            responseError.id = request.id;
                            break;
                        }
                        String username = (String) request.params.get("username");
                        String password = (String) request.params.get("password");
                        Account accountWithPassword = DatabaseHelper.createAccountWithPassword(username, password);
                        if (accountWithPassword != null) {
                            RPCResponse.RPCResponseSuccess<Account> responseSuccess = new RPCResponse.RPCResponseSuccess<>();
                            responseSuccess.result = accountWithPassword;
                            responseSuccess.id = request.id;
                            wsMessageContext.send(responseSuccess);
                        } else {
                            RPCResponse.RPCResponseError<String> responseError = new RPCResponse.RPCResponseError<>();
                            responseError.error = new RPCResponse.RPCError<>(-32000, "Unable to create account with username : " + username, null);
                            responseError.id = request.id;
                            wsMessageContext.send(responseError);
                        }
                    }
                }
            });
        });
    }
}
