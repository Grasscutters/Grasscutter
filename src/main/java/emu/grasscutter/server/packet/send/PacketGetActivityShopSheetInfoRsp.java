package emu.grasscutter.server.packet.send;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.activity.ActivityConfigItem;
import emu.grasscutter.game.activity.ActivityManager;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ActivityShopSheetInfoOuterClass.ActivityShopSheetInfo;
import emu.grasscutter.net.proto.GetActivityShopSheetInfoRspOuterClass.GetActivityShopSheetInfoRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass;

import java.util.ArrayList;
import java.util.List;

public class PacketGetActivityShopSheetInfoRsp extends BasePacket {


    public PacketGetActivityShopSheetInfoRsp(int shopType) {
        super(PacketOpcodes.GetActivityShopSheetInfoRsp);

        var sheetInfo = GameData.getActivityShopDataMap().get(shopType);
        ActivityConfigItem activityConfigItem = null;

        if (sheetInfo != null) {
            activityConfigItem = ActivityManager.getScheduleActivityConfigMap().get(sheetInfo.getScheduleId());
        }

        if (sheetInfo == null || activityConfigItem == null) {
            setData(GetActivityShopSheetInfoRsp.newBuilder()
                .setShopType(shopType)
                .setRetcode(RetcodeOuterClass.Retcode.RET_SHOP_NOT_OPEN_VALUE)
                .build());
            return;
        }

        List<ActivityShopSheetInfo> sheetInfos = new ArrayList<>(sheetInfo.getSheetList().size());
        for (int id : sheetInfo.getSheetList()) {
            sheetInfos.add(ActivityShopSheetInfo.newBuilder()
                .setSheetId(id)
                .setBeginTime((int) activityConfigItem.getBeginTime().getTime())
                .setEndTime((int) activityConfigItem.getEndTime().getTime())
                .build());
        }

        setData(GetActivityShopSheetInfoRsp.newBuilder()
            .setShopType(shopType)
            .addAllSheetInfoList(sheetInfos)
            .build());
    }
}
