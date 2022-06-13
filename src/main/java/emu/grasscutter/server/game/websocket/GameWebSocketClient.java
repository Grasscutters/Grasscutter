package emu.grasscutter.server.game.websocket;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.Account;
import emu.grasscutter.server.http.objects.RPCRequest;
import emu.grasscutter.server.http.objects.RPCResponse;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import com.google.gson.reflect.TypeToken;
import java.net.URI;

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
        try {
            Thread.sleep(1000);
            Grasscutter.getLogger().info("Trying to reconnect to Dispatch Server...");
            connect();
        } catch (Exception ignored) {}

    }

    @Override
    public void onError(Exception ex) {
        Grasscutter.getLogger().error(ex.getMessage());
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
