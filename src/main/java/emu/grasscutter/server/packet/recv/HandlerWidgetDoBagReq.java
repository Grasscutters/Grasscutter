package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.GadgetData;
import emu.grasscutter.game.entity.EntityVehicle;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.WidgetDoBagReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketWidgetCoolDownNotify;
import emu.grasscutter.server.packet.send.PacketWidgetDoBagRsp;
import emu.grasscutter.server.packet.send.PacketWidgetGadgetDataNotify;
import emu.grasscutter.utils.Position;

import java.util.List;

@Opcodes(PacketOpcodes.WidgetDoBagReq)
public class HandlerWidgetDoBagReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        WidgetDoBagReqOuterClass.WidgetDoBagReq req = WidgetDoBagReqOuterClass.WidgetDoBagReq.parseFrom(payload);
        switch (req.getMaterialId()) {
            case 220026 -> {
                GadgetData gadgetData = GameData.getGadgetDataMap().get(70500025);
                Position pos = new Position(req.getWidgetCreatorInfo().getLocationInfo().getPos());
                Position rot = new Position(req.getWidgetCreatorInfo().getLocationInfo().getRot());
                GameEntity entity = new EntityVehicle(
                        session.getPlayer().getScene(),
                        session.getPlayer(),
                        gadgetData.getId(),
                        0,
                        pos,
                        rot
                );

                session.getPlayer().getScene().addEntity(entity);

                session.send(new PacketWidgetGadgetDataNotify(70500025, entity.getId()));  // ???
                session.send(new PacketWidgetCoolDownNotify(15, System.currentTimeMillis() + 5000L, true));
                session.send(new PacketWidgetCoolDownNotify(15, System.currentTimeMillis() + 5000L, true));
                // Send twice, and I don't know why, Ask mhy
                session.send(new PacketWidgetDoBagRsp());
            }
            case 220047 -> {
                GadgetData gadgetData = GameData.getGadgetDataMap().get(70800058);
                Position pos = new Position(req.getWidgetCreatorInfo().getLocationInfo().getPos());
                Position rot = new Position(req.getWidgetCreatorInfo().getLocationInfo().getRot());
                GameEntity entity = new EntityVehicle(
                        session.getPlayer().getScene(),
                        session.getPlayer(),
                        gadgetData.getId(),
                        0,
                        pos,
                        rot
                );

                session.getPlayer().getScene().addEntity(entity);

                session.send(new PacketWidgetGadgetDataNotify(70800058, entity.getId()));  // ???
                // Send twice, and I don't know why, Ask mhy
                session.send(new PacketWidgetDoBagRsp());
            }
            default -> {
                session.send(new PacketWidgetDoBagRsp());
            }

        }
    }

}
