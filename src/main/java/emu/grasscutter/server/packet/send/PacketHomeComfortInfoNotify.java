package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeComfortInfoNotifyOuterClass;
import emu.grasscutter.net.proto.HomeModuleComfortInfoOuterClass;

import java.util.ArrayList;
import java.util.List;

public class PacketHomeComfortInfoNotify extends BasePacket {

    public PacketHomeComfortInfoNotify(Player player) {
        super(PacketOpcodes.HomeComfortInfoNotify);

        if (player.getRealmList() == null) {
            // Do not send
            return;
        }

        List<HomeModuleComfortInfoOuterClass.HomeModuleComfortInfo> comfortInfoList = new ArrayList<>();

        for (int moduleId : player.getRealmList()) {
            comfortInfoList.add(
                    HomeModuleComfortInfoOuterClass.HomeModuleComfortInfo.newBuilder()
                        .setModuleId(moduleId)
                        .build()
            );
        }

        HomeComfortInfoNotifyOuterClass.HomeComfortInfoNotify proto = HomeComfortInfoNotifyOuterClass.HomeComfortInfoNotify
                .newBuilder()
                .addAllModuleInfoList(comfortInfoList)
                .build();


        this.setData(proto);
    }
}
