package emu.grasscutter.server.packet.send;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.MapLayerInfoOuterClass;
import emu.grasscutter.net.proto.PlayerWorldSceneInfoListNotifyOuterClass.PlayerWorldSceneInfoListNotify;
import emu.grasscutter.net.proto.PlayerWorldSceneInfoOuterClass.PlayerWorldSceneInfo;
import java.util.Map;

public class PacketPlayerWorldSceneInfoListNotify extends BasePacket {

    public PacketPlayerWorldSceneInfoListNotify(Player player) {
        super(PacketOpcodes.PlayerWorldSceneInfoListNotify); // Rename opcode later

        var sceneTags = player.getSceneTags();

        PlayerWorldSceneInfoListNotify.Builder proto =
                PlayerWorldSceneInfoListNotify.newBuilder()
                        .addInfoList(
                                PlayerWorldSceneInfo.newBuilder().setSceneId(1).setIsLocked(false).build());

        // Iterate over all scenes
        for (int scene : GameData.getSceneDataMap().keySet()) {
            var worldInfoBuilder = PlayerWorldSceneInfo.newBuilder().setSceneId(scene).setIsLocked(false);

            /** Add scene-specific data */

            // Scenetags
            if (sceneTags.keySet().contains(scene)) {
                worldInfoBuilder.addAllSceneTagIdList(
                        sceneTags.entrySet().stream()
                                .filter(e -> e.getKey().equals(scene))
                                .map(Map.Entry::getValue)
                                .toList()
                                .get(0));
            }

            // Map layer information (Big world)
            if (scene == 3) {
                worldInfoBuilder.setMapLayerInfo(
                        MapLayerInfoOuterClass.MapLayerInfo.newBuilder()
                                .addAllUnlockedMapLayerIdList(
                                        GameData.getMapLayerDataMap().keySet()) // MapLayer Ids
                                .addAllUnlockedMapLayerFloorIdList(GameData.getMapLayerFloorDataMap().keySet())
                                .addAllUnlockedMapLayerGroupIdList(
                                        GameData.getMapLayerGroupDataMap()
                                                .keySet()) // will show MapLayer options when hovered over
                                .build()); // map layer test
            }

            proto.addInfoList(worldInfoBuilder.build());
        }

        this.setData(proto);
    }
}
