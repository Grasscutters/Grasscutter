package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.TowerMiddleLevelChangeTeamNotifyOuterClass;

public class PacketTowerMiddleLevelChangeTeamNotify extends BasePacket {

    public PacketTowerMiddleLevelChangeTeamNotify() {
        super(PacketOpcodes.TowerMiddleLevelChangeTeamNotify);

        TowerMiddleLevelChangeTeamNotifyOuterClass.TowerMiddleLevelChangeTeamNotify proto =
                TowerMiddleLevelChangeTeamNotifyOuterClass.TowerMiddleLevelChangeTeamNotify.newBuilder()
                        .build();

        this.setData(proto);
    }
}
