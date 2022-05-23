package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.RewardData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ItemParamOuterClass.ItemParam;
import emu.grasscutter.net.proto.TakeAchievementRewardReqOuterClass.TakeAchievementRewardReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketTakeAchievementRewardRsp;

import java.util.ArrayList;
import java.util.List;

@Opcodes(PacketOpcodes.TakeAchievementRewardReq)
public class HandlerTakeAchievementRewardReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        TakeAchievementRewardReq req = TakeAchievementRewardReq.parseFrom(payload);

        List<ItemParam> items = new ArrayList<>();
        req.getIdListList().forEach(achievementId -> {
            var achievementInfo = session.getPlayer().getAchievementManager().getAchievementInfoProperties().get(achievementId);
            var achievementExcelInfo = GameData.getAchievementDataIdMap().get(achievementId);
            if(achievementInfo != null){
                achievementInfo.setViewed(true);
                achievementInfo.save();
                RewardData reward = GameData.getRewardDataMap().get(achievementExcelInfo.getFinishRewardId());
                reward.getRewardItemList().forEach(item -> {
                    var gameItem = new GameItem(item.getItemId(), item.getItemCount());
                    session.getPlayer().getInventory().addItem(gameItem);
                    items.add(ItemParam.newBuilder().setItemId(item.getItemId()).setCount(item.getItemCount()).build());
                });
            }
        });
        session.send(new PacketTakeAchievementRewardRsp(req.getIdListList(), items));
    }
}
