package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TakeoffEquipReqOuterClass.TakeoffEquipReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketTakeoffEquipRsp;

@Opcodes(PacketOpcodes.TakeoffEquipReq)
public class HandlerTakeoffEquipReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        TakeoffEquipReq req = TakeoffEquipReq.parseFrom(payload);

        if (session.getPlayer().getInventory().unequipItem(req.getAvatarGuid(), req.getSlot())) {
            session.send(new PacketTakeoffEquipRsp(req.getAvatarGuid(), req.getSlot()));
        }
    }

}
