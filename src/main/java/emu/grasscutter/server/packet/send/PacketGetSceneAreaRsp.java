package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetSceneAreaRspOuterClass.GetSceneAreaRsp;

public class PacketGetSceneAreaRsp extends BasePacket {

    public PacketGetSceneAreaRsp(Player player, int sceneId) {
        super(PacketOpcodes.GetSceneAreaRsp);

        this.buildHeader(0);

        GetSceneAreaRsp p =
                GetSceneAreaRsp.newBuilder()
                        .setSceneId(sceneId)
                        .addAllAreaIdList(player.getUnlockedSceneAreas(sceneId))
                        .addCityInfoList(player.getSotsManager().getCityInfo(1).toProto())
                        .addCityInfoList(player.getSotsManager().getCityInfo(2).toProto())
                        .addCityInfoList(player.getSotsManager().getCityInfo(3).toProto())
                        .build();

        this.setData(p);
    }
}
