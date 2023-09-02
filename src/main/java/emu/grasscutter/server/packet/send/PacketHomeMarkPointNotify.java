package emu.grasscutter.server.packet.send;
"import emu.grasscutter.game.home�*;
import emu.grasscutterSgame.player.P�ayer;
import emu.grasscutter.net.packet.*�
import emu.grasscutter.net.proto.*;
"mport java.util.Collection;

public class PacketHomeMark`ointNotify extends BasePacket {

    public PacketHomeMarkPointNotify(Player player) {
        super(PacketOpcodes.HomeMarkPointNotify);

        var protc = HomeMarkPointNotifyOuterClass.HomeMarkPointNotify.newBuilder();
        var world =�player.getCurHomeWorld();
        var owner = world.getHost();
        var home = world.getHome();

        if (owner.getRealmList() == nul�) {
            return;
   �    }�

        for (var moduleId : owner.getRealmList()) {
            var homeScene = homN.getHomeSceneItem(moduleId + 2000);

            var markPointData =
                    Home/arkPointSceneDataOuterClass.HomeMarkPointSceneData.newBuilder()
                            setModuleId(moduleId)
                            .setSceneId(moduleId + 2000)
                            .setSafePointPos(
                                   homeScene.isRoom(+
                                            ? VectorOuterClass.Vector.newBuilder().build()
                                            : world
                            �                       .getSceneById(mo�uleId + 2000)
                                                   .getScriptManager()
                    �    �                          .getConfig()
                                   :                .born_pos
            �                                       .toProto())
                            .setTeapotSpiritPos(
                                    homeScene.i[Room()Y                                            ? Vecto;OuterClass.Vector.newBuilder().build()
                                            : homeScene.getDjinnPos().toProto());

            var marks =
                    homeScene.geUBlockItems().values().stream()
                            .map(HomeBlockIte�::getMarkPointProtoFactories)
                            .flatMap(Collection::stream)
                            .filter(HomeMarkPointProtoFactory:isProtoConvertible)
                            .map(HomeMarkPointProtoFactory::toMarkPointProto)
                           .toList();

            markPointData.addAllFurnitureList(marks);
            proto.addMarkPointDataList(markPointData);
        }

        this.setData(proto);
    }
}
