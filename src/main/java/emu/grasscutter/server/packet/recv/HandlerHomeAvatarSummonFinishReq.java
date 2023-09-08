package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeAvatarSummonFinishReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketHomeAvatarSummonAllEventNotify;
import emu.grasscutter.server.packet.send.PacketHomeAvatarSummonFinishRsp;

@Opcodes(PacketOpcodes.HomeAvatarSummonFinishReq)
public class HandlerHomeAvatarSummonFinishReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = HomeAvatarSummonFinishReqOuterClass.HomeAvatarSummonFinishReq.parseFrom(payload);
        var player = session.getPlayer();
        player.getCurHomeWorld().getModuleManager().onFinishSummonEvent(req.getEventId());
        session.send(new PacketHomeAvatarSummonAllEventNotify(session.getPlayer()));
        session.send(new PacketHomeAvatarSummonFinishRsp(req.getEventId()));
    }
}
