package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.CookDataNotifyOuterClass.CookDataNotify;
import emu.grasscutter.net.proto.CookRecipeDataOuterClass.CookRecipeData;

import java.util.List;

public class PacketCookDataNotify extends BasePacket {

    public PacketCookDataNotify(List<CookRecipeData> recipies) {
        super(PacketOpcodes.CookDataNotify);

        CookDataNotify proto = CookDataNotify.newBuilder()
                .addAllRecipeDataList(recipies)
                .build();

        this.setData(proto);
    }
}
