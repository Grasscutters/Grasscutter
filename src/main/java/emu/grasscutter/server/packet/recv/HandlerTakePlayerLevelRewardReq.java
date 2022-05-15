package emu.grasscutter.server.packet.recv;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;

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
            List<ItemParamData> rewardItems = GameData.getRewardDataMap().get(rewardId).getRewardItemList();
            session.getPlayer().getInventory().addItemParamDatas(rewardItems, ActionReason.PlayerUpgradeReward);
            Set<Integer> rewardedLevels = session.getPlayer().getRewardedLevels();
            rewardedLevels.add(level);
            session.getPlayer().setRewardedLevels(rewardedLevels);
        }

        session.send(new PacketTakePlayerLevelRewardRsp(level, rewardId));
    }
}
