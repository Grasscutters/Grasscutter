package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.enums.QuestContent;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.VehicleInteractRspOuterClass.VehicleInteractRsp;
import emu.grasscutter.net.proto.VehicleInteractTypeOuterClass.VehicleInteractType;
import emu.grasscutter.net.proto.VehicleMemberOuterClass.VehicleMember;

public class PacketVehicleInteractRsp extends BasePacket {

    public PacketVehicleInteractRsp(Player player, int entityId, VehicleInteractType interactType) {
        super(PacketOpcodes.VehicleInteractRsp);
        VehicleInteractRsp.Builder proto = VehicleInteractRsp.newBuilder();

        GameEntity vehicle = player.getScene().getEntityById(entityId);

        if (vehicle instanceof EntityVehicle) {
            proto.setEntityId(vehicle.getId());

            VehicleMember vehicleMember =
                    VehicleMember.newBuilder()
                            .setUid(player.getUid())
                            .setAvatarGuid(player.getTeamManager().getCurrentCharacterGuid())
                            .build();

            proto.setInteractType(interactType);
            proto.setMember(vehicleMember);

            switch (interactType) {
                case VEHICLE_INTERACT_TYPE_IN -> {
                    ((EntityVehicle) vehicle).getVehicleMembers().add(vehicleMember);
                    player
                            .getQuestManager()
                            .queueEvent(
                                    QuestContent.QUEST_CONTENT_ENTER_VEHICLE,
                                    ((EntityVehicle) vehicle).getGadgetId());
                }
                case VEHICLE_INTERACT_TYPE_OUT -> {
                    ((EntityVehicle) vehicle).getVehicleMembers().remove(vehicleMember);
                }
                default -> {}
            }
        }
        this.setData(proto.build());
    }

    public PacketVehicleInteractRsp(
            EntityVehicle vehicle, VehicleMember vehicleMember, VehicleInteractType interactType) {
        super(PacketOpcodes.VehicleInteractRsp);
        VehicleInteractRsp.Builder proto = VehicleInteractRsp.newBuilder();

        if (vehicle != null) {
            proto.setEntityId(vehicle.getId());
            proto.setInteractType(interactType);
            proto.setMember(vehicleMember);

            switch (interactType) {
                case VEHICLE_INTERACT_TYPE_IN -> {
                    vehicle.getVehicleMembers().add(vehicleMember);
                }
                case VEHICLE_INTERACT_TYPE_OUT -> {
                    vehicle.getVehicleMembers().remove(vehicleMember);
                }
                default -> {}
            }
        }
        this.setData(proto.build());
    }
}
