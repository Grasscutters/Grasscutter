package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerDieTypeOuterClass.PlayerDieType;
import emu.grasscutter.net.proto.DungeonPlayerDieNotifyOuterClass.DungeonPlayerDieNotify;

public class PacketDungeonPlayerDieNotify extends BasePacket {

    public PacketDungeonPlayerDieNotify(PlayerDieType playerDieType, int killerId, int dungeonId, int waitTime, int reviveCount, boolean isGadget) {
        super(PacketOpcodes.DungeonPlayerDieNotify);

        DungeonPlayerDieNotify.Builder proto = DungeonPlayerDieNotify.newBuilder()
            .setDieType(playerDieType)
            .setReviveCount(reviveCount)
            .setWaitTime(waitTime)
            .setDungeonId(dungeonId)
            .setMurdererEntityId(killerId);

        if(isGadget) proto.setGadgetId(killerId);
        else proto.setMonsterId(killerId);

        this.setData(proto.build());
    }
}
