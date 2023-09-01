package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.entity.EntityVehicle;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.WidgetDoBagReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.*;

@Opcodes(PacketOpcodes.WidgetDoBagReq)
public class HandlerWidgetDoBagReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        WidgetDoBagReqOuterClass.WidgetDoBagReq req =
                WidgetDoBagReqOuterClass.WidgetDoBagReq.parseFrom(payload);
        var locationInfo = req.getWidgetCreatorInfo().getLocationInfo();
        Position pos = new Position(locationInfo.getPos());
        Position rot = new Position(locationInfo.getRot());
        switch (req.getMaterialId()) {
            case 220026 -> {
                this.spawnVehicle(session, 70500025, pos, rot);
                session.send(new PacketWidgetCoolDownNotify(15, System.currentTimeMillis() + 5000L, true));
                session.send(new PacketWidgetCoolDownNotify(15, System.currentTimeMillis() + 5000L, true));
            }
            case 220047 -> this.spawnVehicle(session, 70800058, pos, rot);
            default -> {}
        }
        session.send(new PacketWidgetDoBagRsp());
    }

    private void spawnVehicle(GameSession session, int gadgetId, Position pos, Position rot)
            throws Exception {
        var player = session.getPlayer();
        var scene = player.getScene();
        var entity = new EntityVehicle(scene, player, gadgetId, 0, pos, rot);
        scene.addEntity(entity);
        session.send(new PacketWidgetGadgetDataNotify(gadgetId, entity.getId()));
    }
}
