package emu.grasscutter.server.packet.recv;

import static emu.grasscutter.Configuration.ACCOUNT;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetPlayerTokenReqOuterClass.GetPlayerTokenReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.event.game.PlayerCreationEvent;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.game.GameSession.SessionState;
import emu.grasscutter.server.packet.send.PacketGetPlayerTokenRsp;

@Opcodes(PacketOpcodes.GetPlayerTokenReq)
public class HandlerGetPlayerTokenReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		// Max players limit
		if (ACCOUNT.maxPlayer > -1 && Grasscutter.getGameServer().getPlayers().size() >= ACCOUNT.maxPlayer) {
			session.close();
			return;
		}
		
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
		
		// Get player
		Player player = DatabaseHelper.getPlayerByAccount(account);

		if (player == null) {
			int nextPlayerUid = DatabaseHelper.getNextPlayerId(session.getAccount().getReservedPlayerUid());
			
			// Call creation event.
			PlayerCreationEvent event = new PlayerCreationEvent(session, Player.class); event.call();
			
			// Create player instance from event.
			player = event.getPlayerClass().getDeclaredConstructor(GameSession.class).newInstance(session);
			
			// Save to db
			DatabaseHelper.generatePlayerUid(player, nextPlayerUid);
		}
		
		// Set player object for session
		session.setPlayer(player);
		
		// Load player from database
		player.loadFromDatabase();
		
		// Set session state
		session.setUseSecretKey(true);
		session.setState(SessionState.WAITING_FOR_LOGIN);

		// Send packet
		session.send(new PacketGetPlayerTokenRsp(session));
	}

}
