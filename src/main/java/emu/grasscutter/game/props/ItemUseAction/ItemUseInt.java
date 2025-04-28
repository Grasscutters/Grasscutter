package emu.grasscutter.game.props.ItemUseAction;

import lombok.Getter;

@Getter
public abstract class ItemUseInt extends ItemUseAction {
    protected int i = 0;

    public ItemUseInt(String[] useParam) {
        try {
            this.i = Integer.parseInt(useParam[0]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) {
        }
    }
}
