package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SceneForceUnlockNotifyOuterClass.SceneForceUnlockNotify;
import java.util.Collection;
import lombok.val;

public class PacketSceneForceUnlockNotify extends BasePacket {
    public PacketSceneForceUnlockNotify(Collection<Integer> unlocked, boolean isAdd) {
        super(PacketOpcodes.SceneForceUnlockNotify);

        val builder = SceneForceUnlockNotify.newBuilder().addAllForceIdList(unlocked).setIsAdd(isAdd);

        this.setData(builder);
    }

    public PacketSceneForceUnlockNotify(int unlocked, boolean isAdd) {
        super(PacketOpcodes.SceneForceUnlockNotify);

        val builder = SceneForceUnlockNotify.newBuilder().addForceIdList(unlocked).setIsAdd(isAdd);

        this.setData(builder);
    }
}
