package emu.grasscutter.game.props.ItemUseAction;

import lombok.Getter;

public abstract class ItemUseInt extends ItemUseAction {
    @Getter protected int i = 0;

    public ItemUseInt(String[] useParam) {
        try {
            this.i = Integer.parseInt(useParam[0]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) {
        }
    }
}
