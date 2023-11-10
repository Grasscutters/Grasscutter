package emu.grasscutter.server.packet.send;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.home.HomeBlockItem;
import emu.grasscutter.game.home.HomeMarkPointProtoFactory;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeMarkPointNotifyOuterClass.HomeMarkPointNotify;
import emu.grasscutter.net.proto.HomeMarkPointSceneDataOuterClass.HomeMarkPointSceneData;
import java.util.Collection;
import java.util.Set;

public class PacketHomeMarkPointNotify extends BasePacket {

    public PacketHomeMarkPointNotify(Player player) {
        super(PacketOpcodes.HomeMarkPointNotify);

        var proto = HomeMarkPointNotify.newBuilder();
        var world = player.getCurHomeWorld();
        var owner = world.getHost();
        var home = world.getHome();

        if (owner.getRealmList() == null || owner.getRealmList().isEmpty()) {
            return;
        }

        // send current home mark points.
        var moduleId = owner.getCurrentRealmId();
        var scene = world.getSceneById(moduleId + 2000);
        if (scene == null) {
            Grasscutter.getLogger()
                    .warn(
                            "Current Realm id is invalid! SceneExcelConfigData.json, game resource not loaded correctly or the realm id maybe wrong?!");
            return;
        }
        var homeScene = home.getHomeSceneItem(moduleId + 2000);
        var mainHouse = home.getMainHouseItem(moduleId + 2000);

        Set.of(homeScene, mainHouse)
                .forEach(
                        homeSceneItem -> {
                            var markPointData =
                                    HomeMarkPointSceneData.newBuilder()
                                            .setModuleId(moduleId)
                                            .setSceneId(homeSceneItem.getSceneId());

                            if (!homeSceneItem.isRoom()) {
                                var config = scene.getScriptManager().getConfig();
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
