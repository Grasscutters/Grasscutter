package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.CityInfoOuterClass.CityInfo;
import emu.grasscutter.net.proto.LevelupCityRspOuterClass.LevelupCityRsp;

public class PacketLevelupCityRsp extends BasePacket {

    public PacketLevelupCityRsp(
            int sceneId, int level, int cityId, int crystalNum, int areaId, int retcode) {
        super(PacketOpcodes.LevelupCityRsp);

        LevelupCityRsp proto =
                LevelupCityRsp.newBuilder()
                        .setSceneId(sceneId)
                        .setCityInfo(
                                CityInfo.newBuilder()
                                        .setCityId(cityId)
                                        .setLevel(level)
                                        .setCrystalNum(crystalNum)
                                        .build())
                        .setAreaId(areaId)
                        .setRetcode(retcode)
                        .build();

        this.setData(proto);
    }
}
