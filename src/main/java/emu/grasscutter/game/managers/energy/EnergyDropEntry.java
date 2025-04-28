package emu.grasscutter.game.managers.energy;

import java.util.List;
import lombok.Getter;

@Getter
public class EnergyDropEntry {
    private int dropId;
    private List<EnergyDropInfo> dropList;
}
