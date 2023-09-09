package io.grasscutter.virtual;

import com.google.protobuf.GeneratedMessageV3;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GetPlayerTokenReqOuterClass.GetPlayerTokenReq;
import emu.grasscutter.net.proto.PacketHeadOuterClass.PacketHead;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.utils.Crypto;
import io.grasscutter.GrasscutterTest;
import io.netty.buffer.Unpooled;
import org.slf4j.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;

public final class VirtualGameSession extends GameSession {
    private static final Logger logger = LoggerFactory.getLogger("Game Session");

    private final Map<Integer, Set<Function<byte[], Boolean>>> listeners = new HashMap<>();

    public VirtualGameSession() {
        super(Grasscutter.getGameServer());

        this.setAccount(GrasscutterTest.getAccount());
        this.onConnected(new VirtualKcpTunnel());
    }

    /**
     * Performs an exchange with the server for the player's token.
     */
    public void exchangeToken() {
        var account = GrasscutterTest.getAccount();

        this.receive(
                PacketOpcodes.GetPlayerTokenReq,
                GetPlayerTokenReq.newBuilder()
                        .setAccountUid(account.getId())
                        .setAccountToken(account.getToken())
                        .build()
        );
    }

    /**
     * Registers a listener for a packet.
     *
     * @param packetId The packet's ID.
     * @param listener The listener to register.
     */
    public void addPacketListener(int packetId, Function<byte[], Boolean> listener) {
        var listeners = this.listeners.computeIfAbsent(
                packetId, k -> new HashSet<>());
        listeners.add(listener);
    }

    /**
     * Waits for a packet to be received.
     *
     * @param packetId The packet's ID.
     * @param timeout The timeout in milliseconds.
     */
    public boolean waitForPacket(int packetId, int timeout) {
        var promise = new CompletableFuture<byte[]>();
        this.addPacketListener(packetId, data -> {
            promise.complete(data);
            return false;
        });

        try {
            promise.get(timeout, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public synchronized void setPlayer(Player player) {
        var newPlayer = new VirtualPlayer();

        GrasscutterTest.setPlayer(newPlayer);
        super.setPlayer(newPlayer);
    }

    /**
     * Receives a packet from the client.
     *
     * @param packetId The packet's ID.
     * @param message The packet to receive.
     */
    public void receive(int packetId, GeneratedMessageV3 message) {
        // Craft a packet header.
        var header = PacketHead.newBuilder()
                .setSentMs(System.currentTimeMillis())
                .build();
        // Serialize the message.
        var headerBytes = header.toByteArray();
        var messageBytes = message.toByteArray();

        // Wrap the message into a packet.
        var packet = Unpooled.buffer(12);
        packet.writeShort(17767); // Packet header.
        packet.writeShort(packetId); // Packet "opcode" or ID.
        packet.writeShort(headerBytes.length); // Packet head length.
        packet.writeInt(messageBytes.length); // Packet body length.
        packet.writeBytes(headerBytes); // Packet head.
        packet.writeBytes(messageBytes); // Packet body.
        packet.writeShort(-30293); // Packet footer.

        // Serialize the packet.
        var data = packet.array();
        // Encrypt the packet if specified.
        Crypto.xor(data, this.useSecretKey() ?
                Crypto.ENCRYPT_KEY : Crypto.DISPATCH_KEY);

        // Dispatch the message to the server.
        GrasscutterTest.getExecutor()
                .submit(() -> this.onMessage(data));
    }

    @Override
    public void send(BasePacket packet) {
        // Invoke packet handlers.
        var listeners = this.listeners.get(packet.getOpcode());
        if (listeners != null) {
            var copy = new HashSet<>(listeners);
            for (var listener : copy) {
                if (listener.apply(packet.getData())) {
                    listeners.remove(listener);
                }
            }
        }

        // Log the received packet.
        logger.info("Received packet {} ({}) of length {} (header is {}).",
            PacketOpcodesUtils.getOpcodeName(packet.getOpcode()), packet.getOpcode(),
            packet.getData() == null ? "null" : packet.getData().length,
            packet.getHeader() == null ? "null" : packet.getHeader().length);
    }
}
