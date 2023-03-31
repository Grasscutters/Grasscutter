package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.home.HomeBlockItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeMarkPointNotifyOuterClass;
import emu.grasscutter.net.proto.HomeMarkPointSceneDataOuterClass;

import java.util.Collection;

public class PacketHomeMarkPointNotify extends BasePacket {

    public PacketHomeMarkPointNotify(Player player) {
        super(PacketOpcodes.HomeMarkPointNotify);

        var proto = HomeMarkPointNotifyOuterClass.HomeMarkPointNotify.newBuilder();

        if (player.getRealmList() == null) {
            return;
        }
        for (var moduleId : player.getRealmList()) {
            var homeScene = player.getHome().getHomeSceneItem(moduleId + 2000);

            var markPointData = HomeMarkPointSceneDataOuterClass.HomeMarkPointSceneData.newBuilder()
                .setModuleId(moduleId)
                .setSceneId(moduleId + 2000)
                .setTeapotSpiritPos(homeScene.getDjinnPos().toProto());

            // Now it only supports the teleport point
            // TODO add more types
            var marks = homeScene.getBlockItems().values().stream()
                .map(HomeBlockItem::getDeployFurnitureList)
                .flatMap(Collection::stream)
                .filter(i -> i.getFurnitureId() == 373501)
                .map(x -> x.toMarkPointProto(3))
                .toList();

            markPointData.addAllFurnitureList(marks);
            proto.addMarkPointDataList(markPointData);
        }

        this.setData(proto);
    }
}
