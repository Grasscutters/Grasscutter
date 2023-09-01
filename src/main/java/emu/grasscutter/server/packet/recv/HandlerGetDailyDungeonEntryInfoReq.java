package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GetDailyDungeonEntryInfoReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetDailyDungeonEntryInfoRsp;

@Opcodes(PacketOpcodes.GetDailyDungeonEntryInfoReq)
public class HandlerGetDailyDungeonEntryInfoReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = GetDailyDungeonEntryInfoReqOuterClass.GetDailyDungeonEntryInfoReq.parseFrom(payload);

        session.send(new PacketGetDailyDungeonEntryInfoRsp(req.getSceneId()));
    }
}
