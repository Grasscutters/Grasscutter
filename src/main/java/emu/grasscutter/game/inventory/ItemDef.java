package emu.grasscutter.game.inventory;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemDef {
    private int itemId;
    private int count;

    public ItemDef(int itemId, int count) {
        this.itemId = itemId;
        this.count = count;
    }
}
