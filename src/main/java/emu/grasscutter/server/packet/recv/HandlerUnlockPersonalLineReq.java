package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.GameData;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.net.proto.UnlockPersonalLineReqOuterClass.UnlockPersonalLineReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketUnlockPersonalLineRsp;

@Opcodes(PacketOpcodes.UnlockPersonalLineReq)
public class HandlerUnlockPersonalLineReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = UnlockPersonalLineReq.parseFrom(payload);
        var data = GameData.getPersonalLineDataMap().get(req.getPersonalLineId());
        if (data == null) {
            session.send(new PacketUnlockPersonalLineRsp(req.getPersonalLineId(), Retcode.RET_FAIL));
            return;
        }

        session.getPlayer().addPersonalLine(data.getId());
        session.getPlayer().useLegendaryKey(1);

        session.send(new PacketUnlockPersonalLineRsp(data.getId(), 1, data.getChapterId()));
    }
}
