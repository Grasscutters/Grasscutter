package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ItemGivingRspOuterClass.ItemGivingRsp;

public final class PacketItemGivingRsp extends BasePacket {
    public PacketItemGivingRsp() {
        this(0, Mode.FAILURE);
    }

    public PacketItemGivingRsp(int value, Mode mode) {
        super(PacketOpcodes.ItemGivingRsp);

        var packet = ItemGivingRsp.newBuilder().setRetcode(mode == Mode.FAILURE ? 1 : 0);
        if (mode == Mode.EXACT_SUCCESS) {
            packet.setGivingId(value);
        } else if (mode == Mode.GROUP_SUCCESS) {
            packet.setGivingGroupId(value);
        }

        this.setData(packet);
    }

    public enum Mode {
        GROUP_SUCCESS,
        EXACT_SUCCESS,
        FAILURE
    }
}
