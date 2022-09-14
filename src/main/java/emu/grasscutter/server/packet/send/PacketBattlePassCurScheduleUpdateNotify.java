package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.net.proto.BattlePassCurScheduleUpdateNotifyOuterClass.BattlePassCurScheduleUpdateNotify;

import java.util.ArrayList;
import java.util.List;

public class PacketBattlePassCurScheduleUpdateNotify extends BasePacket {
	
    public PacketBattlePassCurScheduleUpdateNotify(Player player) {
        super(PacketOpcodes.BattlePassCurScheduleUpdateNotify);

        var proto = BattlePassCurScheduleUpdateNotify.newBuilder();

        proto
        	.setHaveCurSchedule(true)
        	.setCurSchedule(player.getBattlePassManager().getScheduleProto())
        	.build();

        setData(proto.build());

    }
}
