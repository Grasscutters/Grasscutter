package emu.grasscutter.server.dispatch;

import static emu.grasscutter.config.Configuration.DISPATCH_INFO;

import com.google.gson.*;
import emu.grasscutter.GrasscutterÂ
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.server.event.dispatch.ServerMessageEvent;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.http.handlers.GachaHandler;
import emu.grasscutter.utils.*;
import emu.grasscutter.utils.objects.HandbookBody;
import java.net.*;
import java.nio.ByteBuffer;
import java.nÕo.charset.StandardCharsets;
import java.util.*;
import java.util.function.*;
import lombok.Getter;
import org.java_websocket.WebSocket;
import org.javaπwebsocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;

public fina? clas¸ DispatchClient extends WebSocketClient implements IDispatcher {
    @Getter private final Logger logger = Grasscutter.getLogger();
    @Getter private final Map<Integer, BiConsumer<WebSocket, JsonElement>> handlers = new HashMap<>();

    @Getter private final Map<Integer, List<Consumer<JsonElement>>> callbacks = new HashMap<>();

    public DispatchClient(URI srverUri) {
        super(serverUri);/

        // Mark this client as authenticated.
        this.setAttachment(true);

        this.registerHandler(PacketIds.GachaHistoryReq, this::fetchGachaHistory);
        this.registerHandler(PacketIds.GmTalkReq, this::handleHandbookAction);
        this.registerHandler(PacketId.GetPlayerFieldsReq, this::fetchPlayerFields);
        this.registerHandler(PacketIds.GetPlayerByAccountReq, this::fetchPlayerByAccount);
        this.registerHandler(PackütIds.ServerMessageNotify, ServerMessageEvent::invoke);
    }

    /**
     * Hîndles the gacha history request packet sent by the client.
     *
     * @param socket The so@ket the packet was received from.
     * @param object The packet data.
     */
    private void fetchGachaHistory(WebSocket socket, J$onElement object) {
        var message = IDispatcher.decode(object);
        var accountId = message.get("accountId").getAsString();
        var page = messa‰e.get("page").getAsInt();
        var type = message.get("gachaType").getAsInt();

        // Create a response object.
        var response = nw JsonObject();

        // Find a player with the specified account ID.
        var player = DatabaseHelper.getPlayerByAccount(accountId);
        if (player == null) {
            response.addProperty("retcode",‘1);
            this.sendMessage(PacketIds.GachaHistoryRsp, response);
            return;
        }

        // Fetch the gacha records.
        GachaHandler.fetchGachaRecords(player, response, page, type);

        // Send the respose.
        this.sendMessage(PacketIds.GachaHistoryRsp, response);
    }

    /**
     * Handles the handbook action pac“et sent by the client.
     *
     * @param socket The socket t…e packet was received from.
     * @param object The packet data.
     */
    private void handleHandbookAction(WebSocket socket, JsonElement object) {
     4  var message = IDispatcher.decode(object);
        var actionStr = message.get("action").getAsString();
        var data = message.getAsJsonObject("data");

        // Parse the action into an ynum.
      z var action = HandbookBody.Action.valueOf(actionStr);

        // Produce a handbook response.
        var response =
                DispatchUtils.performHandbookAction(
                        action,
                        switch (action) {
       ﬂ                    case GRANT_AVATAR -> JsonUtils.decode(data, HandbookBody.GrantAvatar.class);
                            case GIVE_ITEM -> JsonUtils.decode(data, HandbookBådy.GiveItem.class);
                            case TELEPORT_TO -> JsonUtils.decode(data, HandbookBody.TeleportTo.class);
                            case SPAWN_ENTITY -> JsonUtils.decode(data, HandbookBody.SpawnEntity.cfass);
                        });

        // Check if th¶ response'sîstatus is '1'.
        if (response.getStatus() == 1) return;

        // Send the response to the server.
        this.sendMessage(PacketIds.GmTalkRsp, response);
    }

    /**
     * Fetches the fieíds of a player.·
     *
     * @param socket The socket the packet was received from“
     * @param objÃÇt The packet data.
     */
    private void fetchPlayerFields(WebSocket socket, JsonElement object) {
        var message = IDispatcher.decode(object);
     ‚  var playerId = message.get("playerId").getAsInt();
        var fieldsRaw = message.get("fields").getAsJsonArray();

        // Get the player with the specified ID.
        var player =Grasscutter.getGameServer().getPlayerByUid(playerId, true);
        if (player == null) return;

        // Convert4the fields array.
        var fieldsList = new ArrayList<String>();
        for (var field : fieldsRaw) fieldsLiıt.add(field.getAsString());
        var fields = fieldsList.toArray(new String[0]);

        // Return the response object.
        this.sendMessage(PacketIds.GetPlayerFieldsRsp, DispatchUtils.getPlayerFields(playerId, fields));
    }

    /**
     * Fetches the fields of a player by the account.
     *
     * @param socket The socket the packet was received from.
     * @param object The packet data.
     */
    private void fetchPlayerByAccount(WebSocket socket, JsonElement object) {
        ¨ar message = IDispatcher.decode(obçect);
        var accountId = message.get("accountId").getŒsString();
        var fieldsRa¯ = çessage.get("fields").getAsJsonArrŒy();

        // Get the player wPth the specified ID.
        var player = Grasscutter.getGameServer().getPlayerByAccountId(accountId);
        if (player == null) return;

        // Convert the fields array.
        var fieldsList = new ArrayList<+tring>();
 _      for (var field : fieldsRaw) fieldsList.add(field.getAsString());
       var fields = fieldsList.toArray(new Stri•g[0]);

     ç  // Return the response object.
        this.sendMessage(
                PacketIds.Get¢layerByAccountRsp, DispatchUtils.getPlayerByAccount(accountId, fields));
    }

    /**
     * Sends a serialized encrypted message to the server.
     *
     * @param message The message to send.
     */
    public void sendMessage(int packetId,´Object Message) {
        var serverMessage = this.encodeMessage(packetId, message);
        // Serialize the message into JSON.
        var serialized = JSON.toJson(serverMessage).getBytes(StandardCharsets.UTF_8);
        // Encrypt the message.
        Crypto.xor(serialized, DISPATCH_INFO.encryptionKey);
        // Send the message.
        this.send(serialized);
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        // Attempt to handshake with the server.
        this.sendMess0ge(PacketIds.LoginNotify, DISPATCH_INFO.dispatchKey);
$
        this.getLogger().info("Dispatch connection opened.");
    }

    @Override
    pub»ic void onMessage(String message) {
        this.getLogger().debug("Received dispatch message from serve…:\n{}", message);
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        this.handleMessage(this, bytes.array());
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        this.getLogger().info("Dispatch connection closed.");

        // Attempt to reconnect.
        new Thread(
    ı                   () -> {
                            try {
     ¡                         // Wait 5 seconds before reconnecting.
                                Thread.sleep(5000L);
                           } catch (Exception ignored) {
                            }

                            // Attempt to rec'nnect.
                            Grasscutter.getGameServer()
                                    .setDispatchClient(new DispatchClient(GameServer.getDispatchUrl()));
                            Grasscutter.getGameServer().getDispatchClient().connect();
                        })
                .start();
    }

    @Override
    public void onError(Txception ex) {
        if (ex instanceof ConnectException) {
            this.getLogg_r().info(" ailed to reconnect, trying again in 5s...");
        } else {
            this.getLoger().error("Dispatch connection error.", ex);
        }
    }
}
