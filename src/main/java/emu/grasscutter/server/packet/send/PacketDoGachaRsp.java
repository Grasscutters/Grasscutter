package emu.grasscutter.server.packet.send;

import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.game.gacha.*;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.DoGachaRspOuterClass.DoGachaRsp;
import emu.grasscutter.net.proto.GachaItemOuterClass.GachaItem;
import emu.grasscutter.net.proto.RetcodeOuterClass;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import java.util.List;

public class PacketDoGachaRsp extends BasePacket {

    public PacketDoGachaRsp(
            GachaBanner banner, List<GachaItem> list, PlayerGachaBannerInfo gachaInfo) {
        super(PacketOpcodes.DoGachaRsp);

        ItemParamData costItem = banner.getCost(1);
        ItemParamData costItem10 = banner.getCost(10);
        int gachaTimesLimit = banner.getGachaTimesLimit();
        int leftGachaTimes =
                switch (gachaTimesLimit) {
                    case Integer.MAX_VALUE -> Integer.MAX_VALUE;
                    default -> Math.max(gachaTimesLimit - gachaInfo.getTotalPulls(), 0);
                };
        DoGachaRsp.Builder rsp =
                DoGachaRsp.newBuilder()
                        .setGachaType(banner.getGachaType())
                        .setGachaScheduleId(banner.getScheduleId())
                        .setGachaTimes(list.size())
                        .setNewGachaRandom(12345)
                        .setLeftGachaTimes(leftGachaTimes)
                        .setGachaTimesLimit(gachaTimesLimit)
                        .setCostItemId(costItem.getId())
                        .setCostItemNum(costItem.getCount())
                        .setTenCostItemId(costItem10.getId())
                        .setTenCostItemNum(costItem10.getCount())
                        .addAllGachaItemList(list);

        if (banner.hasEpitomized()) {
            rsp.setWishItemId(gachaInfo.getWishItemId())
                    .setWishProgress(gachaInfo.getFailedChosenItemPulls())
                    .setWishMaxProgress(banner.getWishMaxProgress());
        }

        this.setData(rsp.build());
    }

    public PacketDoGachaRsp() {
        super(PacketOpcodes.DoGachaRsp);

        DoGachaRsp p =
                DoGachaRsp.newBuilder().setRetcode(RetcodeOuterClass.Retcode.RET_SVR_ERROR_VALUE).build();

        this.setData(p);
    }

    public PacketDoGachaRsp(Retcode retcode) {
        super(PacketOpcodes.DoGachaRsp);

        DoGachaRsp p = DoGachaRsp.newBuilder().setRetcode(retcode.getNumber()).build();

        this.setData(p);
    }
}
