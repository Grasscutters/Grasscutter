package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomePreChangeEditModeNotifyOuterClass;

public class PacketHomePreChangeEditModeNotify extends BasePacket {

    public PacketHomePreChangeEditModeNotify(boolean isEnterEditMode) {
        super(PacketOpcodes.HomePreChangeEditModeNotify);

        var proto = HomePreChangeEditModeNotifyOuterClass.HomePreChangeEditModeNotify.newBuilder();

        proto.setIsEnterEditMode(isEnterEditMode);

        this.setData(proto);
    }
}
