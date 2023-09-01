package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GadgetStateNotifyOuterClass.GadgetStateNotify;

public class PacketGadgetStateNotify extends BasePacket {

    public PacketGadgetStateNotify(EntityGadget gadget, int newState) {
        super(PacketOpcodes.GadgetStateNotify);

        GadgetStateNotify proto =
                GadgetStateNotify.newBuilder()
                        .setGadgetEntityId(gadget.getId())
                        .setGadgetState(newState)
                        .setIsEnableInteract(gadget.isInteractEnabled())
                        .build();

        this.setData(proto);
    }
}
