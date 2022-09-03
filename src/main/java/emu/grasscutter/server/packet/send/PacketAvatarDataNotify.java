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
        super(PacketOpcodes.AvatarDataNotify, true);

        AvatarDataNotify.Builder proto = AvatarDataNotify.newBuilder()
                .setCurAvatarTeamId(player.getTeamManager().getCurrentTeamId())
                //.setChooseAvatarGuid(player.getTeamManager().getCurrentCharacterGuid())
                .addAllOwnedFlycloakList(player.getFlyCloakList())
                .addAllOwnedCostumeList(player.getCostumeList());

        for (Avatar avatar : player.getAvatars()) {
            proto.addAvatarList(avatar.toProto());
        }

        // Add the id list for custom teams.
        for (int id : player.getTeamManager().getTeams().keySet()) {
            if (id > 4) {
                proto.addCustomTeamIds(id);
            }
        }

        for (Entry<Integer, TeamInfo> entry : player.getTeamManager().getTeams().entrySet()) {
            TeamInfo teamInfo = entry.getValue();
            proto.putAvatarTeamMap(entry.getKey(), teamInfo.toProto(player));
        }

        // Set main character
        Avatar mainCharacter = player.getAvatars().getAvatarById(player.getMainCharacterId());
        if (mainCharacter != null) {
            proto.setChooseAvatarGuid(mainCharacter.getGuid());
        }

        this.setData(proto.build());
    }

}
