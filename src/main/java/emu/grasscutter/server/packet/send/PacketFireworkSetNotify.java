package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.FireworksReformDataNotifyOuterClass.FireworksReformDataNotify;
import emu.grasscutter.net.proto.FireworksReformDataOuterClass.FireworksReformData;

public class PacketFireworkSetNotify extends BasePacket {

    public PacketFireworkSetNotify(FireworksReformData data) {
        super(PacketOpcodes.FireworkSetNotify);

        this.setData(FireworksReformDataNotify.newBuilder().addFireworksReformDataList(data));
    }
}
