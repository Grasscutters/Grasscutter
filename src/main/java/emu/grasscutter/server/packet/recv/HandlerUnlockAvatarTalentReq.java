package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.UnlockAvatarTalentReqOuterClass.UnlockAvatarTalentReq;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.UnlockAvatarTalentReq)
public class HandlerUnlockAvatarTalentReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        UnlockAvatarTalentReq req = UnlockAvatarTalentReq.parseFrom(payload);

        // Unlock avatar const
        var avatar = session.getPlayer().getAvatars().getAvatarByGuid(req.getAvatarGuid());
        if (avatar == null) return;
        avatar.unlockConstellation(req.getTalentId());
    }
}
