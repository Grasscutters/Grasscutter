package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.EvtAvatarExitFocusNotifyOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketEvtAvatarExitFocusNotify;

@Opcodes(PacketOpcodes.EvtAvatarExitFocusNotify)
public class HandlerEvtAvatarExitFocusNotify extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        session
                .getPlayer()
                .getScene()
                .broadcastPacketToOthers(
                        session.getPlayer(),
                        new PacketEvtAvatarExitFocusNotify(
                                EvtAvatarExitFocusNotifyOuterClass.EvtAvatarExitFocusNotify.parseFrom(payload)));
    }
}
