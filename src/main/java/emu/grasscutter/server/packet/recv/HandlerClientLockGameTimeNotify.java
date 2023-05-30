package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ClientLockGameTimeNotifyOuterClass.ClientLockGameTimeNotify;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.ClientLockGameTimeNotify)
public final class HandlerClientLockGameTimeNotify extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var packet = ClientLockGameTimeNotify.parseFrom(payload);
        session.getPlayer().getWorld().lockTime(packet.getIsLock());
    }
}
