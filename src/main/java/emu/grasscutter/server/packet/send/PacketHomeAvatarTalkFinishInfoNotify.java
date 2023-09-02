package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass;

public class PacketHomeAvatarTalkFinishInfoNotify extends BasePacket {
    public PacketHomeAvatarTalkFinishInfoNotify(Player homeOwner) {
        super(PacketOpcodes.HomeAvatarTalkFinishInfoNotify);

        this.setData(
                HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify.newBuilder()
                        .addAllAvatarTalkInfoList(homeOwner.getHome().toAvatarTalkFinishInfoProto()));
    }
}
