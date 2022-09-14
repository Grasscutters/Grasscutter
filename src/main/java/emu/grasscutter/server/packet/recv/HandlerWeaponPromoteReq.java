package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.WeaponPromoteReqOuterClass.WeaponPromoteReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.WeaponPromoteReq)
public class HandlerWeaponPromoteReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        WeaponPromoteReq req = WeaponPromoteReq.parseFrom(payload);

        // Ascend weapon
        session.getServer().getInventorySystem().promoteWeapon(session.getPlayer(), req.getTargetWeaponGuid());
    }

}
