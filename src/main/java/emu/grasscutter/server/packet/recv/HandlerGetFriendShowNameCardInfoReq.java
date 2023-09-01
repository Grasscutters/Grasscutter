package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GetFriendShowNameCardInfoReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetFriendShowNameCardInfoRsp;

@Opcodes(PacketOpcodes.GetFriendShowNameCardInfoReq)
public class HandlerGetFriendShowNameCardInfoReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req =
                GetFriendShowNameCardInfoReqOuterClass.GetFriendShowNameCardInfoReq.parseFrom(payload);

        int targetUid = req.getUid();
        Player target = session.getServer().getPlayerByUid(targetUid, true);

        session.send(
                new PacketGetFriendShowNameCardInfoRsp(targetUid, target.getShowNameCardInfoList()));
    }
}
