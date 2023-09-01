package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.UpdatePlayerShowNameCardListReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketUpdatePlayerShowNameCardListRsp;

@Opcodes(PacketOpcodes.UpdatePlayerShowNameCardListReq)
public class HandlerUpdatePlayerShowNameCardListReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req =
                UpdatePlayerShowNameCardListReqOuterClass.UpdatePlayerShowNameCardListReq.parseFrom(
                        payload);

        session.getPlayer().setShowNameCardList(req.getShowNameCardIdListList());

        session.send(new PacketUpdatePlayerShowNameCardListRsp(req.getShowNameCardIdListList()));
    }
}
