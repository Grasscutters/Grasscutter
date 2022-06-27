package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.LunchBoxDataOuterClass;
import emu.grasscutter.net.proto.SetUpLunchBoxWidgetRspOuterClass;

public class PacketSetUpLunchBoxWidgetRsp extends BasePacket {

    public PacketSetUpLunchBoxWidgetRsp(LunchBoxDataOuterClass.LunchBoxData lunchBoxData) {
        super(PacketOpcodes.SetUpLunchBoxWidgetRsp);
        var rsp
                = SetUpLunchBoxWidgetRspOuterClass.SetUpLunchBoxWidgetRsp.newBuilder();
        rsp.setLunchBoxData(lunchBoxData);

        setData(rsp.build());
    }
}
