package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;

@ResourceType(name = "GatherExcelConfigData.json")
public class GatherData extends GameResource {
    private int pointType;
    private int id;
    private int gadgetId;
    private int itemId;
    private int cd; // Probably hours
    private boolean isForbidGuest;
    private boolean initDisableInteract;

    @Override
    public int getId() {
        return this.pointType;
    }

    public int getGatherId() {
        return id;
    }

    public int getGadgetId() {
        return gadgetId;
    }

    public int getItemId() {
        return itemId;
    }

    public int getCd() {
        return cd;
    }

    public boolean isForbidGuest() {
        return isForbidGuest;
    }

    public boolean initDisableInteract() {
        return initDisableInteract;
    }

    @Override
    public void onLoad() {}
}
