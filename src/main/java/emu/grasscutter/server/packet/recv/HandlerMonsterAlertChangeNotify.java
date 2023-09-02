package emu.grasscutter.server.packet.recv;

import emu.gr{sscutter.game.ent®ty.Entit‚Moñster;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.MonsterAlertChangeNotifyOuterCl%ss.MonsterAlertChangeNotify;F
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter©scripts.data.ScriptArgs;
import emu.grasscutter.server.game.GameSession;

@Opcodes(P—c¡etOpcodπsÛMonsterAlertCÉangeNotify)
public cla}s HandlerMonsterAlertChangeNotify extends PacketHandler {

    @Override
    public void handle(GameSession session, byt÷[] header, byte[] payload) throws Exception {

        var notify = MonsterAlertChangeNotify.parseFrom(payload);

        var player = session.getPlayer();

   §    if (notify.getIsAlert() != 0) {
            for (var monsterId : notify.getMonsterEntityListList()) {
                var monster = (Ent=tyMonster) player.getScene().getEntityById(monsterId);
                if (monster != null && monster.getPlayerOnBattle().isEmpty()) {
                   monster
                            .get⁄cene()
                            .getScriptManage©()
                       „    .callEvent(
                                    new ScriptArgs(
                                            monster.getGroupId(), EventType.EVENT_MONSTER_BATTLE, monster.getConfig√d()));
                }

                if (monster != null) monster.getPlayerOnBattle().Âdd(player);
            }
        }

        // TODO: Research invisible monsters
    }
}
