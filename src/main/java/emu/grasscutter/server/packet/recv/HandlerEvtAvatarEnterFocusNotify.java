package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.EvtAvatarEnterFocusNotifyOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketEvtAvatarEnterFocusNotify;

@Opcodes(PacketOpcodes.EvtAvatarEnterFocusNotify)
public class HandlerEvtAvatarEnterFocusNotify extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        session
                .getPlayer()
                .getScene()
                .broadcastPacketToOthers(
                        session.getPlayer(),
                        new PacketEvtAvatarEnterFocusNotify(
                                EvtAvatarEnterFocusNotifyOuterClass.EvtAvatarEnterFocusNotify.parseFrom(payload)));
    }
}
