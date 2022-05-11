package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.VehicleStaminaNotifyOuterClass.VehicleStaminaNotify;

public class PacketVehicleStaminaNotify extends BasePacket {

	public PacketVehicleStaminaNotify(GameEntity entity, int newStamina) {
		super(PacketOpcodes.VehicleStaminaNotify);
		VehicleStaminaNotify.Builder proto = VehicleStaminaNotify.newBuilder();

		proto.setEntityId(entity.getId());
		proto.setCurStamina(newStamina);

		this.setData(proto.build());
	}
}
