package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.BattlePassRewardTakeOptionOuterClass.BattlePassRewardTakeOption;
import emu.grasscutter.net.proto.ItemParamOuterClass.ItemParam;
import emu.grasscutter.net.proto.TakeBattlePassRewardRspOuterClass.TakeBattlePassRewardRsp;
import java.util.List;

public class PacketTakeBattlePassRewardRsp extends BasePacket {
    public PacketTakeBattlePassRewardRsp(
            List<BattlePassRewardTakeOption> takeOptionList, List<GameItem> rewardItems) {
        super(PacketOpcodes.TakeBattlePassRewardRsp);

        var proto = TakeBattlePassRewardRsp.newBuilder().addAllTakeOptionList(takeOptionList);

        if (rewardItems != null) {
            for (var item : rewardItems) {
                proto.addItemList(
                        ItemParam.newBuilder().setItemId(item.getItemId()).setCount(item.getCount()));
            }
        }

        setData(proto);
    }
}
