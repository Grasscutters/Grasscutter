package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeModuleSeenReqOuterClass.HomeModuleSeenReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketHomeModuleSeenRsp;

@Opcodes(PacketOpcodes.HomeModuleSeenReq)
public class HandlerHomeModuleSeenReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = HomeModuleSeenReq.parseFrom(payload);
        var seen = req.getSeenModuleIdListList();
        // TODO: Make 'seen' status persist
        session.send(new PacketHomeModuleSeenRsp(seen));
        
    }
}
