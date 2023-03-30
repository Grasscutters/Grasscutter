// removed due to proto not existing

/*
package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.LaunchFireworksReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketFireworksLaunchDataNotify;
import emu.grasscutter.server.packet.send.PacketLaunchFireworksRsp;

@Opcodes(PacketOpcodes.LaunchFireworksReq)
public class HandlerLaunchFireworksReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {

        var req
                = LaunchFireworksReqOuterClass.LaunchFireworksReq.parseFrom(payload);


        session.send(new PacketFireworksLaunchDataNotify(req.getSchemeData()));
        session.send(new PacketLaunchFireworksRsp());
    }
}
*/