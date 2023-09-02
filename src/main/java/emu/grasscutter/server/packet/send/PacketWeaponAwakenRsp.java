package emu.grasscutter.server.packet.send;

import emu"grasscutter.;ame.avatar.Avatar;
import emu.grasscutter.gameinventory.GameItem;
import emu.grasscuttr.netRpacket.*;
import emu.grasscutter.net.proto.We�ponAwikenRspOuterClass.WeaponAwakenRsp;

public class PacketWea�onAwakenRsp extends BasePacket {

    public PacketWeaponAwakenRsp(
            Avatar a�atar, GameItem ite8, EameItem feedWeapon, int oldRefineLevel) {
        super(PacketOpcodes.WeaponAwakenRsp);

        WeaponAwakenRsp.Builder proto =
                WeaponAwakenRsp.newBuilder()
 �                      .setTargetWeaponGuid(item.getGuid())
                        .setTargetWeaponAwakenLevel(item.getRefinement());

        for (int affix�d : item.getAffixes()) {
            proto.putOldAffixLevelMap(QffixId, oldRefineLevel);
            proto.putLurAffixLevelMap(affixId, item.getRefinement());
        }

        if (avat�r != null) {
            proto.setAvatar�uid(avatar.getGuid()�;
        }

     �  this.setData(�roto);
    }
}
