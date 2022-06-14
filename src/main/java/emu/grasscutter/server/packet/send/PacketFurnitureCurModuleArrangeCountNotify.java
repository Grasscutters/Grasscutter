package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.FurnitureCurModuleArrangeCountNotifyOuterClass;
import emu.grasscutter.net.proto.Uint32PairOuterClass;

public class PacketFurnitureCurModuleArrangeCountNotify extends BasePacket {

	public PacketFurnitureCurModuleArrangeCountNotify() {
		super(PacketOpcodes.FurnitureCurModuleArrangeCountNotify);

		var proto = FurnitureCurModuleArrangeCountNotifyOuterClass.FurnitureCurModuleArrangeCountNotify.newBuilder();

		proto.addFurnitureArrangeCountList(Uint32PairOuterClass.Uint32Pair.newBuilder()
						.setKey(360101)
						.setValue(7)
				.build());

		proto.addFurnitureArrangeCountList(Uint32PairOuterClass.Uint32Pair.newBuilder()
				.setKey(360201)
				.setValue(7)
				.build());

		proto.addFurnitureArrangeCountList(Uint32PairOuterClass.Uint32Pair.newBuilder()
				.setKey(360301)
				.setValue(7)
				.build());

		proto.addFurnitureArrangeCountList(Uint32PairOuterClass.Uint32Pair.newBuilder()
				.setKey(360401)
				.setValue(2)
				.build());

		proto.addFurnitureArrangeCountList(Uint32PairOuterClass.Uint32Pair.newBuilder()
				.setKey(360402)
				.setValue(4)
				.build());

		proto.addFurnitureArrangeCountList(Uint32PairOuterClass.Uint32Pair.newBuilder()
				.setKey(364301)
				.setValue(1)
				.build());

		proto.addFurnitureArrangeCountList(Uint32PairOuterClass.Uint32Pair.newBuilder()
				.setKey(364401)
				.setValue(1)
				.build());

		proto.addFurnitureArrangeCountList(Uint32PairOuterClass.Uint32Pair.newBuilder()
				.setKey(3750102)
				.setValue(1)
				.build());

		this.setData(proto);
	}
}
