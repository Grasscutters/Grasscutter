package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.TeamInfo;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ChangeMpTeamAvatarRspOuterClass.ChangeMpTeamAvatarRsp;

public class PacketChangeMpTeamAvatarRsp extends BasePacket {

    public PacketChangeMpTeamAvatarRsp(Player player, TeamInfo teamInfo) {
        super(PacketOpcodes.ChangeMpTeamAvatarRsp);

        ChangeMpTeamAvatarRsp.Builder proto = ChangeMpTeamAvatarRsp.newBuilder()
            .setCurAvatarGuid(player.getTeamManager().getCurrentCharacterGuid());

        for (int avatarId : teamInfo.getAvatars()) {
            proto.addAvatarGuidList(player.getAvatars().getAvatarById(avatarId).getGuid());
        }

        this.setData(proto);
    }
}
