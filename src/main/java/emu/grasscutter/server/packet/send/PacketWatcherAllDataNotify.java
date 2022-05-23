package emu.grasscutter.server.packet.send;

import emu.grasscutter.data.GameData;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.WatcherAllDataNotifyOuterClass.WatcherAllDataNotify;
import emu.grasscutter.server.game.GameSession;

import java.util.ArrayList;
import java.util.List;

public class PacketWatcherAllDataNotify extends BasePacket {

	public PacketWatcherAllDataNotify(List<Integer> watchers) {
        super(PacketOpcodes.WatcherAllDataNotify);

        WatcherAllDataNotify.Builder proto = WatcherAllDataNotify.newBuilder()
                .addAllWatcherList(watchers);
        this.setData(proto.build());
    }
}
