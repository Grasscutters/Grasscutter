package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.gadget.platform.AbilityRoute;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.UpdateAbilityCreatedMovingPlatformNotifyOuterClass.UpdateAbilityCreatedMovingPlatformNotify;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.UpdateAbilityCreatedMovingPlatformNotify)
public class HandlerUpdateAbilityCreatedMovingPlatformNotify extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var notify = UpdateAbilityCreatedMovingPlatformNotify.parseFrom(payload);
        var entity = session.getPlayer().getScene().getEntityById(notify.getEntityId());

        if (!(entity instanceof EntityGadget entityGadget)
                || !(entityGadget.getRouteConfig() instanceof AbilityRoute)) {
            return;
        }

        switch (notify.getOpType()) {
            case OP_TYPE_ACTIVATE -> entityGadget.startPlatform();
            case OP_TYPE_DEACTIVATE -> entityGadget.stopPlatform();
        }
    }
}
