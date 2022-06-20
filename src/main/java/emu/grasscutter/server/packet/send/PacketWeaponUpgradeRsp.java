package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ItemParamOuterClass.ItemParam;
import emu.grasscutter.net.proto.WeaponUpgradeRspOuterClass.WeaponUpgradeRsp;

import java.util.List;

public class PacketWeaponUpgradeRsp extends BasePacket {

    public PacketWeaponUpgradeRsp(GameItem item, int oldLevel, List<ItemParam> leftoverOres) {
        super(PacketOpcodes.WeaponUpgradeRsp);

        WeaponUpgradeRsp proto = WeaponUpgradeRsp.newBuilder()
            .setTargetWeaponGuid(item.getGuid())
            .setCurLevel(item.getLevel())
            .setOldLevel(oldLevel)
            .addAllItemParamList(leftoverOres)
            .build();

        this.setData(proto);
    }
}
