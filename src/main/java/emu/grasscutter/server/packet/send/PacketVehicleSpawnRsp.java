package emu.grasscutter.server.packet.send;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.entity.EntityVehicle;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.entity.GameEntity;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;

import emu.grasscutter.net.proto.VehicleMemberOuterClass.VehicleMember;
import emu.grasscutter.net.proto.VehicleSpawnRspOuterClass.VehicleSpawnRsp;

import emu.grasscutter.utils.Position;

import java.util.List;


import static emu.grasscutter.net.proto.VehicleInteractTypeOuterClass.VehicleInteractType.VEHICLE_INTERACT_OUT;

public class PacketVehicleSpawnRsp extends BasePacket {

	public PacketVehicleSpawnRsp(Player player, int vehicleId, int pointId, Position pos, Position rot) {
		super(PacketOpcodes.VehicleSpawnRsp);
		VehicleSpawnRsp.Builder proto = VehicleSpawnRsp.newBuilder();

		// Eject vehicle members and Kill previous vehicles if there are any
		List<GameEntity> previousVehicles = player.getScene().getEntities().values().stream()
				.filter(entity -> entity instanceof EntityVehicle
						&& ((EntityVehicle) entity).getGadgetId() == vehicleId
						&& ((EntityVehicle) entity).getOwner().equals(player))
				.toList();

		previousVehicles.stream().forEach(entity -> {
			List<VehicleMember> vehicleMembers = ((EntityVehicle) entity).getVehicleMembers().stream().toList();

			vehicleMembers.stream().forEach(vehicleMember -> {
				player.getScene().broadcastPacket(new PacketVehicleInteractRsp(((EntityVehicle) entity), vehicleMember, VEHICLE_INTERACT_OUT));
			});

			player.getScene().killEntity(entity, 0);
		});

		EntityVehicle vehicle = new EntityVehicle(player.getScene(), player, vehicleId, pointId, pos, rot);

		switch (vehicleId) {
			// TODO: Not hardcode this. Waverider (skiff)
			case 45001001,45001002 -> {
				vehicle.addFightProperty(FightProperty.FIGHT_PROP_BASE_HP, 10000);
				vehicle.addFightProperty(FightProperty.FIGHT_PROP_BASE_ATTACK, 100);
				vehicle.addFightProperty(FightProperty.FIGHT_PROP_CUR_ATTACK, 100);
				vehicle.addFightProperty(FightProperty.FIGHT_PROP_CUR_HP, 10000);
				vehicle.addFightProperty(FightProperty.FIGHT_PROP_CUR_DEFENSE, 0);
				vehicle.addFightProperty(FightProperty.FIGHT_PROP_CUR_SPEED, 0);
				vehicle.addFightProperty(FightProperty.FIGHT_PROP_CHARGE_EFFICIENCY, 0);
				vehicle.addFightProperty(FightProperty.FIGHT_PROP_MAX_HP, 10000);
			}
			default -> {}
		}

		player.getScene().addEntity(vehicle);

		proto.setVehicleId(vehicleId);
		proto.setEntityId(vehicle.getId());

		this.setData(proto.build());
	}
}
