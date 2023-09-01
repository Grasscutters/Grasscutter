package emu.grasscutter.server.packet.send;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.dungeon.DungeonEntryData;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;

public class PacketGetDailyDungeonEntryInfoRsp extends BasePacket {

    public PacketGetDailyDungeonEntryInfoRsp(Integer sceneID) {
        super(PacketOpcodes.GetDailyDungeonEntryInfoRsp);

        var resp = GetDailyDungeonEntryInfoRspOuterClass.GetDailyDungeonEntryInfoRsp.newBuilder();

        for (var info :
                GameData.getDungeonEntryDataMap().values().parallelStream()
                        .filter(d -> d.getSceneId() == sceneID)
                        .map(this::getDungonEntryInfo)
                        .toList()) resp.addDailyDungeonInfoList(info);

        this.setData(resp.build());
    }

    private DailyDungeonEntryInfoOuterClass.DailyDungeonEntryInfo getDungonEntryInfo(
            DungeonEntryData data) {
        var dungeonEntryId = data.getDungeonEntryId();
        var id = data.getId();

        // TODO
        DungeonEntryInfoOuterClass.DungeonEntryInfo dungeonEntryInfo =
                DungeonEntryInfoOuterClass.DungeonEntryInfo.newBuilder().setDungeonId(130).build();

        var builder = DailyDungeonEntryInfoOuterClass.DailyDungeonEntryInfo.newBuilder();

        builder.setDungeonEntryId(dungeonEntryId);
        builder.setDungeonEntryConfigId(id);
        builder.setRecommendDungeonEntryInfo(dungeonEntryInfo);
        return builder.build();
    }
}
