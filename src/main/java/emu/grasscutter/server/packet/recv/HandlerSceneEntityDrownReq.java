package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.entity.GenshinEntity;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SceneEntityDrownReqOuterClass.SceneEntityDrownReq;
import emu.grasscutter.net.proto.VisionTypeOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketLifeStateChangeNotify;
import emu.grasscutter.server.packet.send.PacketSceneEntityDrownRsp;

@Opcodes(PacketOpcodes.SceneEntityDrownReq)
public class HandlerSceneEntityDrownReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        SceneEntityDrownReq req = SceneEntityDrownReq.parseFrom(payload);


        GenshinEntity entity = session.getPlayer().getScene().getEntityById(req.getEntityId());


        PacketLifeStateChangeNotify lifeStateChangeNotify = new PacketLifeStateChangeNotify(entity, entity, LifeState.LIFE_DEAD);
        PacketSceneEntityDrownRsp drownRsp = new PacketSceneEntityDrownRsp(req.getEntityId());



        //kill entity + broadcast it

        session.getPlayer().getScene().broadcastPacket(lifeStateChangeNotify);
        session.getPlayer().getScene().broadcastPacket(drownRsp);

        //TODO: make a list somewhere of all entities to remove per tick rather than one by one

        session.getPlayer().getScene().removeEntity(entity, VisionTypeOuterClass.VisionType.VisionDie);

    }

}
