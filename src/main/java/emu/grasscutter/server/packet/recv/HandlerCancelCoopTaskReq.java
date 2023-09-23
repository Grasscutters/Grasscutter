package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.CancelCoopTaskReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketCancelCoopTaskRsp;

@Opcodes(PacketOpcodes.CancelCoopTaskReq)
public class HandlerCancelCoopTaskReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        CancelCoopTaskReqOuterClass.CancelCoopTaskReq req =
                CancelCoopTaskReqOuterClass.CancelCoopTaskReq.parseFrom(payload);
        var chapterId = req.getChapterId();
        Grasscutter.getLogger().warn("Call to unimplemented packet CancelCoopTaskReq");
        // TODO: Actually cancel the quests.
        session.send(new PacketCancelCoopTaskRsp(chapterId));
    }
}
