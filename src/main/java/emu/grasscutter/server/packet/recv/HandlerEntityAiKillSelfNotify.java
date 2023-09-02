package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.entity.Rebornable;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EntityAiKillSelfNotifyOuterClass;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.EntityAiKillSelfNotify)
public class HandlerEntityAiKillSelfNotify extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var entityId =
                EntityAiKillSelfNotifyOuterClass.EntityAiKillSelfNotify.parseFrom(payload).getEntityId();
        if (session.getPlayer().getScene().getEntityById(entityId) instanceof Rebornable rebornable) {
            rebornable.onAiKillSelf();
        }
    }
}
