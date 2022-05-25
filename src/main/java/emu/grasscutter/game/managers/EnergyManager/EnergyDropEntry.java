package emu.grasscutter.game.managers.EnergyManager;

import java.util.List;

public class EnergyDropEntry {
    private int dropId;
    private List<EnergyDropInfo> dropList;

    public int getDropId() {
        return this.dropId;
    }

    public List<EnergyDropInfo> getDropList() {
        return this.dropList;
    }
}
