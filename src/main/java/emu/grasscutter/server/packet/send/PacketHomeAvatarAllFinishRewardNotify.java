package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeAvatarAllFinishRewardNotifyOuterClass;

public class PacketHomeAvatarAllFinishRewardNotify extends BasePacket {
    public PacketHomeAvatarAllFinishRewardNotify(Player player) {
        super(PacketOpcodes.HomeAvatarAllFinishRewardNotify);

        var list = player.getHome().getFinishedRewardEventIdSet();
        if (list != null) {
            this.setData(
                    HomeAvatarAllFinishRewardNotifyOuterClass.HomeAvatarAllFinishRewardNotify.newBuilder()
                            .addAllEventIdList(player.getHome().getFinishedRewardEventIdSet()));
        }
    }
}
