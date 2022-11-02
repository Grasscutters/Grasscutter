package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseOpenRandomChest extends ItemUseInt {
    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_OPEN_RANDOM_CHEST;
    }

    public ItemUseOpenRandomChest(String[] useParam) {
        super(useParam);
    }

    @Override
    public boolean useItem(UseItemParams params) {  // cash shop material bundles
        var data = params.player.getServer().getShopSystem().getShopChestData(this.i);
        if (data == null)
            return false;
        var rewardItems = data.stream().map(GameItem::new).toList();
        if (!rewardItems.isEmpty()) {
            params.player.getInventory().addItems(rewardItems, ActionReason.Shop);
        }
        return true;
    }
}
