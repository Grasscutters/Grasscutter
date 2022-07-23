package emu.grasscutter.game.entity.gadget.chest;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.game.entity.gadget.GadgetChest;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.server.packet.send.PacketGadgetAutoPickDropInfoNotify;

import java.util.ArrayList;
import java.util.List;

public class BossChestInteractHandler implements ChestInteractHandler{
    @Override
    public boolean isTwoStep() {
        return true;
    }

    @Override
    public boolean onInteract(GadgetChest chest, Player player) {
        var worldDataManager = chest.getGadget().getScene().getWorld().getServer().getWorldDataSystem();
        var monster = chest.getGadget().getMetaGadget().group.monsters.get(chest.getGadget().getMetaGadget().boss_chest.monster_config_id);
        var reward = worldDataManager.getRewardByBossId(monster.monster_id);

        if (reward == null) {
            Grasscutter.getLogger().warn("Could not found the reward of boss monster {}", monster.monster_id);
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
