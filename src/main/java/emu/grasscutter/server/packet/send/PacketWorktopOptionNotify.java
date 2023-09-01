package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.gadget.GadgetWorktop;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.WorktopOptionNotifyOuterClass.WorktopOptionNotify;

public class PacketWorktopOptionNotify extends BasePacket {

    public PacketWorktopOptionNotify(EntityGadget gadget) {
        super(PacketOpcodes.WorktopOptionNotify);

        WorktopOptionNotify.Builder proto =
                WorktopOptionNotify.newBuilder().setGadgetEntityId(gadget.getId());

        if (gadget.getContent() instanceof GadgetWorktop worktop) {
            proto.addAllOptionList(worktop.getWorktopOptions());
        }

        this.setData(proto);
    }
}
