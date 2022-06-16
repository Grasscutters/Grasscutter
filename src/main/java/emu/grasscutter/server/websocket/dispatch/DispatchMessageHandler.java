package emu.grasscutter.server.websocket.dispatch;

import com.google.gson.reflect.TypeToken;
import dev.morphia.query.experimental.filters.Filters;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.database.DatabaseCounter;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.database.DatabaseManager;
import emu.grasscutter.game.Account;
import emu.grasscutter.server.http.dispatch.RegionHandler;
import emu.grasscutter.server.http.objects.RPCRequest;
import emu.grasscutter.server.http.objects.RPCResponse;
import emu.grasscutter.utils.ConfigContainer;
import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import org.jetbrains.annotations.NotNull;
import static emu.grasscutter.server.websocket.dispatch.WebSocketHandler.regionsIp;
import static emu.grasscutter.server.websocket.dispatch.WebSocketHandler.reinitialize;

public class DispatchMessageHandler implements WsMessageHandler{
    @Override
    public void handleMessage(@NotNull WsMessageContext wsMessageContext) throws Exception {
        RPCRequest request = wsMessageContext.message(RPCRequest.class);
        switch (request.method) {
            case "getAccountById" -> {
                checkParams(request, wsMessageContext, "id");
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
                checkParams(request, wsMessageContext, "name");
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
                checkParams(request, wsMessageContext, "token");
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
                checkParams(request, wsMessageContext, "sessionKey");
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
                checkParams(request, wsMessageContext, "username", "uid");
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
                checkParams(request, wsMessageContext, "username");
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
                checkParams(request, wsMessageContext, "username", "password");
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
                checkParams(request, wsMessageContext, "account");
                String accountJson = Grasscutter.getGsonFactory().toJson(request.params.get("account"));
                Account account = Grasscutter.getGsonFactory().fromJson(accountJson, new TypeToken<Account>(){}.getType());
                if (account == null){
                    RPCResponse.RPCResponseError<String> responseError = new RPCResponse.RPCResponseError<>();
                    responseError.error = new RPCResponse.RPCError<>(-32602, "Error parsing account", null);
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
                checkParams(request, wsMessageContext, "server");
                String serverJson = Grasscutter.getGsonFactory().toJson(request.params.get("server"));
                ConfigContainer.Region server = Grasscutter.getGsonFactory().fromJson(serverJson, new TypeToken<ConfigContainer.Region>(){}.getType());
                Grasscutter.getLogger().info("Added server to dispatch : " + Grasscutter.getGsonFactory().toJson(server));
                regionsIp.put(wsMessageContext.session.getRemoteAddress().getAddress().toString(), server);
                reinitialize();
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
    }
    private void checkParams(RPCRequest request, WsMessageContext wsMessageContext, String... params) {
        if (request.params == null) {
            RPCResponse.RPCResponseError<String> responseError = new RPCResponse.RPCResponseError<>();
            responseError.error = new RPCResponse.RPCError<>(-32602, "Invalid params", null);
            responseError.id = request.id;
            wsMessageContext.send(responseError);
        }
        for (String param : params) {
            if (request.params.get(param) == null) {
                RPCResponse.RPCResponseError<String> responseError = new RPCResponse.RPCResponseError<>();
                responseError.error = new RPCResponse.RPCError<>(-32602, "Invalid params", null);
                responseError.id = request.id;
                wsMessageContext.send(responseError);
            }
        }
    }
}
