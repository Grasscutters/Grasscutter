package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ClientLockGameTimeNotifyOuterClass.ClientLockGameTimeNotify;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.ClientLockGameTimeNotify)
public final class HandlerClientLockGameTimeNotify extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var packet = ClientLockGameTimeNotify.parseFrom(payload);
        // session.getPlayer().getWorld().lockTime(packet.getIsLock());
        // TODO: figure out what to implement here
        if (packet.getIsLock())
            Grasscutter.getLogger()
                    .warn(
                            "Invalid 'ClientLockGameTimeNotify' received; value is true. (please report to development channel)");
    }
}
