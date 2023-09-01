package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AvatarUpgradeReqOuterClass.AvatarUpgradeReq;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.AvatarUpgradeReq)
public class HandlerAvatarUpgradeReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        AvatarUpgradeReq req = AvatarUpgradeReq.parseFrom(payload);

        // Level up avatar
        session
                .getServer()
                .getInventorySystem()
                .upgradeAvatar(session.getPlayer(), req.getAvatarGuid(), req.getItemId(), req.getCount());
    }
}
