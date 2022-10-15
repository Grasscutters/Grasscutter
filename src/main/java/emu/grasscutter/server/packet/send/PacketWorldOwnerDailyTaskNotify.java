package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.WorldOwnerDailyTaskNotifyOuterClass;

public class PacketWorldOwnerDailyTaskNotify extends BasePacket {
    public PacketWorldOwnerDailyTaskNotify(Player player) {
        super(PacketOpcodes.WorldOwnerDailyTaskNotify);

        var manager = player.getDailyTaskManager();
        var notify = WorldOwnerDailyTaskNotifyOuterClass.WorldOwnerDailyTaskNotify.newBuilder();

        manager.getDailyTasks().forEach(dailyTask -> notify.addTaskList(dailyTask.toProto()));
        notify.setFilterCityId(manager.getCityId());

        this.setData(notify.build());
    }
}
