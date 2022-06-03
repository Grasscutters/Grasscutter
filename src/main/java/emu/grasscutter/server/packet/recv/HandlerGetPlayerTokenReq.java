package emu.grasscutter.server.packet.recv;

import static emu.grasscutter.Configuration.ACCOUNT;

import java.util.Iterator;
import java.util.Map;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetPlayerTokenReqOuterClass.GetPlayerTokenReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.event.game.PlayerCreationEvent;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.game.GameSession.SessionState;
import emu.grasscutter.server.packet.send.PacketGetPlayerTokenRsp;

@Opcodes(PacketOpcodes.GetPlayerTokenReq)
public class HandlerGetPlayerTokenReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		GameServer server = Grasscutter.getGameServer();
		synchronized (server) {// in case of login simultaneously
			Map<Integer, Player> players = server.getPlayers();

			GetPlayerTokenReq req = GetPlayerTokenReq.parseFrom(payload);

			// Authenticate
			Account account = DatabaseHelper.getAccountByToken(req.getAccountToken());
			if (account == null) {
				session.close();
				return;
			}

			String username = account.getUsername();
			int reservedPlayerUid = account.getReservedPlayerUid();
			// force to make account offline , which logins already.
			Iterator<Map.Entry<Integer, Player>> it = players.entrySet().iterator();
			while(it.hasNext()){
				Player onlinePlayer = it.next().getValue();
				if(onlinePlayer.getAccount().getReservedPlayerUid() == reservedPlayerUid){
					onlinePlayer.onLogout();
					GameSession playerSession = onlinePlayer.getSession();
					playerSession.send(new BasePacket(PacketOpcodes.ServerDisconnectClientNotify));
					playerSession.close();
					it.remove();
					Grasscutter.getLogger().warn("Player {} was kicked by duplicated login", username);
					break;
				}
			}
			if (ACCOUNT.maxPlayer > -1 && players.size() >= ACCOUNT.maxPlayer) {// Max players limit ( a user maybe kicked from players )
				session.close();
				Grasscutter.getLogger().warn("Player {} was refused to login because of exceeding online players", username);
				return;
			}

			// Set account
			session.setAccount(account);

			// Get player
			Player player = DatabaseHelper.getPlayerByAccount(account);

			if (player == null) {
				int nextPlayerUid = DatabaseHelper.getNextPlayerId(session.getAccount().getReservedPlayerUid());

				// Call creation event.
				PlayerCreationEvent event = new PlayerCreationEvent(session, Player.class);
				event.call();

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

}
