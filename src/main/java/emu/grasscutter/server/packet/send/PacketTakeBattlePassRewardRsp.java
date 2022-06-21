package emu.grasscutter.server.packet.send;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.BattlePassRewardExcelConfigData;
import emu.grasscutter.data.excels.RewardData;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.BattlePassRewardTakeOptionOuterClass;
import emu.grasscutter.net.proto.ItemParamOuterClass;
import emu.grasscutter.net.proto.TakeBattlePassRewardRspOuterClass;
import emu.grasscutter.server.game.GameSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PacketTakeBattlePassRewardRsp extends BasePacket {
    public PacketTakeBattlePassRewardRsp(List<BattlePassRewardTakeOptionOuterClass.BattlePassRewardTakeOption> takeOptionList , GameSession session) {
        super(PacketOpcodes.TakeBattlePassRewardRsp);

        var proto
                = TakeBattlePassRewardRspOuterClass.TakeBattlePassRewardRsp.newBuilder();

        Map<Integer , BattlePassRewardExcelConfigData> excelConfigDataMap = GameData.getBattlePassRewardExcelConfigDataMap();
        Map<Integer , RewardData> rewardDataMap = GameData.getRewardDataMap();

        List<Integer> rewardItemList = new ArrayList<>();

        for (var takeOption : takeOptionList) {
            for (int level = session.getPlayer().getBattlePassManager().getAwardTakenLevel() + 1 ; level <= takeOption.getTag().getLevel() ; level++){
                rewardItemList.addAll(excelConfigDataMap.get(level).getFreeRewardIdList());
                rewardItemList.addAll(excelConfigDataMap.get(level).getPaidRewardIdList());
            }

            for (var rewardItemId : rewardItemList) {
                var rewardData = rewardDataMap.get(rewardItemId);
                if (rewardData == null) continue;
                rewardData.getRewardItemList().forEach(i ->
                        proto.addItemList(ItemParamOuterClass.ItemParam.newBuilder().setItemId(i.getId()).setCount(i.getCount()).build()));
            }

        }

        proto.addAllTakeOptionList(takeOptionList).build();

        setData(proto);
    }
}
