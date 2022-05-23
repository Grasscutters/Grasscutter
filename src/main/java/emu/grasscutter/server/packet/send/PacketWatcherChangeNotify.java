package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.WatcherChangeNotifyOuterClass.WatcherChangeNotify;

import java.util.List;

public class PacketWatcherChangeNotify extends BasePacket {

    public PacketWatcherChangeNotify(List<Integer> addWatchers, List<Integer> removeWatchers) {
        super(PacketOpcodes.WatcherChangeNotify);
        WatcherChangeNotify.Builder proto = WatcherChangeNotify.newBuilder()
            .addAllNewWatcherList(addWatchers)
            .addAllRemovedWatcherList(removeWatchers);
        this.setData(proto.build());
    }

}
