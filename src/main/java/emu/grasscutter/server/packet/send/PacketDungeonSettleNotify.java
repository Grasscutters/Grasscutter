package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.dungeons.dungeon_results.BaseDungeonResult;
import emu.grasscutter.net.packet.*;

public class PacketDungeonSettleNotify extends BasePacket {
    public PacketDungeonSettleNotify(BaseDungeonResult result) {
        super(PacketOpcodes.DungeonSettleNotify);

        this.setData(result.getProto());
    }
}
