package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.*;

@Opcodes(PacketOpcodes.SetWidgetSlotReq)
public class HandlerSetWidgetSlotReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        SetWidgetSlotReqOuterClass.SetWidgetSlotReq req =
                SetWidgetSlotReqOuterClass.SetWidgetSlotReq.parseFrom(payload);

        Player player = session.getPlayer();
        player.setWidgetId(req.getMaterialId());

        // WidgetSlotChangeNotify op & slot key
        session.send(
                new PacketWidgetSlotChangeNotify(
                        WidgetSlotOpOuterClass.WidgetSlotOp.WIDGET_SLOT_OP_DETACH));

        // only attaching the widget can set it
        if (req.getOp() == WidgetSlotOpOuterClass.WidgetSlotOp.WIDGET_SLOT_OP_ATTACH) {
            // WidgetSlotChangeNotify slot
            session.send(new PacketWidgetSlotChangeNotify(req.getMaterialId()));
        }

        // SetWidgetSlotRsp
        session.send(new PacketSetWidgetSlotRsp(req.getMaterialId()));
    }
}
