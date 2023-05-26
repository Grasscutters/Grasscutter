package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.props.PlayerProperty;
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
        session.getPlayer().setProperty(PlayerProperty.PROP_IS_GAME_TIME_LOCKED, packet.getIsLock() ? 1 : 0);
    }
}
