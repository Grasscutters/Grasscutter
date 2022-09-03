package emu.grasscutter.server.packet.recv;

import java.lang.invoke.StringConcatFactory;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ForgeStartReqOuterClass;
import emu.grasscutter.net.proto.DeleteFriendReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketDelMailRsp;

@Opcodes(PacketOpcodes.ForgeStartReq)
public class HandlerForgeStartReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        ForgeStartReqOuterClass.ForgeStartReq req = ForgeStartReqOuterClass.ForgeStartReq.parseFrom(payload);
        session.getPlayer().getForgingManager().handleForgeStartReq(req);
    }

}
