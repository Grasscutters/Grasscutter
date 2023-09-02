package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.EvtBulletHitNotifyOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketEvtBulletHitNotify;

@Opcodes(PacketOpcodes.EvtBulletHitNotify)
public class HandlerEvtBulletHitNotify extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        session
                .getPlayer()
                .getScene()
                .broadcastPacketToOthers(
                        session.getPlayer(),
                        new PacketEvtBulletHitNotify(
                                EvtBulletHitNotifyOuterClass.EvtBulletHitNotify.parseFrom(payload)));
    }
}
