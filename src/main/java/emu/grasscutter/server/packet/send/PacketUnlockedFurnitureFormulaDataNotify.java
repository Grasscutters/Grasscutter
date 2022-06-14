package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.UnlockedFurnitureFormulaDataNotifyOuterClass;

public class PacketUnlockedFurnitureFormulaDataNotify extends BasePacket {

	public PacketUnlockedFurnitureFormulaDataNotify() {
		super(PacketOpcodes.UnlockedFurnitureFormulaDataNotify);

		var proto = UnlockedFurnitureFormulaDataNotifyOuterClass.UnlockedFurnitureFormulaDataNotify.newBuilder();

		proto.addFurnitureIdList(361207);
		proto.addFurnitureIdList(362202);
		proto.addFurnitureIdList(362304);
		proto.addFurnitureIdList(363102);
		proto.addFurnitureIdList(363103);
		proto.addFurnitureIdList(363203);
		proto.addFurnitureIdList(370201);
		proto.addFurnitureIdList(370302);

		proto.setIsAll(true);

		this.setData(proto);
	}
}
