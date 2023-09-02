package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.home.HomeBlockItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;
import java.util.*;

public class PacketHomeComfortInfoNotify extends BasePacket {

    public PacketHomeComfortInfoNotify(Player player) {
        super(PacketOpcodes.HomeComfortInfoNotify);

        if (player.getRealmList() == null) {
            // Do not send
            return;
        }

        List<HomeModuleComfortInfoOuterClass.HomeModuleComfortInfo> comfortInfoList = new ArrayList<>();

        for (int moduleId : player.getRealmList()) {
            var homeScene = player.getHome().getHomeSceneItem(moduleId + 2000);
            var blockComfortList =
                    homeScene.getBlockItems().values().stream().map(HomeBlockItem::calComfort).toList();
            var homeRoomScene = player.getHome().getMainHouseItem(moduleId + 2000);

            comfortInfoList.add(
                    HomeModuleComfortInfoOuterClass.HomeModuleComfortInfo.newBuilder()
                            .setModuleId(moduleId)
                            .setRoomSceneComfortValue(homeRoomScene.calComfort())
                            .addAllWorldSceneBlockComfortValueList(blockComfortList)
                            .build());
        }

        HomeComfortInfoNotifyOuterClass.HomeComfortInfoNotify proto =
                HomeComfortInfoNotifyOuterClass.HomeComfortInfoNotify.newBuilder()
                        .addAllModuleInfoList(comfortInfoList)
                        .build();

        this.setData(proto);
    }
}
