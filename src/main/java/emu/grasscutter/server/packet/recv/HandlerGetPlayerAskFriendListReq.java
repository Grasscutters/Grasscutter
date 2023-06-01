package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetPlayerAskFriendListRsp;

@Opcodes(PacketOpcodes.GetPlayerAskFriendListReq)
public class HandlerGetPlayerAskFriendListReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        session.send(new PacketGetPlayerAskFriendListRsp(session.getPlayer()));
    }
}
