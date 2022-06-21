package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.home.HomeSceneItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeGetArrangementInfoRspOuterClass;

import java.util.List;

public class PacketHomeGetArrangementInfoRsp extends BasePacket {

    public PacketHomeGetArrangementInfoRsp(Player player, List<Integer> sceneIdList) {
        super(PacketOpcodes.HomeGetArrangementInfoRsp);

        var home = player.getHome();

        var homeScenes = sceneIdList.stream()
            .map(home::getHomeSceneItem)
            .map(HomeSceneItem::toProto)
            .toList();

        home.save();

        var proto = HomeGetArrangementInfoRspOuterClass.HomeGetArrangementInfoRsp.newBuilder();

        proto.addAllSceneArrangementInfoList(homeScenes);

        this.setData(proto);
    }
}
