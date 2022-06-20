package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

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
        return this.id;
    }

    public int getGadgetId() {
        return this.gadgetId;
    }

    public int getItemId() {
        return this.itemId;
    }

    public int getCd() {
        return this.cd;
    }

    public boolean isForbidGuest() {
        return this.isForbidGuest;
    }

    public boolean initDisableInteract() {
        return this.initDisableInteract;
    }

    @Override
    public void onLoad() {

    }
}
