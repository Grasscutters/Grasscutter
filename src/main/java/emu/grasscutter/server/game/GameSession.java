package emu.grasscutter.server.game;

import java.io.File;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerDebugMode;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.packet.PacketOpcodesUtil;
import emu.grasscutter.netty.KcpChannel;
import emu.grasscutter.server.event.game.SendPacketEvent;
import emu.grasscutter.utils.Crypto;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import static emu.grasscutter.utils.Language.translate;

public class GameSession extends KcpChannel {
	private GameServer server;
	
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
		return server;
	}
	
	public InetSocketAddress getAddress() {
		if (this.getChannel() == null) {
			return null;
		}
		return this.getChannel().remoteAddress();
	}

	public boolean useSecretKey() {
		return useSecretKey;
	}
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public String getAccountId() {
		return this.getAccount().getId();
	}

	public Player getPlayer() {
		return player;
	}

	public synchronized void setPlayer(Player player) {
		this.player = player;
		this.player.setSession(this);
		this.player.setAccount(this.getAccount());
	}

	public SessionState getState() {
		return state;
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
		return lastPingTime;
	}
	
	public void updateLastPingTime(int clientTime) {
		this.clientTime = clientTime;
		this.lastPingTime = System.currentTimeMillis();
	}
	
	public int getNextClientSequence() {
		return ++lastClientSeq;
	}
	
	@Override
	protected void onConnect() {
		Grasscutter.getLogger().info(translate("messages.game.connect", this.getAddress().getHostString().toLowerCase()));
	}

	@Override
	protected synchronized void onDisconnect() { // Synchronize so we don't add character at the same time.
		Grasscutter.getLogger().info(translate("messages.game.disconnect", this.getAddress().getHostString().toLowerCase()));

		// Set state so no more packets can be handled
		this.setState(SessionState.INACTIVE);
		
		// Save after disconnecting
		if (this.isLoggedIn()) {
			// Call logout event.
			getPlayer().onLogout();
			// Remove from server.
			getServer().getPlayers().remove(getPlayer().getUid());
		}
	}
	
    protected void logPacket(ByteBuffer buf) {
		ByteBuf b = Unpooled.wrappedBuffer(buf.array());
    	logPacket(b);
    }
    
    public void replayPacket(int opcode, String name) {
    	String filePath = Grasscutter.getConfig().PACKETS_FOLDER + name;
		File p = new File(filePath);
		
		if (!p.exists()) return;

		byte[] packet = FileUtils.read(p);
		
		BasePacket basePacket = new BasePacket(opcode);
		basePacket.setData(packet);
		
		send(basePacket);
    }
    
    public void send(BasePacket packet) {
    	// Test
    	if (packet.getOpcode() <= 0) {
    		Grasscutter.getLogger().warn("Tried to send packet with missing cmd id!");
    		return;
    	}

		// DO NOT REMOVE (unless we find a way to validate code before sending to client which I don't think we can)
		// Stop WindSeedClientNotify from being sent for security purposes.
		if(PacketOpcodes.BANNED_PACKETS.contains(packet.getOpcode())) {
			return;
		}
    	
    	// Header
    	if (packet.shouldBuildHeader()) {
    		packet.buildHeader(this.getNextClientSequence());
    	}
    	
    	// Log
    	if (Grasscutter.getConfig().DebugMode == ServerDebugMode.ALL) {
    		logPacket(packet);
    	}
		
		// Invoke event.
		SendPacketEvent event = new SendPacketEvent(this, packet); event.call();
    	if(!event.isCanceled()) // If event is not cancelled, continue.
			this.send(event.getPacket().build());
    }
    
	private static final Set<Integer> loopPacket = Set.of(
			PacketOpcodes.PingReq,
			PacketOpcodes.PingRsp,
			PacketOpcodes.WorldPlayerRTTNotify,
			PacketOpcodes.UnionCmdNotify,
			PacketOpcodes.QueryPathReq
	);

    private void logPacket(BasePacket packet) {
		if (!loopPacket.contains(packet.getOpcode())) {
			Grasscutter.getLogger().info("SEND: " + PacketOpcodesUtil.getOpcodeName(packet.getOpcode()) + " (" + packet.getOpcode() + ")");
			System.out.println(Utils.bytesToHex(packet.getData()));
		}
    }

	@Override
	public void onMessage(ChannelHandlerContext ctx, ByteBuf data) {
		// Decrypt and turn back into a packet
		byte[] byteData = Utils.byteBufToArray(data);
		Crypto.xor(byteData, useSecretKey() ? Crypto.ENCRYPT_KEY : Crypto.DISPATCH_KEY);
		ByteBuf packet = Unpooled.wrappedBuffer(byteData);
		
		// Log
		//logPacket(packet);
		
		// Handle
		try {
			while (packet.readableBytes() > 0) {
				// Length
				if (packet.readableBytes() < 12) {
					return;
				}
				
				// Packet sanity check
				int const1 = packet.readShort();
				if (const1 != 17767) {
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
					return; // Bad packet
				}
				
				// Log packet
				if (Grasscutter.getConfig().DebugMode == ServerDebugMode.ALL) {
					if (!loopPacket.contains(opcode)) {
						Grasscutter.getLogger().info("RECV: " + PacketOpcodesUtil.getOpcodeName(opcode) + " (" + opcode + ")");
						System.out.println(Utils.bytesToHex(payload));
					}
				}
				
				// Handle
				getServer().getPacketHandler().handle(this, opcode, header, payload);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			packet.release();
		}
	}
	
	public enum SessionState {
		INACTIVE,
		WAITING_FOR_TOKEN,
		WAITING_FOR_LOGIN,
		PICKING_CHARACTER,
		ACTIVE;
	}
}
