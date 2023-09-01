package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ServerGlobalValueChangeNotifyOuterClass.ServerGlobalValueChangeNotify;
import emu.grasscutter.utils.Utils;

public final class PacketServerGlobalValueChangeNotify extends BasePacket {
    public PacketServerGlobalValueChangeNotify(GameEntity entity, String abilityHash, float value) {
        super(PacketOpcodes.ServerGlobalValueChangeNotify);

        this.setData(
                ServerGlobalValueChangeNotify.newBuilder()
                        .setEntityId(entity.getId())
                        .setValue(value)
                        .setKeyHash(Utils.abilityHash(abilityHash)));
    }

    public PacketServerGlobalValueChangeNotify(int entityId, String abilityHash, float value) {
        super(PacketOpcodes.ServerGlobalValueChangeNotify);

        this.setData(
                ServerGlobalValueChangeNotify.newBuilder()
                        .setEntityId(entityId)
                        .setValue(value)
                        .setKeyHash(Utils.abilityHash(abilityHash)));
    }
}
