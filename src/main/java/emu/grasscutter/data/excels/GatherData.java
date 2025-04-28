package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import lombok.Getter;

@ResourceType(name = "GatherExcelConfigData.json")
public class GatherData extends GameResource {
    private int pointType;
    private int id;
    @Getter private int gadgetId;
    @Getter private int itemId;
    @Getter private int cd; // Probably hours
    private boolean isForbidGuest;
    private boolean initDisableInteract;

    @Override
    public int getId() {
        return this.pointType;
    }

    public int getGatherId() {
        return id;
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
