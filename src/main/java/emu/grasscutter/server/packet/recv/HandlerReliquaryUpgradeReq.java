package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ReliquaryUpgradeReqOuterClass.ReliquaryUpgradeReq;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.ReliquaryUpgradeReq)
public class HandlerReliquaryUpgradeReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        ReliquaryUpgradeReq req = ReliquaryUpgradeReq.parseFrom(payload);

        session.getServer().getInventoryManager().upgradeRelic(session.getPlayer(), req.getTargetReliquaryGuid(), req.getFoodReliquaryGuidListList(), req.getItemParamListList());
    }

}
