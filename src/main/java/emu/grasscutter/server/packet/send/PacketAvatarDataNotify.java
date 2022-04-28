package emu.grasscutter.server.packet.send;

import java.util.Map.Entry;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.TeamInfo;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarDataNotifyOuterClass.AvatarDataNotify;
import emu.grasscutter.net.proto.AvatarTeamOuterClass.AvatarTeam;

public class PacketAvatarDataNotify extends BasePacket {
	
	public PacketAvatarDataNotify(Player player) {
		super(PacketOpcodes.AvatarDataNotify, 2);

		AvatarDataNotify.Builder proto = AvatarDataNotify.newBuilder()
				.setCurAvatarTeamId(player.getTeamManager().getCurrentTeamId())
				.setChooseAvatarGuid(player.getTeamManager().getCurrentCharacterGuid())
				.addAllOwnedFlycloakList(player.getFlyCloakList())
				.addAllOwnedCostumeList(player.getCostumeList());
				
		for (Avatar avatar : player.getAvatars()) {
			proto.addAvatarList(avatar.toProto());
		}
		
		for (Entry<Integer, TeamInfo> entry : player.getTeamManager().getTeams().entrySet()) {
			TeamInfo teamInfo = entry.getValue();
			AvatarTeam.Builder avatarTeam = AvatarTeam.newBuilder()
					.setTeamName(teamInfo.getName());
			
			for (int i = 0; i < teamInfo.getAvatars().size(); i++) {
				Avatar avatar = player.getAvatars().getAvatarById(teamInfo.getAvatars().get(i));
				avatarTeam.addAvatarGuidList(avatar.getGuid());
			}
			
			proto.putAvatarTeamMap(entry.getKey(), avatarTeam.build());
		}
		
		this.setData(proto.build());
	}

}
