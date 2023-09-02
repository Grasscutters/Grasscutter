package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.EvtBulletDeactiveNotifyOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketEvtBulletDeactiveNotify;

@Opcodes(PacketOpcodes.EvtBulletDeactiveNotify)
public class HandlerEvtBulletDeactiveNotify extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        session
                .getPlayer()
                .getScene()
                .broadcastPacketToOthers(
                        session.getPlayer(),
                        new PacketEvtBulletDeactiveNotify(
                                EvtBulletDeactiveNotifyOuterClass.EvtBulletDeactiveNotify.parseFrom(payload)));
    }
}
