package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.FurnitureCurModuleArrangeCountNotifyOuterClass;

public class PacketFurnitureCurModuleArrangeCountNotify extends BasePacket {

    public PacketFurnitureCurModuleArrangeCountNotify() {
        super(PacketOpcodes.FurnitureCurModuleArrangeCountNotify);

        var proto = FurnitureCurModuleArrangeCountNotifyOuterClass.FurnitureCurModuleArrangeCountNotify.newBuilder();

        // TODO

        this.setData(proto);
    }
}
