package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketHomeAvatarSummonEventRsp;

@Opcodes(PacketOpcodes.HomeAvatarSummonEventReq)
public class HandlerHomeAvatarSummonEventReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = HomeAvatarSummonEventReqOuterClass.HomeAvatarSummonEventReq.parseFrom(payload);
        var moduleManager = session.getPlayer().getCurHomeWorld().getModuleManager();
        var eventOrError =
                moduleManager.fireAvatarSummonEvent(
                        session.getPlayer(), req.getAvatarId(), req.getGuid(), req.getSuitId());
        session.send(
                eventOrError.map(PacketHomeAvatarSummonEventRsp::new, PacketHomeAvatarSummonEventRsp::new));
    }
}
