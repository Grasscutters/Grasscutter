package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SetFriendEnterHomeOptionReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketPlayerHomeCompInfoNotify;
import emu.grasscutter.server.packet.send.PacketSetFriendEnterHomeOptionRsp;

@Opcodes(PacketOpcodes.SetFriendEnterHomeOptionReq)
public class HandlerSetFriendEnterHomeOptionReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = SetFriendEnterHomeOptionReqOuterClass.SetFriendEnterHomeOptionReq.parseFrom(payload);

        session.getPlayer().getHome().setEnterHomeOption(req.getOptionValue());
        session.getPlayer().getHome().save();

        session.send(new PacketPlayerHomeCompInfoNotify(session.getPlayer()));
        session.send(new PacketSetFriendEnterHomeOptionRsp());
    }
}
