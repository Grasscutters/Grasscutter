package emu.grasscutter.game.managers.EnergyManager;

import java.util.List;

public class EnergyDropEntry {
    private int monsterId;
    private List<EnergyDropInfo> energyDrops;

    public int getMonsterId() {
        return this.monsterId;
    }

    public List<EnergyDropInfo> getEnergyDrops() {
        return this.energyDrops;
    }
}
