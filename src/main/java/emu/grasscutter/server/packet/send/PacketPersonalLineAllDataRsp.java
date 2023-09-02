package emu.grasscutter.server.packet.send;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PersonalLineAllDataRspOuterClass;
import java.util.*;
import java.util.stream.Collectors;

public class PacketPersonalLineAllDataRsp extends BasePacket {

    public PacketPersonalLineAllDataRsp(Collection<GameMainQuest> gameMainQuestList) {
        super(PacketOpcodes.PersonalLineAllDataRsp);

        var proto = PersonalLineAllDataRspOuterClass.PersonalLineAllDataRsp.newBuilder();

        var questList =
                gameMainQuestList.stream()
                        .map(GameMainQuest::getChildQuests)
                        .map(Map::values)
                        .flatMap(Collection::stream)
                        .map(GameQuest::getSubQuestId)
                        .collect(Collectors.toSet());

        GameData.getPersonalLineDataMap().values().stream()
                .filter(i -> !questList.contains(i.getStartQuestId()))
                .forEach(i -> proto.addCanBeUnlockedPersonalLineList(i.getId()));

        this.setData(proto);
    }
}
