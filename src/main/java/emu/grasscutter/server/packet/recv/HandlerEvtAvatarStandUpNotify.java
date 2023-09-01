package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.EvtAvatarStandUpNotifyOuterClass.EvtAvatarStandUpNotify;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketEvtAvatarStandUpNotify;

@Opcodes(PacketOpcodes.EvtAvatarStandUpNotify)
public class HandlerEvtAvatarStandUpNotify extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        EvtAvatarStandUpNotify notify = EvtAvatarStandUpNotify.parseFrom(payload);

        session.getPlayer().getScene().broadcastPacket(new PacketEvtAvatarStandUpNotify(notify));
    }
}
