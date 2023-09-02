package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.achievement.Achievement;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AchievementUpdateNotifyOuterClass;
import java.util.List;

public class PacketAchievementUpdateNotify extends BasePacket {
    public PacketAchievementUpdateNotify(List<Achievement> achievements) {
        super(PacketOpcodes.AchievementUpdateNotify);

        var notify =
                AchievementUpdateNotifyOuterClass.AchievementUpdateNotify.newBuilder()
                        .addAllAchievementList(achievements.stream().map(Achievement::toProto).toList())
                        .build();

        this.setData(notify);
    }
}
