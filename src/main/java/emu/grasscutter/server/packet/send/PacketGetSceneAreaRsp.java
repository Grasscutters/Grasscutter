package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.CityInfoOuterClass.CityInfo;
import emu.grasscutter.net.proto.GetSceneAreaRspOuterClass.GetSceneAreaRsp;

public class PacketGetSceneAreaRsp extends BasePacket {

    public PacketGetSceneAreaRsp(Player player, int sceneId) {
        super(PacketOpcodes.GetSceneAreaRsp);

        this.buildHeader(0);

        GetSceneAreaRsp p = GetSceneAreaRsp.newBuilder()
                .setSceneId(sceneId)
                .addAllAreaIdList(player.getUnlockedSceneAreas(sceneId))
                .addCityInfoList(CityInfo.newBuilder().setCityId(1).setLevel(1).build())
                .addCityInfoList(CityInfo.newBuilder().setCityId(2).setLevel(1).build())
                .addCityInfoList(CityInfo.newBuilder().setCityId(3).setLevel(1).build())
                .build();

        this.setData(p);
    }
}
