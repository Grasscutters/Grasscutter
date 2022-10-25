package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ElementType;
import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseAddAllEnergy extends ItemUseAddEnergy {
    private float energy = 0f;

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_ADD_ALL_ENERGY;
    }

    public ItemUseAddAllEnergy(String[] useParam) {
        try {
            this.energy = Float.parseFloat(useParam[0]);
        } catch (Exception ignored) {}
    }

    public float getAddEnergy(ElementType avatarElement) {
        return this.energy;
    }
}
