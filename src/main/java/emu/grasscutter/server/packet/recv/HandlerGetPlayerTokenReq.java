package emu.grasscutter.server.packet.recv;

import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetPlayerTokenReqOuterClass.GetPlayerTokenReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.game.GameSession.SessionState;
import emu.grasscutter.server.packet.send.PacketGetPlayerTokenRsp;

@Opcodes(PacketOpcodes.GetPlayerTokenReq)
public class HandlerGetPlayerTokenReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		GetPlayerTokenReq req = GetPlayerTokenReq.parseFrom(payload);
		
		// Authenticate
		Account account = DatabaseHelper.getAccountById(req.getAccountUid());
		if (account == null) {
			return;
		}
		
		// Check token
		if (!account.getToken().equals(req.getAccountToken())) {
			return;
		}
		
		// Set account
		session.setAccount(account);
		session.setUseSecretKey(true);
		session.setState(SessionState.WAITING_FOR_LOGIN);
		
		// Has character
		boolean doesPlayerExist = false;
		if (account.getPlayerUid() > 0) {
			// Set flag for player existing
			doesPlayerExist = DatabaseHelper.checkPlayerExists(account.getPlayerUid());
		}
		
		// Set reserve player id if account doesnt exist
		if (!doesPlayerExist) {
			int id = DatabaseHelper.getNextPlayerId(session.getAccount().getPlayerUid());
			if (id != session.getAccount().getPlayerUid()) {
				session.getAccount().setPlayerId(id);
				session.getAccount().save();
			}
		}

		// Send packet
		session.send(new PacketGetPlayerTokenRsp(session, doesPlayerExist));
	}

}
