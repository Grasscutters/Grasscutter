package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SceneEntityDrownReqOuterClass.SceneEntityDrownReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketSceneEntityDrownRsp;

@Opcodes(PacketOpcodes.SceneEntityDrownReq)
public class HandlerSceneEntityDrownReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        SceneEntityDrownReq req = SceneEntityDrownReq.parseFrom(payload);

        GameEntity entity = session.getPlayer().getScene().getEntityById(req.getEntityId());

        if (entity == null || !(entity instanceof EntityMonster || entity instanceof EntityAvatar)) {
            return;
        }

        entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, 0);

        // TODO: make a list somewhere of all entities to remove per tick rather than one by one
        session.getPlayer().getScene().killEntity(entity, 0);
        session
                .getPlayer()
                .getScene()
                .broadcastPacket(new PacketSceneEntityDrownRsp(req.getEntityId()));
    }
}
