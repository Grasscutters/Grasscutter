package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.UnlockAvatarTalentReqOuterClass.UnlockAvatarTalentReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.UnlockAvatarTalentReq)
public class HandlerUnlockAvatarTalentReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        UnlockAvatarTalentReq req = UnlockAvatarTalentReq.parseFrom(payload);

        // Unlock avatar const
        session.getServer().getInventorySystem().unlockAvatarConstellation(session.getPlayer(), req.getAvatarGuid());
    }

}
