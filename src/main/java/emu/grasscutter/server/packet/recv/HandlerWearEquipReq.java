package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.WearEquipReqOuterClass.WearEquipReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketWearEquipRsp;

@Opcodes(PacketOpcodes.WearEquipReq)
public class HandlerWearEquipReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        WearEquipReq req = WearEquipReq.parseFrom(payload);

        if (session.getPlayer().getInventory().equipItem(req.getAvatarGuid(), req.getEquipGuid())) {
            session.send(new PacketWearEquipRsp(req.getAvatarGuid(), req.getEquipGuid()));
        }
    }
}
