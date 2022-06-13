package emu.grasscutter.server.game.websocket;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.Account;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.http.objects.RPCRequest;
import emu.grasscutter.server.http.objects.RPCResponse;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import com.google.gson.reflect.TypeToken;
import java.net.URI;
import static emu.grasscutter.Configuration.*;

public class GameWebSocketClient extends WebSocketClient{
    private RPCResponse.RPCResponseError<?> responseError = null;
    private RPCResponse.RPCResponseSuccess<?> responseSuccess = null;
    public GameWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Grasscutter.getLogger().info("WebSocket Connected to Dispatch Server!");
        Grasscutter.getGameServer().setIsGameWebSocketClientConnected(true);
        if (!isServerOnDispatch()) {
            if (addServerToDispatch()) {
                Grasscutter.getLogger().info("Added to Dispatch Server!.");
            } else {
                Grasscutter.getLogger().error("Failed to add to Dispatch Server!.");
            }
        }
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
        Grasscutter.getGameServer().setIsGameWebSocketClientConnected(false);
    }

    @Override
    public void onError(Exception ex) {
        Grasscutter.getLogger().error(ex.getMessage());
        Grasscutter.getGameServer().setIsGameWebSocketClientConnected(false);
    }

    public Account getAccountById(String id){
        RPCRequest rpcRequest = new RPCRequest();
        rpcRequest.method = "getAccountById";
        rpcRequest.params.put("id",id);
        this.send(Grasscutter.getGsonFactory().toJson(rpcRequest));
        if (!waitForResponse(rpcRequest.id)) return null;
        String jsonResult = Grasscutter.getGsonFactory().toJson(responseSuccess.result);
        return Grasscutter.getGsonFactory().fromJson(jsonResult, new TypeToken<Account>(){}.getType());
    }

    public Account getAccountByName(String username){
        RPCRequest rpcRequest = new RPCRequest();
        rpcRequest.method = "getAccountByName";
        rpcRequest.params.put("username",username);
        this.send(Grasscutter.getGsonFactory().toJson(rpcRequest));
        if (!waitForResponse(rpcRequest.id)) return null;
        String jsonResult = Grasscutter.getGsonFactory().toJson(responseSuccess.result);
        return Grasscutter.getGsonFactory().fromJson(jsonResult, new TypeToken<Account>(){}.getType());
    }

    public Account getAccountByToken(String token){
        RPCRequest rpcRequest = new RPCRequest();
        rpcRequest.method = "getAccountByToken";
        rpcRequest.params.put("token",token);
        this.send(Grasscutter.getGsonFactory().toJson(rpcRequest));
        if (!waitForResponse(rpcRequest.id)) return null;
        String jsonResult = Grasscutter.getGsonFactory().toJson(responseSuccess.result);
        return Grasscutter.getGsonFactory().fromJson(jsonResult, new TypeToken<Account>(){}.getType());
    }

    public Account getAccountBySessionKey(String sessionKey){
        RPCRequest rpcRequest = new RPCRequest();
        rpcRequest.method = "getAccountBySessionKey";
        rpcRequest.params.put("sessionKey",sessionKey);
        this.send(Grasscutter.getGsonFactory().toJson(rpcRequest));
        if (!waitForResponse(rpcRequest.id)) return null;
        String jsonResult = Grasscutter.getGsonFactory().toJson(responseSuccess.result);
        return Grasscutter.getGsonFactory().fromJson(jsonResult, new TypeToken<Account>(){}.getType());
    }

    public Account createAccountWithUid(String username, int reservedUid){
        RPCRequest rpcRequest = new RPCRequest();
        rpcRequest.method = "createAccountWithUid";
        rpcRequest.params.put("username",username);
        rpcRequest.params.put("uid",reservedUid);
        this.send(Grasscutter.getGsonFactory().toJson(rpcRequest));
        if (!waitForResponse(rpcRequest.id)) return null;
        String jsonResult = Grasscutter.getGsonFactory().toJson(responseSuccess.result);
        return Grasscutter.getGsonFactory().fromJson(jsonResult, new TypeToken<Account>(){}.getType());
    }

    public Account createAccount(String username){
        RPCRequest rpcRequest = new RPCRequest();
        rpcRequest.method = "createAccount";
        rpcRequest.params.put("username",username);
        this.send(Grasscutter.getGsonFactory().toJson(rpcRequest));
        if (!waitForResponse(rpcRequest.id)) return null;
        String jsonResult = Grasscutter.getGsonFactory().toJson(responseSuccess.result);
        return Grasscutter.getGsonFactory().fromJson(jsonResult, new TypeToken<Account>(){}.getType());
    }

    public void saveAccount(Account account){
        RPCRequest rpcRequest = new RPCRequest();
        rpcRequest.method = "saveAccount";
        rpcRequest.params.put("account",account);
        this.send(Grasscutter.getGsonFactory().toJson(rpcRequest));
    }

    public int getNextPlayerId(){
        RPCRequest rpcRequest = new RPCRequest();
        rpcRequest.method = "getNextPlayerId";
        this.send(Grasscutter.getGsonFactory().toJson(rpcRequest));
        if (!waitForResponse(rpcRequest.id)) return -1;
        String jsonResult = Grasscutter.getGsonFactory().toJson(responseSuccess.result);
        return Grasscutter.getGsonFactory().fromJson(jsonResult, new TypeToken<Integer>(){}.getType());
    }

    public int getNextAccountId(){
        RPCRequest rpcRequest = new RPCRequest();
        rpcRequest.method = "getNextAccountId";
        this.send(Grasscutter.getGsonFactory().toJson(rpcRequest));
        if (!waitForResponse(rpcRequest.id)) return -1;
        String jsonResult = Grasscutter.getGsonFactory().toJson(responseSuccess.result);
        return Grasscutter.getGsonFactory().fromJson(jsonResult, new TypeToken<Integer>(){}.getType());
    }

    public boolean addServerToDispatch(){
        RPCRequest rpcRequest = new RPCRequest();
        rpcRequest.method = "addServerToDispatch";
        rpcRequest.params.put("server",SERVER.dispatch.regions[0]);
        this.send(Grasscutter.getGsonFactory().toJson(rpcRequest));
        Grasscutter.getLogger().info(Grasscutter.getGsonFactory().toJson(rpcRequest));
        if (!waitForResponse(rpcRequest.id)) return false;
        String jsonResult = Grasscutter.getGsonFactory().toJson(responseSuccess.result);
        return Grasscutter.getGsonFactory().fromJson(jsonResult, new TypeToken<Boolean>(){}.getType());
    }

    public boolean isServerOnDispatch(){
        RPCRequest rpcRequest = new RPCRequest();
        rpcRequest.method = "isServerOnDispatch";
        this.send(Grasscutter.getGsonFactory().toJson(rpcRequest));
        if (!waitForResponse(rpcRequest.id)) return false;
        String jsonResult = Grasscutter.getGsonFactory().toJson(responseSuccess.result);
        return Grasscutter.getGsonFactory().fromJson(jsonResult, new TypeToken<Boolean>(){}.getType());
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
}
