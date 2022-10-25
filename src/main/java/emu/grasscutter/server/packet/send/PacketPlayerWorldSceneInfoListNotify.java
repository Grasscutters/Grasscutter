package emu.grasscutter.server.packet.send;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerWorldSceneInfoListNotifyOuterClass.PlayerWorldSceneInfoListNotify;
import emu.grasscutter.net.proto.PlayerWorldSceneInfoOuterClass.PlayerWorldSceneInfo;

public class PacketPlayerWorldSceneInfoListNotify extends BasePacket {

    public PacketPlayerWorldSceneInfoListNotify() {
        super(PacketOpcodes.PlayerWorldSceneInfoListNotify); // Rename opcode later

        PlayerWorldSceneInfoListNotify.Builder proto = PlayerWorldSceneInfoListNotify.newBuilder()
                .addInfoList(
                        PlayerWorldSceneInfo.newBuilder()
                                .setSceneId(1)
                                .setIsLocked(false)
                                .build()
                )
                .addInfoList(
                        PlayerWorldSceneInfo.newBuilder()
                                .setSceneId(3)
                                .setIsLocked(false)
                                .addSceneTagIdList(102) // Jade chamber
                                .addSceneTagIdList(113)
                                .addSceneTagIdList(117)

                                // Vanarana (Sumeru tree)
                                .addSceneTagIdList(1093) // Vana_real
                                // .addSceneTagIdList(1094) // Vana_dream
                                // .addSceneTagIdList(1095) // Vana_first
                                // .addSceneTagIdList(1096) // Vana_festival

                                // 3.1 event
                                .addSceneTagIdList(152)
                                .addSceneTagIdList(153)

                                // Pyramid
                                .addSceneTagIdList(1164) // Arena (XMSM_CWLTop)
                                .addSceneTagIdList(1166) // Pyramid (CWL_Trans_02)

                                // Brute force
                                //.addAllSceneTagIdList(IntStream.range(1150, 1250).boxed().toList())
                                .build()
                )
                .addInfoList(
                        PlayerWorldSceneInfo.newBuilder()
                                .setSceneId(4)
                                .setIsLocked(false)
                                .addSceneTagIdList(106)
                                .addSceneTagIdList(109)
                                .addSceneTagIdList(117)
                                .build()
                )
                .addInfoList(
                        PlayerWorldSceneInfo.newBuilder()
                                .setSceneId(5)
                                .setIsLocked(false)
                                .build()
                )
                .addInfoList(
                        PlayerWorldSceneInfo.newBuilder()
                                .setSceneId(6)
                                .setIsLocked(false)
                                .build()
                )
                .addInfoList(
                        PlayerWorldSceneInfo.newBuilder()
                                .setSceneId(7)
                                .setIsLocked(false)
                                .build()
                )
                .addInfoList(
                        PlayerWorldSceneInfo.newBuilder()
                                .setSceneId(9)
                                .setIsLocked(false)
                                .addAllSceneTagIdList(IntStream.range(0, 3000).boxed().toList())
                                .build()
                );

        this.setData(proto);
    }
}
