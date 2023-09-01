package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.LaunchFireworksReqOuterClass.LaunchFireworksReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.*;

@Opcodes(PacketOpcodes.LaunchFireworksReq)
public class HandlerLaunchFireworksReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = LaunchFireworksReq.parseFrom(payload);
        session.send(new PacketFireworksLaunchDataNotify(req.getSchemeData()));
        session.send(new PacketLaunchFireworksRsp());
    }
}
