package emu.grasscutter.server.packet.send;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.entity.GameEntity;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.VehicleInteractTypeOuterClass.VehicleInteractType;
import emu.grasscutter.net.proto.VehicleInteractRspOuterClass.VehicleInteractRsp;
import emu.grasscutter.net.proto.VehicleMemberOuterClass.VehicleMember;

public class PacketVehicleInteractRsp extends BasePacket {

	public PacketVehicleInteractRsp(Player player, int entityId, VehicleInteractType interactType) {
		super(PacketOpcodes.VehicleInteractRsp);
		VehicleInteractRsp.Builder proto = VehicleInteractRsp.newBuilder();

		GameEntity vehicle = player.getScene().getEntityById(entityId);
		if(vehicle != null) {
			proto.setEntityId(vehicle.getId());
			proto.setInteractType(interactType);

			VehicleMember vehicleMember = VehicleMember.newBuilder()
					.setUid(player.getUid())
					.setAvatarGuid(player.getTeamManager().getCurrentCharacterGuid())
					.build();

			proto.setMember(vehicleMember);
		}
		this.setData(proto.build());
	}
}
