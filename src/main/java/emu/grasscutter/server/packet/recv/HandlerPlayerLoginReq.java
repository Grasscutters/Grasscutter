package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerLoginReqOuterClass.PlayerLoginReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.game.GameSession.SessionState;
import emu.grasscutter.server.packet.send.PacketPlayerLoginRsp;
import emu.grasscutter.server.packet.send.PacketTakeAchievementRewardReq;
import emu.grasscutter.utils.ShowInfosUtil;
import emu.grasscutter.utils.Utils;

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
		Player player = DatabaseHelper.getPlayerById(session.getAccount().getPlayerUid());
		
		if (player == null) {
			// Send packets
			session.setState(SessionState.PICKING_CHARACTER);
			session.send(new BasePacket(PacketOpcodes.DoSetPlayerBornDataNotify));
		} else {
			// Set character
			session.setPlayer(player);
			
			// Login done
			session.getPlayer().onLogin();
			session.setState(SessionState.ACTIVE);

			// Solve the problem that the show command may not have the corresponding language cache information when used for the first time.
			ShowInfosUtil.initShowInfoMap(Utils.getLanguageCode(session.getAccount().getLocale()), Utils.getLanguageCode((Grasscutter.getConfig().language.language)));
		}

		// Final packet to tell client logging in is done
		session.send(new PacketPlayerLoginRsp(session));
		session.send(new PacketTakeAchievementRewardReq(session));
	}

}
