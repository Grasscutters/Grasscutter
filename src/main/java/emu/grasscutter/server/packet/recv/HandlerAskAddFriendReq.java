package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AskAddFriendReqOuterClass.AskAddFriendReq;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.AskAddFriendReq)
public class HandlerAskAddFriendReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        AskAddFriendReq req = AskAddFriendReq.parseFrom(payload);

        session.getPlayer().getFriendsList().sendFriendRequest(req.getTargetUid());
    }

}
