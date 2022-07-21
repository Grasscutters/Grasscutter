package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarPromoteReqOuterClass.AvatarPromoteReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.AvatarPromoteReq)
public class HandlerAvatarPromoteReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        AvatarPromoteReq req = AvatarPromoteReq.parseFrom(payload);

        // Ascend avatar
        session.getServer().getInventorySystem().promoteAvatar(session.getPlayer(), req.getGuid());
    }

}
