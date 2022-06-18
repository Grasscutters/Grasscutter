package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.ScenePointEntry;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SceneTransToPointReqOuterClass.SceneTransToPointReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketSceneTransToPointRsp;
import emu.grasscutter.utils.Position;

@Opcodes(PacketOpcodes.SceneTransToPointReq)
public class HandlerSceneTransToPointReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        SceneTransToPointReq req = SceneTransToPointReq.parseFrom(payload);

        String code = req.getSceneId() + "_" + req.getPointId();
        ScenePointEntry scenePointEntry = GameData.getScenePointEntries().get(code);

        if (scenePointEntry != null) {
            float x = scenePointEntry.getPointData().getTranPos().getX();
            float y = scenePointEntry.getPointData().getTranPos().getY();
            float z = scenePointEntry.getPointData().getTranPos().getZ();

            session.getPlayer().getWorld().transferPlayerToScene(session.getPlayer(), req.getSceneId(), new Position(x, y, z));
            session.send(new PacketSceneTransToPointRsp(session.getPlayer(), req.getPointId(), req.getSceneId()));
        } else {
            session.send(new PacketSceneTransToPointRsp());
        }
    }

}
