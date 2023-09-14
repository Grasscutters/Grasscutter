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
                        .addInfoList(PlayerWorldSceneInfo.newBuilder().setSceneId(1).setIsLocked(false).build());

                        // Iterate over all scenes the user has tags for
                        for (int scene : sceneTags.keySet()) {
                                var worldInfoBuilder = PlayerWorldSceneInfo.newBuilder();
                                
                                // Add all scene tags for the given scene
                                proto.addInfoList(
                                        worldInfoBuilder
                                        .setSceneId(scene)
                                        .setIsLocked(false)
                                        .addAllSceneTagIdList(
                                                sceneTags.entrySet().stream()
                                                .filter(e -> e.getKey().equals(scene))
                                                .map(Map.Entry::getValue)
                                                .toList()
                                                .get(0)
                                        )
                                );

                                // Add map layer information for big world
                                if (scene == 3) {
                                        worldInfoBuilder.setMapLayerInfo(
                                                MapLayerInfoOuterClass.MapLayerInfo.newBuilder()
                                                        .addAllUnlockedMapLayerIdList(
                                                                GameData.getMapLayerDataMap().keySet()) // MapLayer Ids
                                                        .addAllUnlockedMapLayerFloorIdList(
                                                                GameData.getMapLayerFloorDataMap().keySet())
                                                        .addAllUnlockedMapLayerGroupIdList(
                                                                GameData.getMapLayerGroupDataMap()
                                                                        .keySet()) // will show MapLayer options when hovered over
                                                        .build()); // map layer test
                                }

                                worldInfoBuilder.build();
                        }

        this.setData(proto);
    }
}
