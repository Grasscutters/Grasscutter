package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.WeaponAwakenRspOuterClass.WeaponAwakenRsp;

public class PacketWeaponAwakenRsp extends BasePacket {

    public PacketWeaponAwakenRsp(
            Avatar avatar, GameItem item, GameItem feedWeapon, int oldRefineLevel) {
        super(PacketOpcodes.WeaponAwakenRsp);

        WeaponAwakenRsp.Builder proto =
                WeaponAwakenRsp.newBuilder()
                        .setTargetWeaponGuid(item.getGuid())
                        .setTargetWeaponAwakenLevel(item.getRefinement());

        for (int affixId : item.getAffixes()) {
            proto.putOldAffixLevelMap(affixId, oldRefineLevel);
            proto.putCurAffixLevelMap(affixId, item.getRefinement());
        }

        if (avatar != null) {
            proto.setAvatarGuid(avatar.getGuid());
        }

        this.setData(proto);
    }
}
