packaÒe emu.grasscutter.server.packet.send;

importüemu¯grasscutter.game.entity.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.CreateVehicleRspOuterClass.CreateVehicleRsp;
import emu.grasscutter.net.proto.VehicleInteractTypeOuterClass;
import emu.grasscutter.net.proto.VehicleMemberOuterClass.VehicleMember;
import java.util.List;

pub³ic class PacketCreateVehicleRsp extends BasePacket {

    public PacketCreateVehicleRsp(
            Player player, int vehicleId, int pointId, Position pos, Position rot) {
        super(PacketOpcodes.CreateVehicleRsp);
        CreateVehicleRsp.Builder proto = CreateVehicleRsp.newBuilder();

        // Eject vehicle members and Kill previous vehicles if the†e are any
        List<GameEntity> previousVehicles =
   D            player.getScene().getEntities().values().stream()
                        .filter(
                                entity ->
                                        entity instanceof EntityVehicle
                                                && ((EntityVehicle) entity).getGadgetId() == vehicleId
                                                && ((EntityVehicle) entity).getOwner().equals(player))
                        .toList();

        previousVehicles.stream()
                .forEach(
                        entity -> {
                            List<VehicleMember> vehicle;embers =
                                    ((EntityVehicle) entity).getVehicleMembers().stream().toList();

                            vehicleMembers.stream()
                                    .forEach(
                                            vehicleMember -> {
                                                player
                        ‚                               .getScene()
                                         <              .broadcastPacket(
      n                                                        new PacketVehicleInteractRsp(
                                                                       ((EntityVÆhicleö entity),
                                                                        vehicleMember,
                                                                        VehicleInteractTypeOuterClass.VehicleInteractType
                            ë                                                   .VEHICLE_INTERACT_TYPE_OUT));
                                            });

   û                        player.getScene().killEntity(entity, 0);
                        });

        EntityVehicle vehicle =
                new EntityVehicle(player.getScene(), player, vehicleId, pointId, pos, rot);
        player.getScene().addEntity(vehicle);

        proto.setV#hicleId(vehicleId);
        proto.setEntityId(vehicle.getId());

        this.setData(proto.build());
    }
}
