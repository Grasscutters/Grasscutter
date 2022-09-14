package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SetEquipLockStateReqOuterClass.SetEquipLockStateReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.SetEquipLockStateReq)
public class HandlerSetEquipLockStateReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        SetEquipLockStateReq req = SetEquipLockStateReq.parseFrom(payload);

        session.getServer().getInventorySystem().lockEquip(session.getPlayer(), req.getTargetEquipGuid(), req.getIsLocked());
    }

}
