package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.LevelupCityRspOuterClass.LevelupCityRsp;
import emu.grasscutter.net.proto.CityInfoOuterClass.CityInfo;

public class PacketLevelupCityRsp extends BasePacket {

    public PacketLevelupCityRsp(int sceneId, int level, int cityId, int crystalNum, int areaId, int retcode) {
        super(PacketOpcodes.LevelupCityRsp);

        LevelupCityRsp proto =
                LevelupCityRsp.newBuilder()
                        .setSceneId(sceneId)
                        .setCityInfo(CityInfo.newBuilder().setCityId(cityId).setLevel(level).setCrystalNum(crystalNum).build())
                        .setAreaId(areaId)
                        .setRetcode(retcode)
                        .build();

        this.setData(proto);
    }

}
