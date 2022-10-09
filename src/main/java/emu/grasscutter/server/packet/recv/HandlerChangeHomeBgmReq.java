package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PacketHeadOuterClass;
import emu.grasscutter.net.proto.Unk2700BEDLIGJANCJClientReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketChangeHomeBgmNotify;
import emu.grasscutter.server.packet.send.PacketChangeHomeBgmRsp;

@Opcodes(PacketOpcodes.Unk2700_BEDLIGJANCJ_ClientReq)
public class HandlerChangeHomeBgmReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var head = PacketHeadOuterClass.PacketHead.parseFrom(header);
        var req = Unk2700BEDLIGJANCJClientReq.Unk2700_BEDLIGJANCJ_ClientReq.parseFrom(payload);

        int homeBgmId = req.getUnk2700BJHAMKKECEI();

        session.send(new PacketChangeHomeBgmNotify(head.getClientSequenceId(), homeBgmId));
        session.send(new PacketChangeHomeBgmRsp(head.getClientSequenceId()));
    }
}
