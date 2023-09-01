package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.HomeAllUnlockedBgmIdListNotifyOuterClass;

public class PacketHomeAllUnlockedBgmIdListNotify extends BasePacket {
    public PacketHomeAllUnlockedBgmIdListNotify(Player player) {
        super(PacketOpcodes.HomeAllUnlockedBgmIdListNotify);

        if (player.getRealmList() == null) {
            return;
        }

        var unlocked = player.getHome().getUnlockedHomeBgmList();

        var notify =
                HomeAllUnlockedBgmIdListNotifyOuterClass.HomeAllUnlockedBgmIdListNotify.newBuilder()
                        .addAllAllUnlockedBgmIdList(unlocked)
                        .build();

        this.setData(notify);
    }
}
