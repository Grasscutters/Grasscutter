package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.*;

public class ItemUseAddElemEnergy extends ItemUseAddEnergy {
    private ElementType element = ElementType.None;
    private float elemEnergy = 0f;
    private float otherEnergy = 0f;

    public ItemUseAddElemEnergy(String[] useParam) {
        try {
            this.element = ElementType.getTypeByValue(Integer.parseInt(useParam[0]));
        } catch (Exception ignored) {
        }
        try {
            this.elemEnergy = Float.parseFloat(useParam[1]);
        } catch (Exception ignored) {
        }
        try {
            this.otherEnergy = Float.parseFloat(useParam[2]);
        } catch (Exception ignored) {
        }
    }

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_ADD_ELEM_ENERGY;
    }

    public float getAddEnergy(ElementType avatarElement) {
        return (avatarElement == this.element) ? this.elemEnergy : this.otherEnergy;
    }
}
