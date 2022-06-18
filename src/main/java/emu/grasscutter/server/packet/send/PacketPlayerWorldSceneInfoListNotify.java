package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerWorldSceneInfoListNotifyOuterClass.PlayerWorldSceneInfoListNotify;

import static emu.grasscutter.net.proto.PlayerWorldSceneInfoOuterClass.PlayerWorldSceneInfo;

public class PacketPlayerWorldSceneInfoListNotify extends BasePacket {

    public PacketPlayerWorldSceneInfoListNotify() {
        super(PacketOpcodes.PlayerWorldSceneInfoListNotify); // Rename opcode later

        PlayerWorldSceneInfoListNotify proto = PlayerWorldSceneInfoListNotify.newBuilder()
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
                    .addSceneTagIdList(102)
                    .addSceneTagIdList(113)
                    .addSceneTagIdList(117)
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
            .build();

        this.setData(proto);
    }
}
