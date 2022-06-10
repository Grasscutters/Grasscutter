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
import java.util.HashMap;

public class GameWebSocketClient extends WebSocketClient{
    private Account account;
    public GameWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Grasscutter.getLogger().info("WebSocket Connected to Dispatch Server!");
    }

    @Override
    public synchronized void onMessage(String message) {
        RPCResponse.RPCResponseSuccess<?> response = Grasscutter.getGsonFactory().fromJson(message, new TypeToken<RPCResponse.RPCResponseSuccess<Account>>(){}.getType());
        account = (Account) response.result;
        Grasscutter.getLogger().info("Account on Message : "+response.result);
        this.notify();
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
        rpcRequest.method = "get";
        rpcRequest.params.put("uid",id);
        this.send(Grasscutter.getGsonFactory().toJson(rpcRequest));
        String json = Grasscutter.getGsonFactory().toJson(rpcRequest);
        Grasscutter.getLogger().info(json);
        while (account==null){
            try {
                this.wait();
            } catch (Exception ignored) {}
        }
        Grasscutter.getLogger().info("Account on method : "+account);
        return account;
    }
}
