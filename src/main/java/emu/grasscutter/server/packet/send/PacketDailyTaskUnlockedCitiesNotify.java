package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DailyTaskUnlockedCitiesNotifyOuterClass;

import java.util.Set;

public class PacketDailyTaskUnlockedCitiesNotify extends BasePacket {
    public PacketDailyTaskUnlockedCitiesNotify(Set<Integer> unlockedCities) {
        super(PacketOpcodes.DailyTaskUnlockedCitiesNotify);

        var notify = DailyTaskUnlockedCitiesNotifyOuterClass.DailyTaskUnlockedCitiesNotify.newBuilder()
            .addAllUnlockedCityList(unlockedCities)
            .build();

        this.setData(notify);
    }
}
