package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SkipPlayerGameTimeReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketPlayerGameTimeNotify;
import emu.grasscutter.server.packet.send.PacketSkipPlayerGameTimeRsp;

@Opcodes(PacketOpcodes.SkipPlayerGameTimeReq)
public class HandlerSkipPlayerGameTimeReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = SkipPlayerGameTimeReqOuterClass.SkipPlayerGameTimeReq.parseFrom(payload);
        var player = session.getPlayer();
        player.getScene().setTime(req.getGameTime());
        player.getScene().broadcastPacket(new PacketPlayerGameTimeNotify(player));
        player.sendPacket(new PacketSkipPlayerGameTimeRsp(req));
    }
}
