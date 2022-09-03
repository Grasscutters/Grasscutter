package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.FireworkSetReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketFireworkSetNotify;
import emu.grasscutter.server.packet.send.PacketFireworkSetRsp;

@Opcodes(PacketOpcodes.FireworkSetReq)
public class HandlerFireworkSetReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {

        var req
                = FireworkSetReqOuterClass.FireworkSetReq.parseFrom(payload);


        session.send(new PacketFireworkSetNotify(req.getData()));
        session.send(new PacketFireworkSetRsp());
    }
}
