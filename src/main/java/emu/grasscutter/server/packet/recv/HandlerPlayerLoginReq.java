package emu.grasscutter.server.packet.recv;

import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerLoginReqOuterClass.PlayerLoginReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.game.GameSession.SessionState;
import emu.grasscutter.server.packet.send.PacketPlayerLoginRsp;

@Opcodes(PacketOpcodes.PlayerLoginReq) // Sends initial data packets
public class HandlerPlayerLoginReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		// Check
		if (session.getAccount() == null) {
			return;
		}
		
		// Parse request
		PlayerLoginReq req = PlayerLoginReq.parseFrom(payload);
		
		// Authenticate session
		if (!req.getToken().equals(session.getAccount().getToken())) {
			return;
		}
		
		// Load character from db
		GenshinPlayer player = DatabaseHelper.getPlayerById(session.getAccount().getPlayerUid());
		
		if (player == null) {
			// Send packets
			session.setState(SessionState.PICKING_CHARACTER);
			session.send(new GenshinPacket(PacketOpcodes.DoSetPlayerBornDataNotify));
		} else {
			// Set character
			session.setPlayer(player);
			
			// Login done
			session.getPlayer().onLogin();
			session.setState(SessionState.ACTIVE);
		}

		// Final packet to tell client logging in is done
		session.send(new PacketPlayerLoginRsp(session));
	}

}
