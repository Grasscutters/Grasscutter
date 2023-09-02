package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.home.HomeSceneItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.HomeGetArrangementInfoRspOuterClass;
import java.util.List;

public class PacketHomeGetArrangementInfoRsp extends BasePacket {

    public PacketHomeGetArrangementInfoRsp(Player player, List<Integer> sceneIdList) {
        super(PacketOpcodes.HomeGetArrangementInfoRsp);

        var proto = HomeGetArrangementInfoRspOuterClass.HomeGetArrangementInfoRsp.newBuilder();
        var home = player.getCurHomeWorld().getHome();
        var homeScenes =
                sceneIdList.stream().map(home::getHomeSceneItem).map(HomeSceneItem::toProto).toList();
        proto.addAllSceneArrangementInfoList(homeScenes);
        home.save();

        this.setData(proto);
    }
}
