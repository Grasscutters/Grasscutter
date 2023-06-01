package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.dungeons.dungeon_results.BaseDungeonResult;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;

public class PacketDungeonSettleNotify extends BasePacket {
    public PacketDungeonSettleNotify(BaseDungeonResult result) {
        super(PacketOpcodes.DungeonSettleNotify);

        this.setData(result.getProto());
    }
}
