package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TakeBattlePassRewardReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketTakeBattlePassRewardRsp;

@Opcodes(PacketOpcodes.TakeBattlePassRewardReq)
public class HandlerTakeBattlePassRewardReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req
                = TakeBattlePassRewardReqOuterClass.TakeBattlePassRewardReq.parseFrom(payload);

        //due to the list only have one element, so we can use get(0)
        session.send(new PacketTakeBattlePassRewardRsp(req.getTakeOptionListList() , session));

        //update the awardTakenLevel
        req.getTakeOptionListList().forEach(battlePassRewardTakeOption ->
                session.getPlayer().getBattlePassManager().updateAwardTakenLevel(battlePassRewardTakeOption.getTag().getLevel()));
    }
}
