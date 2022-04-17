package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.TeamInfo;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SetUpAvatarTeamRspOuterClass.SetUpAvatarTeamRsp;

public class PacketSetUpAvatarTeamRsp extends GenshinPacket {
	
	public PacketSetUpAvatarTeamRsp(GenshinPlayer player, int teamId, TeamInfo teamInfo) {
		super(PacketOpcodes.SetUpAvatarTeamRsp);

		SetUpAvatarTeamRsp.Builder proto = SetUpAvatarTeamRsp.newBuilder()
				.setTeamId(teamId)
				.setCurAvatarGuid(player.getTeamManager().getCurrentCharacterGuid());
		
		for (int avatarId : teamInfo.getAvatars()) {
			proto.addAvatarTeamGuidList(player.getAvatars().getAvatarById(avatarId).getGuid());
		}
		
		this.setData(proto);
	}
}
