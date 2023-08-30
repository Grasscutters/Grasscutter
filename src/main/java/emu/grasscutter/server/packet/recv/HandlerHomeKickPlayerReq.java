package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeKickPlayerReqOuterClass;
import emu.grasscutter.net.proto.RetcodeOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketHomeKickPlayerRsp;

import java.util.concurrent.atomic.AtomicBoolean;

@Opcodes(PacketOpcodes.HomeKickPlayerReq)
public class HandlerHomeKickPlayerReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = HomeKickPlayerReqOuterClass.HomeKickPlayerReq.parseFrom(payload);

        var success = new AtomicBoolean();
        session.getPlayer().getCurHomeWorld().getGuests().stream()
            .filter(player -> player.getUid() == req.getTargetUid())
            .findFirst()
            .ifPresent(player -> {
                success.set(session.getServer().getHomeWorldMPSystem().kickPlayerFromHome(session.getPlayer(), player.getUid()));
            });

        session.send(new PacketHomeKickPlayerRsp(success.get() ? 0 : RetcodeOuterClass.Retcode.RET_FAIL_VALUE, req));
    }
}
