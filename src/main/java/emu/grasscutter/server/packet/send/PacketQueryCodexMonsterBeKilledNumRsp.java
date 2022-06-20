package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.QueryCodexMonsterBeKilledNumRspOuterClass.QueryCodexMonsterBeKilledNumRsp;

import java.util.List;

public class PacketQueryCodexMonsterBeKilledNumRsp extends BasePacket {

    public PacketQueryCodexMonsterBeKilledNumRsp(Player player, List<Integer> codexList) {
        super(PacketOpcodes.QueryCodexMonsterBeKilledNumRsp);
        QueryCodexMonsterBeKilledNumRsp.Builder proto = QueryCodexMonsterBeKilledNumRsp.newBuilder();

        codexList.forEach(animal -> {
            if (player.getCodex().getUnlockedAnimal().containsKey(animal)) {
                proto.addCodexIdList(animal)
                    .addBeKilledNumList(player.getCodex().getUnlockedAnimal().get(animal))
                    .addCHPBKCLKPCJ(0);
            }
        });

        this.setData(proto);
    }
}
