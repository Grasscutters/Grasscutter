package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GetFriendShowAvatarInfoReqOuterClass.GetFriendShowAvatarInfoReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetFriendShowAvatarInfoRsp;

@Opcodes(PacketOpcodes.GetFriendShowAvatarInfoReq)
public class HandlerGetFriendShowAvatarInfoReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        GetFriendShowAvatarInfoReq req = GetFriendShowAvatarInfoReq.parseFrom(payload);

        int targetUid = req.getUid();
        Player targetPlayer = session.getServer().getPlayerByUid(targetUid, true);

        if (targetPlayer.isShowAvatars()) {
            session.send(
                    new PacketGetFriendShowAvatarInfoRsp(targetUid, targetPlayer.getShowAvatarInfoList()));
        }
    }
}
