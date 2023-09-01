package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.LevelupCityReqOuterClass.LevelupCityReq;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.LevelupCityReq)
public class HandlerLevelupCityReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        LevelupCityReq req = LevelupCityReq.parseFrom(payload);

        // Level up city
        session
                .getPlayer()
                .getSotsManager()
                .levelUpSotS(req.getAreaId(), req.getSceneId(), req.getItemNum());
    }
}
