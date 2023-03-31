package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TowerCurLevelRecordChangeNotifyOuterClass.TowerCurLevelRecordChangeNotify;
import emu.grasscutter.net.proto.TowerCurLevelRecordOuterClass.TowerCurLevelRecord;

public class PacketTowerCurLevelRecordChangeNotify extends BasePacket {

    public PacketTowerCurLevelRecordChangeNotify(int curFloorId, int curLevelIndex) {
        super(PacketOpcodes.TowerCurLevelRecordChangeNotify);

        TowerCurLevelRecordChangeNotify proto = TowerCurLevelRecordChangeNotify.newBuilder()
            .setCurLevelRecord(TowerCurLevelRecord.newBuilder()
                .setCurFloorId(curFloorId)
                .setCurLevelIndex(curLevelIndex)
                // TODO team info
                .build())
            .build();

        this.setData(proto);
    }
}
