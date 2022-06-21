package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HitTreeNotifyOuterClass.HitTreeNotify;
import emu.grasscutter.server.game.GameSession;

/**
 * Implement Deforestation Function
 */
@Opcodes(PacketOpcodes.HitTreeNotify)
public class HandlerHitTreeNotify extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        HitTreeNotify hit = HitTreeNotify.parseFrom(payload);
        session.getPlayer().getDeforestationManager().onDeforestationInvoke(hit);
    }
}