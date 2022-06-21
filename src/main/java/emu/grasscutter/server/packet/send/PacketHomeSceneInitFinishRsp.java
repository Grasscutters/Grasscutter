package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeSceneInitFinishReqOuterClass;

public class PacketHomeSceneInitFinishRsp extends BasePacket {

    public PacketHomeSceneInitFinishRsp() {
        super(PacketOpcodes.HomeSceneInitFinishRsp);

        var proto = HomeSceneInitFinishReqOuterClass.HomeSceneInitFinishReq.newBuilder();

        this.setData(proto);

    }
}
