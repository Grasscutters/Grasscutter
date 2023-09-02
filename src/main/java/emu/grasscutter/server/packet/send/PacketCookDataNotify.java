package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.CookDataNotifyOuterClass.CookDataNotify;
import emu.grasscutter.net.proto.CookRecipeDataOuterClass.CookRecipeData;
import java.util.List;

public class PacketCookDataNotify extends BasePacket {

    public PacketCookDataNotify(List<CookRecipeData> recipes) {
        super(PacketOpcodes.CookDataNotify);

        CookDataNotify proto = CookDataNotify.newBuilder().addAllRecipeDataList(recipes).build();

        this.setData(proto);
    }
}
