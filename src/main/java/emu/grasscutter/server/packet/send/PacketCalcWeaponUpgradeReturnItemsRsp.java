package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.CalcWeaponUpgradeReturnItemsRspOuterClass.CalcWeaponUpgradeReturnItemsRsp;
import emu.grasscutter.net.proto.ItemParamOuterClass.ItemParam;
import emu.grasscutter.net.proto.RetcodeOuterClass;
import java.util.List;

public class PacketCalcWeaponUpgradeReturnItemsRsp extends BasePacket {

    public PacketCalcWeaponUpgradeReturnItemsRsp(long itemGuid, List<ItemParam> returnItems) {
        super(PacketOpcodes.CalcWeaponUpgradeReturnItemsRsp);

        CalcWeaponUpgradeReturnItemsRsp proto =
                CalcWeaponUpgradeReturnItemsRsp.newBuilder()
                        .setTargetWeaponGuid(itemGuid)
                        .addAllItemParamList(returnItems)
                        .build();

        this.setData(proto);
    }

    public PacketCalcWeaponUpgradeReturnItemsRsp() {
        super(PacketOpcodes.CalcWeaponUpgradeReturnItemsRsp);

        CalcWeaponUpgradeReturnItemsRsp proto =
                CalcWeaponUpgradeReturnItemsRsp.newBuilder()
                        .setRetcode(RetcodeOuterClass.Retcode.RET_SVR_ERROR_VALUE)
                        .build();

        this.setData(proto);
    }
}
