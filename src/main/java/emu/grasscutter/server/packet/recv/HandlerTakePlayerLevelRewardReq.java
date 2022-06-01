package emu.grasscutter.server.packet.recv;

import java.util.List;
import java.util.Set;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TakePlayerLevelRewardReqOuterClass.TakePlayerLevelRewardReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketTakePlayerLevelRewardRsp;

@Opcodes(PacketOpcodes.TakePlayerLevelRewardReq)
public class HandlerTakePlayerLevelRewardReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        synchronized (HandlerTakePlayerLevelRewardReq.class) {
            Player pl = session.getPlayer();
            TakePlayerLevelRewardReq req = TakePlayerLevelRewardReq.parseFrom(payload);
            int level = req.getLevel();
            Set<Integer> rewardedLevels = session.getPlayer().getRewardedLevels();
            if (!rewardedLevels.contains(level)) {// No duplicated reward
                int rewardId = GameData.getPlayerLevelDataMap().get(level).getRewardId();
                if (rewardId != 0) {
                    List<ItemParamData> rewardItems = GameData.getRewardDataMap().get(rewardId).getRewardItemList();
                    pl.getInventory().addItemParamDatas(rewardItems, ActionReason.PlayerUpgradeReward);
                    rewardedLevels.add(level);
                    pl.setRewardedLevels(rewardedLevels);
                    session.send(new PacketTakePlayerLevelRewardRsp(level, rewardId));
                }
            }
        }
    }
}
