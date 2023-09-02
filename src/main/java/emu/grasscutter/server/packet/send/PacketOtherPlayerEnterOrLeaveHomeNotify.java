package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.OtherPlayerEnterHomeNotifyOuterClass;

public class PacketOtherPlayerEnterOrLeaveHomeNotify extends BasePacket {
    public PacketOtherPlayerEnterOrLeaveHomeNotify(
            Player enterer,
            OtherPlayerEnterHomeNotifyOuterClass.OtherPlayerEnterHomeNotify.Reason reason) {
        super(PacketOpcodes.OtherPlayerEnterHomeNotify);

        this.setData(
                OtherPlayerEnterHomeNotifyOuterClass.OtherPlayerEnterHomeNotify.newBuilder()
                        .setNickname(enterer.getNickname())
                        .setReason(reason));
    }
}
