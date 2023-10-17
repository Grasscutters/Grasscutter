package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GetDungeonEntryExploreConditionReqOuterClass.GetDungeonEntryExploreConditionReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketDungeonEntryToBeExploreNotify;

@Opcodes(PacketOpcodes.GetDungeonEntryExploreConditionReq)
public class HandlerGetDungeonEntryExploreConditionReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = GetDungeonEntryExploreConditionReq.parseFrom(payload);

        // TODO Send GetDungeonEntryExploreConditionRsp if condition
        // (adventurer rank or quest completion) is not met. Parse
        // dungeon entry conditions from DungeonEntryExcelConfigData.json.
        // session.send(new PacketGetDungeonEntryExploreConditionRsp(req.getDungeonEntryConfigId()));

        // For now, just unlock any domain the player touches.
        session.send(
                new PacketDungeonEntryToBeExploreNotify(
                        req.getDungeonEntryScenePointId(), req.getSceneId(), req.getDungeonEntryConfigId()));
    }
}
