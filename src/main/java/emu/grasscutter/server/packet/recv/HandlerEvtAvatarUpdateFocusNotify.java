package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.EvtAvatarUpdateFocusNotifyOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketEvtAvatarUpdateFocusNotify;

@Opcodes(PacketOpcodes.EvtAvatarUpdateFocusNotify)
public class HandlerEvtAvatarUpdateFocusNotify extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        session
                .getPlayer()
                .getScene()
                .broadcastPacketToOthers(
                        session.getPlayer(),
                        new PacketEvtAvatarUpdateFocusNotify(
                                EvtAvatarUpdateFocusNotifyOuterClass.EvtAvatarUpdateFocusNotify.parseFrom(
                                        payload)));
    }
}
