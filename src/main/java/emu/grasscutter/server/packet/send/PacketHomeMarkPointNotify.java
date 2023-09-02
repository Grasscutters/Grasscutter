package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.home.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;
import java.util.Collection;

public class PacketHomeMarkPointNotify extends BasePacket {

    public PacketHomeMarkPointNotify(Player player) {
        super(PacketOpcodes.HomeMarkPointNotify);

        var proto = HomeMarkPointNotifyOuterClass.HomeMarkPointNotify.newBuilder();
        var world = player.getCurHomeWorld();
        var owner = world.getHost();
        var home = world.getHome();

        if (owner.getRealmList() == null) {
            return;
        }

        for (var moduleId : owner.getRealmList()) {
            var homeScene = home.getHomeSceneItem(moduleId + 2000);

            var markPointData =
                    HomeMarkPointSceneDataOuterClass.HomeMarkPointSceneData.newBuilder()
                            .setModuleId(moduleId)
                            .setSceneId(moduleId + 2000)
                            .setSafePointPos(
                                    homeScene.isRoom()
                                            ? VectorOuterClass.Vector.newBuilder().build()
                                            : world
                                                    .getSceneById(moduleId + 2000)
                                                    .getScriptManager()
                                                    .getConfig()
                                                    .born_pos
                                                    .toProto())
                            .setTeapotSpiritPos(
                                    homeScene.isRoom()
                                            ? VectorOuterClass.Vector.newBuilder().build()
                                            : homeScene.getDjinnPos().toProto());

            var marks =
                    homeScene.getBlockItems().values().stream()
                            .map(HomeBlockItem::getMarkPointProtoFactories)
                            .flatMap(Collection::stream)
                            .filter(HomeMarkPointProtoFactory::isProtoConvertible)
                            .map(HomeMarkPointProtoFactory::toMarkPointProto)
                            .toList();

            markPointData.addAllFurnitureList(marks);
            proto.addMarkPointDataList(markPointData);
        }

        this.setData(proto);
    }
}
