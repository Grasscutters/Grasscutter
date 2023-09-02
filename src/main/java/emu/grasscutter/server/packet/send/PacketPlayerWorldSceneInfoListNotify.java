package emu.grasscutter.server.packet.send;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.scene.SceneTagData;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.MapLayerInfoOuterClass;
import emu.grasscutter.net.proto.PlayerWorldSceneInfoListNotifyOuterClass.PlayerWorldSceneInfoListNotify;
import emu.grasscutter.net.proto.PlayerWorldSceneInfoOuterClass.PlayerWorldSceneInfo;
import java.util.stream.IntStream;

public class PacketPlayerWorldSceneInfoListNotify extends BasePacket {

    public PacketPlayerWorldSceneInfoListNotify() {
        super(PacketOpcodes.PlayerWorldSceneInfoListNotify); // Rename opcode later

        PlayerWorldSceneInfoListNotify.Builder proto =
                PlayerWorldSceneInfoListNotify.newBuilder()
                        .addInfoList(PlayerWorldSceneInfo.newBuilder().setSceneId(1).setIsLocked(false).build())
                        .addInfoList(
                                PlayerWorldSceneInfo.newBuilder()
                                        .setSceneId(3)
                                        .setIsLocked(false)
                                        .addAllSceneTagIdList(
                                                GameData.getSceneTagDataMap().values().stream()
                                                        .filter(sceneTag -> sceneTag.getSceneId() == 3)
                                                        .filter(
                                                                sceneTag ->
                                                                        sceneTag.isDefaultValid()
                                                                                || sceneTag.getCond().get(0).getCondType() != null)
                                                        .map(SceneTagData::getId)
                                                        .toList())
                                        // .addSceneTagIdList(102)  // Jade chamber (alr added)
                                        // .addSceneTagIdList(113)
                                        // .addSceneTagIdList(117)
                                        // .addSceneTagIdList(1093) // 3.0 Vana_real
                                        .addSceneTagIdList(1094) // 3.0 Vana_dream
                                        // .addSceneTagIdList(1095) // 3.0 Vana_first
                                        // .addSceneTagIdList(1096) // 3.0 Vana_festival
                                        .addSceneTagIdList(152) // 3.1 event
                                        .addSceneTagIdList(153) // 3.1 event
                                        .addSceneTagIdList(1164) // Desert Arena (XMSM_CWLTop)
                                        .addSceneTagIdList(1166) // Desert Pyramid (CWL_Trans_02)
                                        .setMapLayerInfo(
                                                MapLayerInfoOuterClass.MapLayerInfo.newBuilder()
                                                        .addAllUnlockedMapLayerIdList(
                                                                GameData.getMapLayerDataMap().keySet()) // MapLayer Ids
                                                        .addAllUnlockedMapLayerFloorIdList(
                                                                GameData.getMapLayerFloorDataMap().keySet())
                                                        .addAllUnlockedMapLayerGroupIdList(
                                                                GameData.getMapLayerGroupDataMap()
                                                                        .keySet()) // will show MapLayer options when hovered over
                                                        .build()) // map layer test
                                        .build())
                        .addInfoList(
                                PlayerWorldSceneInfo.newBuilder()
                                        .setSceneId(4)
                                        .setIsLocked(false)
                                        .addSceneTagIdList(106)
                                        .addSceneTagIdList(109)
                                        .addSceneTagIdList(117)
                                        .build())
                        .addInfoList(PlayerWorldSceneInfo.newBuilder().setSceneId(5).setIsLocked(false).build())
                        .addInfoList(PlayerWorldSceneInfo.newBuilder().setSceneId(6).setIsLocked(false).build())
                        .addInfoList(PlayerWorldSceneInfo.newBuilder().setSceneId(7).setIsLocked(false).build())
                        .addInfoList(
                                PlayerWorldSceneInfo.newBuilder()
                                        .setSceneId(9)
                                        .setIsLocked(false)
                                        .addAllSceneTagIdList(IntStream.range(0, 3000).boxed().toList())
                                        .build())
                        .addInfoList(
                                PlayerWorldSceneInfo.newBuilder()
                                        .setSceneId(10)
                                        .setIsLocked(false)
                                        .addAllSceneTagIdList(IntStream.range(0, 3000).boxed().toList())
                                        .build()); // 3.8

        this.setData(proto);
    }
}
