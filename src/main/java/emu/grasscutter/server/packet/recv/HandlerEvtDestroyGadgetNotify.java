package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.EvtDestroyGadgetNotifyOuterClass.EvtDestroyGadgetNotify;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.EvtDestroyGadgetNotify)
public class HandlerEvtDestroyGadgetNotify extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        EvtDestroyGadgetNotify notify = EvtDestroyGadgetNotify.parseFrom(payload);

        session.getPlayer().getScene().onPlayerDestroyGadget(notify.getEntityId());
    }
}
