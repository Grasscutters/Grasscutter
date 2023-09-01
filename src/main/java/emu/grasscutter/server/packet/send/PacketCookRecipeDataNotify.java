package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.CookRecipeDataNotifyOuterClass.CookRecipeDataNotify;
import emu.grasscutter.net.proto.CookRecipeDataOuterClass.CookRecipeData;

public class PacketCookRecipeDataNotify extends BasePacket {
    public PacketCookRecipeDataNotify(CookRecipeData recipe) {
        super(PacketOpcodes.CookRecipeDataNotify);

        CookRecipeDataNotify proto = CookRecipeDataNotify.newBuilder().setRecipeData(recipe).build();

        this.setData(proto);
    }

    public PacketCookRecipeDataNotify(int recipeId) {
        super(PacketOpcodes.CookRecipeDataNotify);

        CookRecipeDataNotify proto =
                CookRecipeDataNotify.newBuilder()
                        .setRecipeData(CookRecipeData.newBuilder().setRecipeId(recipeId))
                        .build();

        this.setData(proto);
    }
}
