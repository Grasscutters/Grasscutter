package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DailyTaskFilterCityRspOuterClass;

public class PacketDailyTaskFilterRsp extends BasePacket {
    public PacketDailyTaskFilterRsp(int cityId) {
        super(PacketOpcodes.DailyTaskFilterCityRsp);

        var rsp = DailyTaskFilterCityRspOuterClass.DailyTaskFilterCityRsp.newBuilder()
            .setCityId(cityId)
            .build();

        this.setData(rsp);
    }
}
