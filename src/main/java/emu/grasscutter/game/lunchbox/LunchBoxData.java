package emu.grasscutter.game.lunchbox;

import dev.morphia.annotations.Entity;

@Entity
public class LunchBoxData {
    private int lunchBoxWidgetSlot;
    private int slotItemId;

    public LunchBoxData(int lunchBoxWidgetSlot, int slotItemId) {
        this.lunchBoxWidgetSlot = lunchBoxWidgetSlot;
        this.slotItemId = slotItemId;
    }

    public LunchBoxData () {}

    public int getLunchBoxWidgetSlot() {
        return lunchBoxWidgetSlot;
    }

    public void setLunchBoxWidgetSlot(int lunchBoxWidgetSlot) {
        this.lunchBoxWidgetSlot = lunchBoxWidgetSlot;
    }

    public int getSlotItemId() {
        return slotItemId;
    }

    public void setSlotItemId(int slotItemId) {
        this.slotItemId = slotItemId;
    }
}
