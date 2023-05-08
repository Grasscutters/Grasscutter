package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.world.World;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ClientLockGameTimeNotifyOuterClass.ClientLockGameTimeNotify;

public final class PacketClientLockGameTimeNotify extends BasePacket {
    public PacketClientLockGameTimeNotify(World world) {
        super(PacketOpcodes.ClientLockGameTimeNotify);

        var packet = ClientLockGameTimeNotify.newBuilder()
            .setIsLock(world.isTimeLocked())
            .build();

        this.setData(packet);
    }
}
