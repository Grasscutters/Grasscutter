package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.MassiveEntityElementOpBatchNotifyOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketMassiveEntityElementOpBatchNotify;

@Opcodes(PacketOpcodes.MassiveEntityElementOpBatchNotify)
public class HandlerMassiveEntityElementOpBatchNotify extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        session
                .getPlayer()
                .getScene()
                .broadcastPacketToOthers(
                        session.getPlayer(),
                        new PacketMassiveEntityElementOpBatchNotify(
                                MassiveEntityElementOpBatchNotifyOuterClass.MassiveEntityElementOpBatchNotify
                                        .parseFrom(payload)));
    }
}
