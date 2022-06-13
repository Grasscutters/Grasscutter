package emu.grasscutter.server.http.dispatch;

import com.google.gson.reflect.TypeToken;
import dev.morphia.query.experimental.filters.Filters;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.database.DatabaseCounter;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.database.DatabaseManager;
import emu.grasscutter.game.Account;
import emu.grasscutter.server.http.Router;
import emu.grasscutter.server.http.objects.RPCRequest;
import emu.grasscutter.server.http.objects.RPCResponse;
import express.Express;
import io.javalin.Javalin;
import org.eclipse.jetty.util.ajax.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static emu.grasscutter.Configuration.*;

public final class WebSocketHandler implements Router {
    private HashMap<String,Region> regionsIp = new HashMap<>();
    @Override
    public void applyRoutes(Express express, Javalin handle) {
        express.ws("/websocket",wsHandler -> {
            wsHandler.onConnect(wsConnectContext -> {

            });
            wsHandler.onMessage(wsMessageContext -> {
                RPCRequest request = wsMessageContext.message(RPCRequest.class);
                switch (request.method) {
                    case "getAccountById" -> {
                        if (request.params.get("id") == null){
                            RPCResponse.RPCResponseError<?> error = new RPCResponse.RPCResponseError<>();
                            error.error = new RPCResponse.RPCError<>(-32602, "Invalid params",null);
                            error.id = request.id;
                            wsMessageContext.send(error);
                            break;
                        }
                        Account account = DatabaseHelper.getAccountById((String) request.params.get("id"));
                        if (account != null) {
                            RPCResponse.RPCResponseSuccess<Account> responseSuccess = new RPCResponse.RPCResponseSuccess<>();
                            responseSuccess.result = account;
                            responseSuccess.id = request.id;
                            wsMessageContext.send(responseSuccess);
                        }else{
                            RPCResponse.RPCResponseError<?> error = new RPCResponse.RPCResponseError<>();
                            error.error = new RPCResponse.RPCError<>(-32000, "Account not found",null);
                            error.id = request.id;
                            wsMessageContext.send(error);
                        }
                    }
                    case "getAccountByName" -> {
                        if (request.params.get("name") == null){
                            RPCResponse.RPCResponseError<?> error = new RPCResponse.RPCResponseError<>();
                            error.error = new RPCResponse.RPCError<>(-32602, "Invalid params",null);
                            error.id = request.id;
                            wsMessageContext.send(error);
                            break;
                        }
                        Account account = DatabaseHelper.getAccountByName((String) request.params.get("name"));
                        if (account != null) {
                            RPCResponse.RPCResponseSuccess<Account> responseSuccess = new RPCResponse.RPCResponseSuccess<>();
                            responseSuccess.result = account;
                            responseSuccess.id = request.id;
                            wsMessageContext.send(responseSuccess);
                        }else{
                            RPCResponse.RPCResponseError<?> error = new RPCResponse.RPCResponseError<>();
                            error.error = new RPCResponse.RPCError<>(-32000, "Account not found",null);
                            error.id = request.id;
                            wsMessageContext.send(error);
                        }
                    }
                    case "getAccountByToken" -> {
                        if (request.params.get("token") == null){
                            RPCResponse.RPCResponseError<?> error = new RPCResponse.RPCResponseError<>();
                            error.error = new RPCResponse.RPCError<>(-32602, "Invalid params",null);
                            error.id = request.id;
                            wsMessageContext.send(error);
                            break;
                        }
                        Account account = DatabaseHelper.getAccountByToken((String) request.params.get("token"));
                        if (account != null) {
                            RPCResponse.RPCResponseSuccess<Account> responseSuccess = new RPCResponse.RPCResponseSuccess<>();
                            responseSuccess.result = account;
                            responseSuccess.id = request.id;
                            wsMessageContext.send(responseSuccess);
                        }else{
                            RPCResponse.RPCResponseError<?> error = new RPCResponse.RPCResponseError<>();
                            error.error = new RPCResponse.RPCError<>(-32000, "Account not found",null);
                            error.id = request.id;
                            wsMessageContext.send(error);
                        }
                    }
                    case "getAccountBySessionKey" -> {
                        if (request.params.get("sessionKey") == null){
                            RPCResponse.RPCResponseError<?> error = new RPCResponse.RPCResponseError<>();
                            error.error = new RPCResponse.RPCError<>(-32602, "Invalid params",null);
                            error.id = request.id;
                            wsMessageContext.send(error);
                            break;
                        }
                        Account account = DatabaseHelper.getAccountBySessionKey((String) request.params.get("sessionKey"));
                        if (account != null) {
                            RPCResponse.RPCResponseSuccess<Account> responseSuccess = new RPCResponse.RPCResponseSuccess<>();
                            responseSuccess.result = account;
                            responseSuccess.id = request.id;
                            wsMessageContext.send(responseSuccess);
                        }else{
                            RPCResponse.RPCResponseError<?> error = new RPCResponse.RPCResponseError<>();
                            error.error = new RPCResponse.RPCError<>(-32000, "Account not found",null);
                            error.id = request.id;
                            wsMessageContext.send(error);
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
                    case "saveAccount" -> {
                        String accountJson = Grasscutter.getGsonFactory().toJson(request.params);
                        Account account = Grasscutter.getGsonFactory().fromJson(accountJson, new TypeToken<Account>(){}.getType());
                        if (account == null){
                            RPCResponse.RPCResponseError<String> responseError = new RPCResponse.RPCResponseError<>();
                            responseError.error = new RPCResponse.RPCError<>(-32602, "Invalid params", null);
                            responseError.id = request.id;
                            wsMessageContext.send(responseError);
                            break;
                        }
                        try{
                            DatabaseHelper.saveAccount(account);
                            RPCResponse.RPCResponseSuccess<Account> responseSuccess = new RPCResponse.RPCResponseSuccess<>();
                            responseSuccess.result = account;
                            responseSuccess.id = request.id;
                            wsMessageContext.send(responseSuccess);
                        }catch (Exception e){
                            RPCResponse.RPCResponseError<String> responseError = new RPCResponse.RPCResponseError<>();
                            responseError.error = new RPCResponse.RPCError<>(-32000, "Unable to save account", null);
                            responseError.id = request.id;
                            wsMessageContext.send(responseError);
                        }
                    }
                    case "getNextPlayerId" -> {
                        RPCResponse.RPCResponseSuccess<Integer> responseSuccess = new RPCResponse.RPCResponseSuccess<>();
                        DatabaseCounter counter = DatabaseManager.getAccountDatastore().find(DatabaseCounter.class).filter(Filters.eq("_id", "Player")).first();
                        if (counter == null){
                            counter = new DatabaseCounter("Player");
                        }
                        int id = counter.getNextId();
                        DatabaseManager.getAccountDatastore().save(counter);
                        responseSuccess.result = id;
                        responseSuccess.id = request.id;
                        wsMessageContext.send(responseSuccess);
                    }
                    case "getNextAccountId" -> {
                        RPCResponse.RPCResponseSuccess<Integer> responseSuccess = new RPCResponse.RPCResponseSuccess<>();
                        DatabaseCounter counter = DatabaseManager.getAccountDatastore().find(DatabaseCounter.class).filter(Filters.eq("_id", "Account")).first();
                        if (counter == null){
                            counter = new DatabaseCounter("Account");
                        }
                        int id = counter.getNextId();
                        DatabaseManager.getAccountDatastore().save(counter);
                        responseSuccess.result = id;
                        responseSuccess.id = request.id;
                        wsMessageContext.send(responseSuccess);
                    }
                    case "addServerToDispatch" -> {
                        String serverJson = Grasscutter.getGsonFactory().toJson(request.params.get("server"));
                        Region server = Grasscutter.getGsonFactory().fromJson(serverJson, new TypeToken<Region>(){}.getType());
                        DISPATCH_INFO.regions = Arrays.copyOf(DISPATCH_INFO.regions, DISPATCH_INFO.regions.length + 1);
                        DISPATCH_INFO.regions[DISPATCH_INFO.regions.length - 1] = server;
                        Grasscutter.getLogger().info("Added server to dispatch : " + Grasscutter.getGsonFactory().toJson(server));
                        regionsIp.put(wsMessageContext.session.getRemoteAddress().getAddress().toString(), server);
                        try{
                            RegionHandler.initialize();
                            RPCResponse.RPCResponseSuccess<Boolean> responseSuccess = new RPCResponse.RPCResponseSuccess<>();
                            responseSuccess.result = true;
                            responseSuccess.id = request.id;
                            wsMessageContext.send(responseSuccess);
                        }catch (Exception e){
                            RPCResponse.RPCResponseError<Boolean> responseError = new RPCResponse.RPCResponseError<>();
                            responseError.error = new RPCResponse.RPCError<>(-32000, "Unable to add server to dispatch", null);
                            responseError.id = request.id;
                            wsMessageContext.send(responseError);
                        }
                    }
                    case "isServerOnDispatch" -> {
                        RPCResponse.RPCResponseSuccess<Boolean> responseSuccess = new RPCResponse.RPCResponseSuccess<>();
                        responseSuccess.result = regionsIp.containsKey(wsMessageContext.session.getRemoteAddress().getAddress().toString());
                        responseSuccess.id = request.id;
                        wsMessageContext.send(responseSuccess);
                    }
                }
            });
            wsHandler.onClose(wsCloseContext->{
                Grasscutter.getLogger().info("Websocket with Game Server : " + wsCloseContext.session.getRemoteAddress().getAddress().toString() + " closed, Removing from game server list.");
                regionsIp.remove(wsCloseContext.session.getRemoteAddress().toString());
                RegionHandler.initialize();
            });
        });
    }
}
