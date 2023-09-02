package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.CookRecipeDataOuterClass.CookRecipeData;
import emu.grasscutter.net.proto.ItemParamOuterClass.ItemParam;
import emu.grasscutter.net.proto.PlayerCookRspOuterClass.PlayerCookRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import java.util.List;

public class PacketPlayerCookRsp extends BasePacket {
    public PacketPlayerCookRsp(Retcode retcode) {
        super(PacketOpcodes.PlayerCookRsp);

        PlayerCookRsp proto = PlayerCookRsp.newBuilder().setRetcode(retcode.getNumber()).build();

        this.setData(proto);
    }

    public PacketPlayerCookRsp(
            List<GameItem> output, int quality, int count, int recipeId, int proficiency) {
        super(PacketOpcodes.PlayerCookRsp);

        PlayerCookRsp.Builder proto =
                PlayerCookRsp.newBuilder()
                        .setRecipeData(
                                CookRecipeData.newBuilder().setRecipeId(recipeId).setProficiency(proficiency))
                        .setQteQuality(quality)
                        .setCookCount(count);

        for (var item : output) {
            proto.addItemList(
                    ItemParam.newBuilder().setItemId(item.getItemId()).setCount(item.getCount()));
        }

        this.setData(proto);
    }
}
