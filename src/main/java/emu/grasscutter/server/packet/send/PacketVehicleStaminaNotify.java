package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.VehicleStaminaNotifyOuterClass.VehicleStaminaNotify;

public class PacketVehicleStaminaNotify extends BasePacket {

    public PacketVehicleStaminaNotify(int vehicleId, float newStamina) {
        super(PacketOpcodes.VehicleStaminaNotify);
        VehicleStaminaNotify.Builder proto = VehicleStaminaNotify.newBuilder();

        proto.setEntityId(vehicleId);
        proto.setCurStamina(newStamina);

        this.setData(proto.build());
    }
}
