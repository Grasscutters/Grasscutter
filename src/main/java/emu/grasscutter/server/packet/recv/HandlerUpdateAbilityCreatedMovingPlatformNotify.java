package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.entity.platform.EntityPlatform;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PacketHeadOuterClass;
import emu.grasscutter.net.proto.UpdateAbilityCreatedMovingPlatformNotifyOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketPlatformStartRouteNotify;
import emu.grasscutter.server.packet.send.PacketPlatformStopRouteNotify;

@Opcodes(PacketOpcodes.UpdateAbilityCreatedMovingPlatformNotify)
public class HandlerUpdateAbilityCreatedMovingPlatformNotify extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var sequence = PacketHeadOuterClass.PacketHead.parseFrom(header).getClientSequenceId();
        var notify = UpdateAbilityCreatedMovingPlatformNotifyOuterClass.UpdateAbilityCreatedMovingPlatformNotify.parseFrom(payload);
        var entity = session.getPlayer().getScene().getEntityById(notify.getEntityId());

        if (!(entity instanceof EntityPlatform)) {
            return;
        }

        switch (notify.getOpType()) {
            case OP_TYPE_ACTIVATE -> session.send(new PacketPlatformStartRouteNotify(sequence, (EntityPlatform) entity, session.getPlayer().getScene()));
            case OP_TYPE_DEACTIVATE -> session.send(new PacketPlatformStopRouteNotify(sequence, (EntityPlatform) entity, session.getPlayer().getScene()));
        }
    }
}
