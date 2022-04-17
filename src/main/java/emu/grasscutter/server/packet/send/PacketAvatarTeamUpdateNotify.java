package emu.grasscutter.server.packet.send;

import java.util.Map.Entry;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.TeamInfo;
import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarTeamOuterClass.AvatarTeam;
import emu.grasscutter.net.proto.AvatarTeamUpdateNotifyOuterClass.AvatarTeamUpdateNotify;

public class PacketAvatarTeamUpdateNotify extends GenshinPacket {
	
	public PacketAvatarTeamUpdateNotify(GenshinPlayer player) {
		super(PacketOpcodes.AvatarTeamUpdateNotify);

		AvatarTeamUpdateNotify.Builder proto = AvatarTeamUpdateNotify.newBuilder();
		
		for (Entry<Integer, TeamInfo> entry : player.getTeamManager().getTeams().entrySet()) {
			TeamInfo teamInfo = entry.getValue();
			AvatarTeam.Builder avatarTeam = AvatarTeam.newBuilder()
					.setTeamName(teamInfo.getName());
			
			for (int i = 0; i < teamInfo.getAvatars().size(); i++) {
				GenshinAvatar avatar = player.getAvatars().getAvatarById(teamInfo.getAvatars().get(i));
				avatarTeam.addAvatarGuidList(avatar.getGuid());
			}
			
			proto.putAvatarTeamMap(entry.getKey(), avatarTeam.build());
		}
		
		this.setData(proto);
	}
}
