package emu.grasscutter.server.game.websocket;


import static emu.grasscutter.Configuration.*;

import com.google.gson.internal.LinkedTreeMap;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.Account;
import emu.grasscutter.server.http.objects.RPCRequest;
import emu.grasscutter.server.http.objects.RPCResponse;
import org.eclipse.jetty.util.ajax.JSON;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import com.google.gson.reflect.TypeToken;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

public class GameWebSocketClient extends WebSocketClient{
    private RPCResponse.RPCResponseError<?> responseError = null;
    private RPCResponse.RPCResponseSuccess<?> responseSuccess = null;
    public GameWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Grasscutter.getLogger().info("WebSocket Connected to Dispatch Server!");
    }

    @Override
    public synchronized void onMessage(String message) {
        try{
            responseError = Grasscutter.getGsonFactory().fromJson(message, new TypeToken<RPCResponse.RPCResponseError<?>>(){}.getType());
        }catch (Exception ignored){}
        try{
            responseSuccess = Grasscutter.getGsonFactory().fromJson(message, new TypeToken<RPCResponse.RPCResponseSuccess<?>>(){}.getType());
        }catch (Exception ignored){}
        if (responseError != null || responseSuccess!=null){
            notify();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Grasscutter.getLogger().error("Websocket Closed. "+code+" "+reason);
    }

    @Override
    public void onError(Exception ex) {
        Grasscutter.getLogger().error(ex.getMessage());
    }

    public Account getAccountById(String id){
        RPCRequest rpcRequest = new RPCRequest();
        rpcRequest.method = "getAccountById";
        rpcRequest.params.put("id",id);
        rpcRequest.id = new Date().getTime();
        this.send(Grasscutter.getGsonFactory().toJson(rpcRequest));
        if (!waitForResponse(rpcRequest.id)) return null;
        String jsonResult = Grasscutter.getGsonFactory().toJson(responseSuccess.result);
        Account account = Grasscutter.getGsonFactory().fromJson(jsonResult, new TypeToken<Account>(){}.getType());
        Grasscutter.getLogger().info("Account on method : "+account);
        return account;
    }

    private synchronized boolean waitForResponse(long id){
        while (responseSuccess == null && responseError == null){
            try {this.wait();} catch (Exception ignored) {}
        }
        if (responseSuccess == null) return false;
        while (responseSuccess.id != id){
            try{wait();}catch (Exception ignored){}
        }
        return true;
    }

    private void resetResponse(){
        responseSuccess = null;
        responseError = null;
    }
}
