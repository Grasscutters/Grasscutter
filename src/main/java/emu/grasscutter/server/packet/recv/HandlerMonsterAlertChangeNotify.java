package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.MonsterAlertChangeNotifyOuterClass.MonsterAlertChangeNotify;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.ScriptArgs;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.MonsterAlertChangeNotify)
public class HandlerMonsterAlertChangeNotify extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var notify = MonsterAlertChangeNotify.parseFrom(payload);

        var player = session.getPlayer();

        if (notify.getIsAlert() != 0) {
            for (var monsterId : notify.getMonsterEntityListList()) {
                var monster = (EntityMonster) player.getScene().getEntityById(monsterId);
                if (monster != null && monster.getPlayerOnBattle().isEmpty()) {
                    monster
                            .getScene()
                            .getScriptManager()
                            .callEvent(
                                    new ScriptArgs(
                                            monster.getGroupId(), EventType.EVENT_MONSTER_BATTLE, monster.getConfigId()));
                }

                if (monster != null) monster.getPlayerOnBattle().add(player);
            }
        }

        // TODO: Research invisible monsters
    }
}
