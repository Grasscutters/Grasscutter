package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SceneKickPlayerReqOuterClass.SceneKickPlayerReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketSceneKickPlayerRsp;

@Opcodes(PacketOpcodes.SceneKickPlayerReq)
public class HandlerSceneKickPlayerReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        SceneKickPlayerReq req = SceneKickPlayerReq.parseFrom(payload);

        if (session.getServer().getMultiplayerSystem().kickPlayer(session.getPlayer(), req.getTargetUid())) {
            // Success
            session.send(new PacketSceneKickPlayerRsp(req.getTargetUid()));
        } else {
            // Fail
            session.send(new PacketSceneKickPlayerRsp());
        }
    }

}
