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
		
		// Make sure resources folder is set
		if (!GameData.getAvatarDataMap().containsKey(avatarId)) {
			Grasscutter.getLogger().error("No avatar data found! Please check your ExcelBinOutput folder.");
			session.close();
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
			MailBuilder mailBuilder = new MailBuilder(player.getUid(), new Mail());
			mailBuilder.mail.mailContent.title = Grasscutter.getConfig().GameServer.WelcomeMailTitle;
			mailBuilder.mail.mailContent.sender = Grasscutter.getConfig().GameServer.WelcomeMailSender;
			// Please credit Grasscutter if changing something here. We don't condone commercial use of the project.
			mailBuilder.mail.mailContent.content = Grasscutter.getConfig().GameServer.WelcomeMailContent + "\n<type=\"browser\" text=\"GitHub\" href=\"https://github.com/Melledy/Grasscutter\"/>";
			mailBuilder.mail.itemList.addAll(Arrays.asList(Grasscutter.getConfig().GameServer.WelcomeMailItems));
			mailBuilder.mail.importance = 1;
			player.sendMail(mailBuilder.mail);
		} catch (Exception e) {
			Grasscutter.getLogger().error("Error creating player object: ", e);
			session.close();
		}
	}
}
