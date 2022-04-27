package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetWidgetSlotItemOuterClass;
import emu.grasscutter.net.proto.SetWidgetSlotReqOuterClass;
import emu.grasscutter.net.proto.SetWidgetSlotRspOuterClass;
import emu.grasscutter.net.proto.WidgetSlotChangeNotifyOuterClass;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.SetWidgetSlotReq)
public class HandlerSetWidgetSlotReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        BasePacket packet2 = new BasePacket(PacketOpcodes.SetWidgetSlotRsp);
        SetWidgetSlotReqOuterClass.SetWidgetSlotReq req = SetWidgetSlotReqOuterClass.SetWidgetSlotReq.parseFrom(payload);
        packet2.setData(SetWidgetSlotRspOuterClass.SetWidgetSlotRsp.newBuilder().setWidgetItemId(req.getWidgetItemId()));

        // I think there's something wrong, but it works sry :(

        BasePacket notifyPacket1 = new BasePacket(PacketOpcodes.WidgetSlotChangeNotify);
        notifyPacket1.setData(WidgetSlotChangeNotifyOuterClass.WidgetSlotChangeNotify.newBuilder()
                .setUnequip(req.getUnequip())
                .setWidgetItem(GetWidgetSlotItemOuterClass.GetWidgetSlotItem.newBuilder().setEquiped(!req.getUnequip()).build()));
        session.send(notifyPacket1);

        if (!req.getUnequip()) {
            BasePacket notifyPacket2 = new BasePacket(PacketOpcodes.WidgetSlotChangeNotify);
            notifyPacket2.setData(WidgetSlotChangeNotifyOuterClass.WidgetSlotChangeNotify.newBuilder()
                    .setWidgetItem(GetWidgetSlotItemOuterClass.GetWidgetSlotItem.newBuilder().setWidgetId(req.getWidgetItemId()).setEquiped(true).build()));
            session.send(notifyPacket2);
        }

        session.getPlayer().setEquipedWidget(req.getUnequip() ? 0 : req.getWidgetItemId());
        session.getPlayer().save();

        session.send(packet2);
    }
}
