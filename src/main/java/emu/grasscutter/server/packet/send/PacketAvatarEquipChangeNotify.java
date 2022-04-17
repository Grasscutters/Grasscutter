package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.game.inventory.EquipType;
import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarEquipChangeNotifyOuterClass.AvatarEquipChangeNotify;

public class PacketAvatarEquipChangeNotify extends GenshinPacket {
	
	public PacketAvatarEquipChangeNotify(GenshinAvatar avatar, GenshinItem item) {
		super(PacketOpcodes.AvatarEquipChangeNotify);

		AvatarEquipChangeNotify.Builder proto = AvatarEquipChangeNotify.newBuilder()
				.setAvatarGuid(avatar.getGuid())
				.setEquipType(item.getEquipSlot())
				.setItemId(item.getItemId())
				.setEquipGuid(item.getGuid());
		
		if (item.getItemData().getEquipType() == EquipType.EQUIP_WEAPON) {
			proto.setWeapon(item.createSceneWeaponInfo());
		} else {
			proto.setReliquary(item.createSceneReliquaryInfo());
		}

		this.setData(proto);
	}
	
	public PacketAvatarEquipChangeNotify(GenshinAvatar avatar, EquipType slot) {
		super(PacketOpcodes.AvatarEquipChangeNotify);

		AvatarEquipChangeNotify.Builder proto = AvatarEquipChangeNotify.newBuilder()
				.setAvatarGuid(avatar.getGuid())
				.setEquipType(slot.getValue());

		this.setData(proto);
	}
}
