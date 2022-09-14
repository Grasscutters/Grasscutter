package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.WorldDataSystem;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetActivityInfoRspOuterClass;
import emu.grasscutter.net.proto.GetInvestigationMonsterRspOuterClass;

import java.util.List;

public class PacketGetInvestigationMonsterRsp extends BasePacket {

    public PacketGetInvestigationMonsterRsp(Player player, WorldDataSystem worldDataManager, List<Integer> cityIdListList) {

        super(PacketOpcodes.GetInvestigationMonsterRsp);

        var resp = GetInvestigationMonsterRspOuterClass.GetInvestigationMonsterRsp.newBuilder();

        cityIdListList.forEach(id -> resp.addAllMonsterList(worldDataManager.getInvestigationMonstersByCityId(player, id)));


        this.setData(resp.build());
    }
}
