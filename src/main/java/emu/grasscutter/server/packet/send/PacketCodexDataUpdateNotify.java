package emu.grasscutter.server.packet.send;

import java.util.Collections;
import java.util.List;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.GameMainQuest;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.CodexDataUpdateNotifyOuterClass.CodexDataUpdateNotify;
import emu.grasscutter.server.game.GameSession;

public class PacketCodexDataUpdateNotify extends BasePacket {
    public PacketCodexDataUpdateNotify(GameMainQuest quest) {
        super(PacketOpcodes.CodexDataUpdateNotify, true);
        var codexQuest = GameData.getCodexQuestIdMap().get(quest.getParentQuestId());
        if(codexQuest != null){
            CodexDataUpdateNotify proto = CodexDataUpdateNotify.newBuilder()
                    .setTypeValue(1)
                    .setId(codexQuest.getId())
                    .build();
            this.setData(proto);
        }
    }
}
