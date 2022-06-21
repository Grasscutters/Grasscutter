package emu.grasscutter.server.game;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerDebugMode;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.packet.PacketOpcodesUtil;
import emu.grasscutter.server.event.game.SendPacketEvent;
import emu.grasscutter.utils.Crypto;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.Set;

import static emu.grasscutter.Configuration.PACKET;
import static emu.grasscutter.Configuration.SERVER;
import static emu.grasscutter.utils.Language.translate;

public class GameSession implements GameSessionManager.KcpChannel {
    private final GameServer server;
    private GameSessionManager.KcpTunnel tunnel;

    private Account account;
    private Player player;

    private boolean useSecretKey;
    private SessionState state;

    private int clientTime;
    private long lastPingTime;
    private int lastClientSeq = 10;

    public GameSession(GameServer server) {
        this.server = server;
        this.state = SessionState.WAITING_FOR_TOKEN;
        this.lastPingTime = System.currentTimeMillis();
    }

    public GameServer getServer() {
        return this.server;
    }

    public InetSocketAddress getAddress() {
        try {
            return this.tunnel.getAddress();
        } catch (Throwable ignore) {
            return null;
        }
    }

    public boolean useSecretKey() {
        return this.useSecretKey;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAccountId() {
        return this.getAccount().getId();
    }

    public Player getPlayer() {
        return this.player;
    }

    public synchronized void setPlayer(Player player) {
        this.player = player;
        this.player.setSession(this);
        this.player.setAccount(this.getAccount());
    }

    public SessionState getState() {
        return this.state;
    }

    public void setState(SessionState state) {
        this.state = state;
    }

    public boolean isLoggedIn() {
        return this.getPlayer() != null;
    }

    public void setUseSecretKey(boolean useSecretKey) {
        this.useSecretKey = useSecretKey;
    }

    public int getClientTime() {
        return this.clientTime;
    }

    public long getLastPingTime() {
        return this.lastPingTime;
    }

    public void updateLastPingTime(int clientTime) {
        this.clientTime = clientTime;
        this.lastPingTime = System.currentTimeMillis();
    }

    public int getNextClientSequence() {
        return ++this.lastClientSeq;
    }

    public void replayPacket(int opcode, String name) {
        String filePath = PACKET(name);
        File p = new File(filePath);

        if (!p.exists()) return;

        byte[] packet = FileUtils.read(p);

        BasePacket basePacket = new BasePacket(opcode);
        basePacket.setData(packet);

        this.send(basePacket);
    }

    public void send(BasePacket packet) {
        // Test
        if (packet.getOpcode() <= 0) {
            Grasscutter.getLogger().warn("Tried to send packet with missing cmd id!");
            return;
        }

        // DO NOT REMOVE (unless we find a way to validate code before sending to client which I don't think we can)
        // Stop WindSeedClientNotify from being sent for security purposes.
        if (PacketOpcodes.BANNED_PACKETS.contains(packet.getOpcode())) {
            return;
        }

        // Header
        if (packet.shouldBuildHeader()) {
            packet.buildHeader(this.getNextClientSequence());
        }

        // Log
        if (SERVER.debugLevel == ServerDebugMode.ALL) {
            if (!loopPacket.contains(packet.getOpcode())) {
                Grasscutter.getLogger().info("SEND: " + PacketOpcodesUtil.getOpcodeName(packet.getOpcode()) + " (" + packet.getOpcode() + ")");
                System.out.println(Utils.bytesToHex(packet.getData()));
            }
        }

        // Invoke event.
        SendPacketEvent event = new SendPacketEvent(this, packet);
        event.call();
        if (!event.isCanceled()) { // If event is not cancelled, continue.
            this.tunnel.writeData(event.getPacket().build());
        }
    }

    private static final Set<Integer> loopPacket = Set.of(
        PacketOpcodes.PingReq,
        PacketOpcodes.PingRsp,
        PacketOpcodes.WorldPlayerRTTNotify,
        PacketOpcodes.UnionCmdNotify,
        PacketOpcodes.QueryPathReq
    );

    @Override
    public void onConnected(GameSessionManager.KcpTunnel tunnel) {
        this.tunnel = tunnel;
        Grasscutter.getLogger().info(translate("messages.game.connect", this.getAddress().toString()));
    }


    @Override
    public void handleReceive(byte[] bytes) {
        // Decrypt and turn back into a packet
        Crypto.xor(bytes, this.useSecretKey() ? Crypto.ENCRYPT_KEY : Crypto.DISPATCH_KEY);
        ByteBuf packet = Unpooled.wrappedBuffer(bytes);

        // Log
        //logPacket(packet);
        // Handle
        try {
            boolean allDebug = SERVER.debugLevel == ServerDebugMode.ALL;
            while (packet.readableBytes() > 0) {
                // Length
                if (packet.readableBytes() < 12) {
                    return;
                }
                // Packet sanity check
                int const1 = packet.readShort();
                if (const1 != 17767) {
                    if (allDebug) {
                        Grasscutter.getLogger().error("Bad Data Package Received: got {} ,expect 17767", const1);
                    }
                    return; // Bad packet
                }
                // Data
                int opcode = packet.readShort();
                int headerLength = packet.readShort();
                int payloadLength = packet.readInt();
                byte[] header = new byte[headerLength];
                byte[] payload = new byte[payloadLength];

                packet.readBytes(header);
                packet.readBytes(payload);
                // Sanity check #2
                int const2 = packet.readShort();
                if (const2 != -30293) {
                    if (allDebug) {
                        Grasscutter.getLogger().error("Bad Data Package Received: got {} ,expect -30293", const2);
                    }
                    return; // Bad packet
                }
                // Log packet
                if (allDebug) {
                    if (!loopPacket.contains(opcode)) {
                        Grasscutter.getLogger().info("RECV: " + PacketOpcodesUtil.getOpcodeName(opcode) + " (" + opcode + ")");
                        System.out.println(Utils.bytesToHex(payload));
                    }
                }
                // Handle
                this.getServer().getPacketHandler().handle(this, opcode, header, payload);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //byteBuf.release(); //Needn't
            packet.release();
        }
    }

    @Override
    public void handleClose() {
        this.setState(SessionState.INACTIVE);
        //send disconnection pack in case of reconnection
        Grasscutter.getLogger().info(translate("messages.game.disconnect", this.getAddress().toString()));
        // Save after disconnecting
        if (this.isLoggedIn()) {
            Player player = this.getPlayer();
            // Call logout event.
            player.onLogout();
        }
        try {
            this.send(new BasePacket(PacketOpcodes.ServerDisconnectClientNotify));
        } catch (Throwable ignore) {
            Grasscutter.getLogger().warn("closing {} error", this.getAddress().getAddress().getHostAddress());
        }
        this.tunnel = null;
    }

    public void close() {
        this.tunnel.close();
    }

    public boolean isActive() {
        return this.getState() == SessionState.ACTIVE;
    }

    public enum SessionState {
        INACTIVE,
        WAITING_FOR_TOKEN,
        WAITING_FOR_LOGIN,
        PICKING_CHARACTER,
        ACTIVE
    }
}
