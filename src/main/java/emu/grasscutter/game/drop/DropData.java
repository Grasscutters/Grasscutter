package emu.grasscutter.game.drop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Deprecated
public class DropData {
    private int minWeight;
    private int maxWeight;
    @Setter private int itemId;
    private int minCount;
    private int maxCount;
    private boolean share = false;
    @Setter private boolean give = false;

    public void setIsShare(boolean share) {
        this.share = share;
    }
}
