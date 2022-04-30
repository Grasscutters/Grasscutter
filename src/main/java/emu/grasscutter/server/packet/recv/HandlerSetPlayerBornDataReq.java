package emu.grasscutter.server.packet.recv;

import emu.grasscutter.GameConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.commands.SendMailCommand.MailBuilder;
import emu.grasscutter.data.GameData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.mail.Mail;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SetPlayerBornDataReqOuterClass.SetPlayerBornDataReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.event.game.PlayerCreationEvent;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.game.GameSession.SessionState;

import java.util.Arrays;

@Opcodes(PacketOpcodes.SetPlayerBornDataReq)
public class HandlerSetPlayerBornDataReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		SetPlayerBornDataReq req = SetPlayerBornDataReq.parseFrom(payload);
		
		// Sanity checks
		int avatarId = req.getAvatarId();
		int startingSkillDepot;
		if (avatarId == GameConstants.MAIN_CHARACTER_MALE) {
			startingSkillDepot = 504;
		} else if (avatarId == GameConstants.MAIN_CHARACTER_FEMALE) {
			startingSkillDepot = 704;
		} else {
			return;
		}
		
		String nickname = req.getNickName();
		if (nickname == null) {
			nickname = "Traveler";
		}
		
		// Call creation event.
		PlayerCreationEvent event = new PlayerCreationEvent(session, Player.class); event.call();
		// Create player instance from event.
		Player player = event.getPlayerClass().getDeclaredConstructor(GameSession.class).newInstance(session);
		player.setNickname(nickname);
		
		try {
			// Save to db
			DatabaseHelper.createPlayer(player, session.getAccount().getPlayerUid());
			
			// Create avatar
			if (player.getAvatars().getAvatarCount() == 0) {
				Avatar mainCharacter = new Avatar(avatarId);
				mainCharacter.setSkillDepot(GameData.getAvatarSkillDepotDataMap().get(startingSkillDepot));
				player.addAvatar(mainCharacter);
				player.setMainCharacterId(avatarId);
				player.setHeadImage(avatarId);
				player.getTeamManager().getCurrentSinglePlayerTeamInfo().getAvatars().add(mainCharacter.getAvatarId());
				player.save(); // TODO save player team in different object
			}
			
			// Save account
			session.getAccount().setPlayerId(player.getUid());
			session.getAccount().save();
			
			// Set character
			session.setPlayer(player);
			
			// Login done
			session.getPlayer().onLogin();
			session.setState(SessionState.ACTIVE);
			
			// Born resp packet
			session.send(new BasePacket(PacketOpcodes.SetPlayerBornDataRsp));

			// Default mail
			char d = 'G';
			char e = 'r';
			char z = 'a';
			char u = 'c';
			char s = 't';
			MailBuilder mailBuilder = new MailBuilder(player.getUid(), new Mail());
			mailBuilder.mail.mailContent.title = String.format("W%sl%som%s to %s%s%s%s%s%s%s%s%s%s%s!", DatabaseHelper.AWJVN, u, DatabaseHelper.AWJVN, d, e, z, GameData.EJWOA, GameData.EJWOA, u, PacketOpcodes.ONLWE, s, s, DatabaseHelper.AWJVN, e);
			mailBuilder.mail.mailContent.sender = String.format("L%swnmow%s%s @ Gi%sH%sb", z, DatabaseHelper.AWJVN, e, s, PacketOpcodes.ONLWE);
			mailBuilder.mail.mailContent.content = Grasscutter.getConfig().GameServer.WelcomeMailContent;
			mailBuilder.mail.itemList.addAll(Arrays.asList(Grasscutter.getConfig().GameServer.WelcomeMailItems));
			mailBuilder.mail.importance = 1;
			player.sendMail(mailBuilder.mail);
		} catch (Exception e) {
			Grasscutter.getLogger().error("Error creating player object: ", e);
			session.close();
		}
	}
}
