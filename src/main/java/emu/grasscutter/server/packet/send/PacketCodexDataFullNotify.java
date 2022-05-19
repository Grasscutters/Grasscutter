package emu.grasscutter.server.packet.send;

import java.util.Collections;
import java.util.List;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.CodexDataFullNotifyOuterClass.CodexDataFullNotify;
import emu.grasscutter.net.proto.CodexTypeDataOuterClass.CodexTypeData;
import emu.grasscutter.net.proto.CodexTypeOuterClass;
import emu.grasscutter.server.game.GameSession;

public class PacketCodexDataFullNotify extends BasePacket {
    public PacketCodexDataFullNotify(Player player) {
        super(PacketOpcodes.CodexDataFullNotify, true);

        //Quests
        CodexTypeData.Builder questTypeData = CodexTypeData.newBuilder()
                .setTypeValue(1);

        //Tips
        CodexTypeData.Builder pushTipsTypeData = CodexTypeData.newBuilder()
                .setTypeValue(6);

        //Views
        CodexTypeData.Builder viewTypeData = CodexTypeData.newBuilder()
                .setTypeValue(7);

        //Weapons
        CodexTypeData.Builder weaponTypeData = CodexTypeData.newBuilder()
                .setTypeValue(2);


        player.getQuestManager().forEachMainQuest(mainQuest -> {
            if(mainQuest.isFinished()){
                var codexQuest = GameData.getCodexQuestIdMap().get(mainQuest.getParentQuestId());
                if(codexQuest != null){
                    questTypeData.addCodexIdList(codexQuest.getId()).addAllHaveViewedList(Collections.singleton(true));
                }
            }
        });

        CodexDataFullNotify.Builder proto = CodexDataFullNotify.newBuilder()
                .addTypeDataList(questTypeData.build())
                .addTypeDataList(pushTipsTypeData.build())
                .addTypeDataList(viewTypeData.build())
                .addTypeDataList(weaponTypeData);

        this.setData(proto);
    }
}
