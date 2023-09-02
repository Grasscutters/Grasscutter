package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.home.HomeBlockItem;
import emu.grasscutter.game.home.HomeMarkPointProtoFactory;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;
import java.util.Collection;
import java.util.Set;

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

        // send current home mark points.
        var moduleId = owner.getCurrentRealmId();
        var homeScene = home.getHomeSceneItem(moduleId + 2000);
        var mainHouse = home.getMainHouseItem(moduleId + 2000);

        Set.of(homeScene, mainHouse)
                .forEach(
                        homeSceneItem -> {
                            var markPointData =
                                    HomeMarkPointSceneDataOuterClass.HomeMarkPointSceneData.newBuilder()
                                            .setModuleId(moduleId)
                                            .setSceneId(homeSceneItem.getSceneId());

                            if (!homeSceneItem.isRoom()) {
                                var config = world.getSceneById(moduleId + 2000).getScriptManager().getConfig();
                                markPointData
                                        .setSafePointPos(
                                                config == null
                                                        ? homeSceneItem.getBornPos().toProto()
                                                        : config.born_pos.toProto())
                                        .setTeapotSpiritPos(homeSceneItem.getDjinnPos().toProto());
                            }

                            var marks =
                                    homeSceneItem.getBlockItems().values().stream()
                                            .map(HomeBlockItem::getMarkPointProtoFactories)
                                            .flatMap(Collection::stream)
                                            .filter(HomeMarkPointProtoFactory::isProtoConvertible)
                                            .map(HomeMarkPointProtoFactory::toMarkPointProto)
                                            .toList();

                            markPointData.addAllFurnitureList(marks);
                            proto.addMarkPointDataList(markPointData);
                        });

        this.setData(proto);
    }
}
