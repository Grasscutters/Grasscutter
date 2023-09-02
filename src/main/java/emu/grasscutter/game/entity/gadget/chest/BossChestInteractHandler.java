package emu.grasscutter.game.entity.gadget.chest;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.game.entity.gadget.GadgetChest;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.server.packet.send.PacketGadgetAutoPickDropInfoNotify;
import java.util.*;

public class BossChestInteractHandler implements ChestInteractHandler {
    @Override
    public boolean isTwoStep() {
        return true;
    }

    @Override
    public boolean onInteract(GadgetChest chest, Player player) {
        return this.onInteract(chest, player, false);
    }

    public boolean onInteract(GadgetChest chest, Player player, boolean useCondensedResin) {
        var blossomRewards =
                player
                        .getScene()
                        .getBlossomManager()
                        .onReward(player, chest.getGadget(), useCondensedResin);
        if (blossomRewards != null) {
            player.getInventory().addItems(blossomRewards, ActionReason.OpenWorldBossChest);
            player.sendPacket(new PacketGadgetAutoPickDropInfoNotify(blossomRewards));
            return true;
        }

        var worldDataManager = chest.getGadget().getScene().getWorld().getServer().getWorldDataSystem();
        var monster =
                chest
                        .getGadget()
                        .getMetaGadget()
                        .group
                        .monsters
                        .get(chest.getGadget().getMetaGadget().boss_chest.monster_config_id);
        var reward = worldDataManager.getRewardByBossId(monster.monster_id);

        if (reward == null) {
            var dungeonManager = player.getScene().getDungeonManager();

            if (dungeonManager != null) {
                return dungeonManager.getStatueDrops(
                        player, useCondensedResin, chest.getGadget().getGroupId());
            }
            Grasscutter.getLogger()
                    .warn("Could not found the reward of boss monster {}", monster.monster_id);
            return false;
        }
        List<GameItem> rewards = new ArrayList<>();
        for (ItemParamData param : reward.getPreviewItems()) {
            rewards.add(new GameItem(param.getId(), Math.max(param.getCount(), 1)));
        }

        player.getInventory().addItems(rewards, ActionReason.OpenWorldBossChest);
        player.sendPacket(new PacketGadgetAutoPickDropInfoNotify(rewards));

        return true;
    }
}
