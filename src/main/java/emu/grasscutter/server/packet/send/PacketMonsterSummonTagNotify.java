package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.MonsterSummonTagNotifyOuterClass.MonsterSummonTagNotify;
import java.util.*;
import static java.util.Map.entry;

public class PacketMonsterSummonTagNotify extends BasePacket {

    public PacketMonsterSummonTagNotify(EntityMonster monsterEntity) {
        super(PacketOpcodes.MonsterSummonTagNotify);

        var proto =
                MonsterSummonTagNotify.newBuilder()
                        .setMonsterEntityId(monsterEntity.getId());
        monsterEntity.getSummonTagMap().forEach((k, v) -> proto.putSummonTagMap(k, v == null ? 0 : 1));

        this.setData(proto.build());
    }
}
