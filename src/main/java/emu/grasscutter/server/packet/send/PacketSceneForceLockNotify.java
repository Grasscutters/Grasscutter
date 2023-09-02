package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SceneForceLockNotifyOuterClass.SceneForceLockNotify;
import java.util.Collection;
import lombok.val;

public class PacketSceneForceLockNotify extends BasePacket {
    public PacketSceneForceLockNotify(Collection<Integer> locked) {
        super(PacketOpcodes.SceneForceLockNotify);
        val builder = SceneForceLockNotify.newBuilder().addAllForceIdList(locked);

        this.setData(builder);
    }

    public PacketSceneForceLockNotify(int locked) {
        super(PacketOpcodes.SceneForceLockNotify);
        val builder = SceneForceLockNotify.newBuilder().addForceIdList(locked);

        this.setData(builder);
    }
}
