package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass;

public class PacketPlayerApplyEnterHomeNotify extends BasePacket {
    public PacketPlayerApplyEnterHomeNotify(Player requester) {
        super(PacketOpcodes.PlayerApplyEnterHomeNotify);

        this.setData(
                PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify.newBuilder()
                        .setSrcPlayerInfo(requester.getOnlinePlayerInfo()));
    }
}
