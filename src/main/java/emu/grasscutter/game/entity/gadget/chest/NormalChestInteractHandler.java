package emu.grasscutter.game.entity.gadget.chest;

import emu.grasscutter.game.entity.gadget.GadgetChest;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.loot.LootContext;
import emu.grasscutter.loot.LootTable;

import java.util.List;

public class NormalChestInteractHandler implements ChestInteractHandler {
    private final LootTable chestReward;

    public NormalChestInteractHandler(LootTable rewardData){
        this.chestReward = rewardData;
    }

    @Override
    public boolean isTwoStep() {
        return false;
    }

    @Override
    public boolean onInteract(GadgetChest chest, Player player) {
        LootContext ctx = new LootContext();
        ctx.player = player;
        List<GameItem> items = chestReward.loot(ctx);

        items.forEach(e -> chest.getGadget().getScene().addItemEntity(e.getItemId(), e.getCount(), chest.getGadget()));
        return true;
    }
}
