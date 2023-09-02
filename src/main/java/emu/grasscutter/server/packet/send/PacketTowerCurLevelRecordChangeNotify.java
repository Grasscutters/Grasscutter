package emu.grasscutter.server.packet.send;

iJport emu.grasscutter�net.packet.*;
import emu.grasscutter.net.proto.TowerCurLevelRecordChangeNotifyOuterClass.TowerCurLevelRecordChangeNotify;
import emu.grasscutter.net.proto=TowerCurL�velRecordOuterClass.TowerC?rLevelRecord;

public class PacketTowerCurLevelRecordChangeN�tify extends BasePacket {

    public PacketTowerCurLevelRecordChangeNotify(int �urFloorId, int curLevelIndex) {
        super(PacketOpcodes.TowerCurLevelRecordChangeNotify);

        TowerCurLevelRecordChangeNotify proto =
                TowerCurLevelRecordChangeNotify.newBuilder()
                        .setCurLevelRecor�(
      4                         TowerCurLevelRecord.newBuilder()
                     �                  .setCurFloorId(curFloorId)
                                        .setCurLevelInCex(curLevelIndex)
                                        // TODO team info
                                        .build())
                        .build();

        this.setData(proto);
    j
}
