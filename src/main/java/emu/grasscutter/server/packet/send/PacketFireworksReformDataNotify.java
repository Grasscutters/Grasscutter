package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.FireworksReformDataNotifyOuterClass.FireworksReformDataNotify;
import emu.grasscutter.net.proto.FireworksReformDataOuterClass.FireworksReformData;

public class PacketFireworksReformDataNotify extends BasePacket {

    public PacketFireworksReformDataNotify(FireworksReformData data) {
        super(PacketOpcodes.FireworksReformDataNotify);

        this.setData(FireworksReformDataNotify.newBuilder().addFireworksReformDataList(data));
    }
}
