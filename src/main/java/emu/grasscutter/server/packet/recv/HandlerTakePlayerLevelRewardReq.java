package emu.grasscutter.server.packet.recv;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.RewardItemData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TakePlayerLevelRewardReqOuterClass.TakePlayerLevelRewardReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketItemAddHintNotify;
import emu.grasscutter.server.packet.send.PacketTakePlayerLevelRewardRsp;

@Opcodes(PacketOpcodes.TakePlayerLevelRewardReq)
public class HandlerTakePlayerLevelRewardReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        TakePlayerLevelRewardReq req = TakePlayerLevelRewardReq.parseFrom(payload);

        int level = req.getLevel();
        int rewardId = GameData.getPlayerLevelDataMap().get(level).getRewardId();

        if (rewardId != 0) {
            List<RewardItemData> rewardItems = GameData.getRewardDataMap().get(rewardId).getRewardItemList();
            List<GameItem> items = new LinkedList<>();
            for (RewardItemData rewardItem : rewardItems) {
                if (rewardItem != null) {
                    if (rewardItem.getItemId() != 0) {
                        items.add(new GameItem(rewardItem.getItemId(), rewardItem.getItemCount()));
                    }
                }
            }
            session.getPlayer().getInventory().addItems(items);
            session.getPlayer().sendPacket(new PacketItemAddHintNotify(items, ActionReason.PlayerUpgradeReward));
            Set<Integer> rewardedLevels = session.getPlayer().getRewardedLevels();
            rewardedLevels.add(level);
            session.getPlayer().setRewardedLevels(rewardedLevels);
        }

        session.send(new PacketTakePlayerLevelRewardRsp(level, rewardId));
    }
}
