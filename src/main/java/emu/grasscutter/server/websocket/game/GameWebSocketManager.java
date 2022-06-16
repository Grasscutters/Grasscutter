package emu.grasscutter.server.websocket.game;

import emu.grasscutter.Grasscutter;

import java.net.URI;

import static emu.grasscutter.Configuration.HTTP_INFO;
import static emu.grasscutter.Configuration.SERVER;

public class GameWebSocketManager {
    private GameWebSocketClient gameWebSocketClient;
    private boolean isGameWebSocketClientConnected;
    public GameWebSocketManager() {
        isGameWebSocketClientConnected = false;
        String scheme  = HTTP_INFO.encryption.useEncryption || HTTP_INFO.encryption.useInRouting ? "wss://":"ws://";
        URI websocketURI = null;
        if (SERVER.dispatch.regions.length != 1){
            Grasscutter.getLogger().error("GAME_ONLY mode require to set exactly 1 region.");
            System.exit(1);
        }
        try {
            websocketURI = new URI(scheme+HTTP_INFO.accessAddress+":"+HTTP_INFO.accessPort+"/websocket?key="+SERVER.dispatch.key);
        } catch (Exception ignored) {
            Grasscutter.getLogger().error("Error connecting to Dispatch Server Websocket! Make sure your dispatch server is already up.");
        }
        URI finalWebsocketURI = websocketURI;
        new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(5000);
                    if (!this.isGameWebSocketClientConnected){
                        this.gameWebSocketClient = new GameWebSocketClient(finalWebsocketURI);
                        this.gameWebSocketClient.connect();
                    }
                    if (this.gameWebSocketClient.isOpen() && !this.gameWebSocketClient.isServerOnDispatch()){
                        if (this.gameWebSocketClient.addServerToDispatch()) {
                            Grasscutter.getLogger().info("Added to Dispatch Server!.");
                        } else {
                            Grasscutter.getLogger().error("Failed to add to Dispatch Server!.");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public boolean isGameWebSocketClientConnected() {
        return isGameWebSocketClientConnected;
    }
    public void setIsGameWebSocketClientConnected(boolean isGameWebSocketClientConnected) {
        this.isGameWebSocketClientConnected = isGameWebSocketClientConnected;
    }

    public GameWebSocketClient getGameWebSocketClient() {
        return gameWebSocketClient;
    }
}
