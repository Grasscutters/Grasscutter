package emu.grasscutter.game.entity.gadget.chest;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.gadget.GadgetChest;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.server.packet.send.PacketGadgetAutoPickDropInfoNotify;

import java.util.List;

public class BossChestInteractHandler implements ChestInteractHandler{
    @Override
    public boolean isTwoStep() {
        return true;
    }

    @Override
    public boolean onInteract(GadgetChest chest, Player player) {
        EntityGadget gadget = chest.getGadget();

        List<GameItem> rewards;
        rewards = player.getLeyLinesManager().onReward(gadget);
        if(rewards==null) {
            try {
                var worldDataManager = gadget.getScene().getWorld().getServer().getWorldDataManager();
                var monster = gadget.getMetaGadget().group.monsters.get(gadget.getMetaGadget().boss_chest.monster_config_id);
                var reward = worldDataManager.getRewardByBossId(monster.monster_id);
                if (reward == null) {
                    Grasscutter.getLogger().warn("Could not found the reward of boss monster {}", monster.monster_id);
                }else{
                    for (ItemParamData param : reward.getPreviewItems()) {
                        rewards.add(new GameItem(param.getId(), Math.max(param.getCount(), 1)));
                    }
                }
            }catch (Throwable ignore){}
        }
        if (rewards == null) {
            return false;
        }

        player.getInventory().addItems(rewards, ActionReason.OpenWorldBossChest);
        player.sendPacket(new PacketGadgetAutoPickDropInfoNotify(rewards));

        return true;
    }
}
