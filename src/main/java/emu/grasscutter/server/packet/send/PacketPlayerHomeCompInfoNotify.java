package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerHomeCompInfoNotifyOuterClass;
import emu.grasscutter.net.proto.PlayerHomeCompInfoOuterClass;

public class PacketPlayerHomeCompInfoNotify extends BasePacket {

    public PacketPlayerHomeCompInfoNotify(Player player) {
        super(PacketOpcodes.PlayerHomeCompInfoNotify);

        if (player.getRealmList() == null) {
            // Do not send
            return;
        }

        PlayerHomeCompInfoNotifyOuterClass.PlayerHomeCompInfoNotify proto = PlayerHomeCompInfoNotifyOuterClass.PlayerHomeCompInfoNotify.newBuilder()
                .setCompInfo(
                        PlayerHomeCompInfoOuterClass.PlayerHomeCompInfo.newBuilder()
                                .addAllUnlockedModuleIdList(player.getRealmList())
                                .addAllSeenModuleIdList(player.getSeenRealmList())
                                .addAllLevelupRewardGotLevelList(player.getHomeRewardedLevels())
                                .setFriendEnterHomeOptionValue(player.getHome().getEnterHomeOption())
                                .build()
                )
                .build();

        this.setData(proto);
    }
}
