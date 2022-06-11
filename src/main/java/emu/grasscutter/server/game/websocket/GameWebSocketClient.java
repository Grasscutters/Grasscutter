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
    private boolean gotResponse = false;
    private Object result;
    public GameWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Grasscutter.getLogger().info("WebSocket Connected to Dispatch Server!");
    }

    @Override
    public synchronized void onMessage(String message) {
        RPCResponse.RPCResponseError<?> responseError = null;
        RPCResponse.RPCResponseSuccess<?> responseSuccess = null;
        try{
            responseError = Grasscutter.getGsonFactory().fromJson(message, new TypeToken<RPCResponse.RPCResponseError<?>>(){}.getType());
        }catch (Exception ignored){}
        try{
            responseSuccess = Grasscutter.getGsonFactory().fromJson(message, new TypeToken<RPCResponse.RPCResponseSuccess<?>>(){}.getType());
        }catch (Exception ignored){}
        if (responseSuccess!=null) result = responseSuccess.result;
        if (responseError != null || responseSuccess!=null){
            gotResponse = true;
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

    public synchronized Account getAccountById(String id){
        RPCRequest rpcRequest = new RPCRequest();
        rpcRequest.method = "getAccountById";
        rpcRequest.params.put("id",id);
        rpcRequest.id = new Date().getTime();
        this.send(Grasscutter.getGsonFactory().toJson(rpcRequest));
        String json = Grasscutter.getGsonFactory().toJson(rpcRequest);
        Grasscutter.getLogger().info(json);
        while (!gotResponse){
            try {
                this.wait();
            } catch (Exception ignored) {}
        }
        String jsonResult = Grasscutter.getGsonFactory().toJson(result);
        Account account = Grasscutter.getGsonFactory().fromJson(jsonResult, new TypeToken<Account>(){}.getType());
        Grasscutter.getLogger().info("Account on method : "+account);
        gotResponse = false;
        return account;
    }
}
