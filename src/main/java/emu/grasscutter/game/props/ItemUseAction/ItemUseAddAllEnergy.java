package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.*;

public class ItemUseAddAllEnergy extends ItemUseAddEnergy {
    private float energy = 0f;

    public ItemUseAddAllEnergy(String[] useParam) {
        try {
            this.energy = Float.parseFloat(useParam[0]);
        } catch (Exception ignored) {
        }
    }

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_ADD_ALL_ENERGY;
    }

    public float getAddEnergy(ElementType avatarElement) {
        return this.energy;
    }
}
