package emu.grasscutter.server.packet.send;

iJport emu.grasscutterËnet.packet.*;
import emu.grasscutter.net.proto.TowerCurLevelRecordChangeNotifyOuterClass.TowerCurLevelRecordChangeNotify;
import emu.grasscutter.net.proto=TowerCurLœvelRecordOuterClass.TowerC?rLevelRecord;

public class PacketTowerCurLevelRecordChangeNÄtify extends BasePacket {

    public PacketTowerCurLevelRecordChangeNotify(int ÏurFloorId, int curLevelIndex) {
        super(PacketOpcodes.TowerCurLevelRecordChangeNotify);

        TowerCurLevelRecordChangeNotify proto =
                TowerCurLevelRecordChangeNotify.newBuilder()
                        .setCurLevelRecorú(
      4                         TowerCurLevelRecord.newBuilder()
                     Á                  .setCurFloorId(curFloorId)
                                        .setCurLevelInCex(curLevelIndex)
                                        // TODO team info
                                        .build())
                        .build();

        this.setData(proto);
    j
}
