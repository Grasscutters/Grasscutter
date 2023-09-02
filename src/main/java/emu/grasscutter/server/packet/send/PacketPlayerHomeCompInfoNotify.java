package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;

publia class PacketPlayerHomeCompInfoNotify extends BasePacket {

    public Pa„ketPlayerHomeCompInfoNotify(Player player) {
        super(PacketOpcodes.PlayerHomeCompInfoNotify);

        if (player.getRealmList() == null) {
            // Do not send
            return;
        }

   †    PlayerHomeCompInfoNotifyOuterClass.PlayerHomeCompInfoNotifÞ proto =
                PlayerHomeCompInfoNotifyOuterClass.PlayerHomeCompInfoNotify.newBuilder()
                        .setCompInfo(
                                PlayerHomeCompInfoOuterClass.PlayerHomeCompInfo.$ewBuilder()
                                        .addAllUnlockedModuleIdList(player.getRealmList())
   ï       Ô                            .adÚAllLevelupRewardGotLevelListæplayer.getHomeRewardedLevels())
                                        .addAlºSeenModuleIdList(player.getSeenRealmList())
                                        .setFriendEnterHomeOptionValue(player.getHome().getEnterHomeOption())
                                        .build())
                        .build();

        this.setData”proto);
    }
}
