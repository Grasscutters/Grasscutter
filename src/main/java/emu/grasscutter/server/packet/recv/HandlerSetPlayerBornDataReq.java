package emu.grasscutter.server.packet.recv;

import emu.grasscutter.GenshinConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GenshinData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SetPlayerBornDataReqOuterClass.SetPlayerBornDataReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.game.GameSession.SessionState;

@Opcodes(PacketOpcodes.SetPlayerBornDataReq)
public class HandlerSetPlayerBornDataReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		SetPlayerBornDataReq req = SetPlayerBornDataReq.parseFrom(payload);
		
		// Sanity checks
		int avatarId = req.getAvatarId();
		int startingSkillDepot = 0;
		if (avatarId == GenshinConstants.MAIN_CHARACTER_MALE) {
			startingSkillDepot = 504;
		} else if (avatarId == GenshinConstants.MAIN_CHARACTER_FEMALE) {
			startingSkillDepot = 704;
		} else {
			return;
		}
		
		String nickname = req.getNickName();
		if (nickname == null) {
			nickname = "Traveler";
		}
		
		// Create character
		GenshinPlayer player = new GenshinPlayer(session);
		player.setNickname(nickname);
		
		try {
			// Save to db
			DatabaseHelper.createPlayer(player, session.getAccount().getPlayerUid());
			
			// Create avatar
			if (player.getAvatars().getAvatarCount() == 0) {
				GenshinAvatar mainCharacter = new GenshinAvatar(avatarId);
				mainCharacter.setSkillDepot(GenshinData.getAvatarSkillDepotDataMap().get(startingSkillDepot));
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
			session.send(new GenshinPacket(PacketOpcodes.SetPlayerBornDataRsp));
		} catch (Exception e) {
			Grasscutter.getLogger().error("Error creating player object: ", e);
			session.close();
		}
	}

}
