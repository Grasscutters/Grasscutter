package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = {"ReliquaryCodexExcelConfigData.json"})
public class CodexReliquaryData extends GameResource {
    private int Id;
    private int suitId;
    private int level;
    private int cupId;
    private int leatherId;
    private int capId;
    private int flowerId;
    private int sandId;
    private int sortOrder;

    public int getSortOrder() {
        return this.sortOrder;
    }

    public int getId() {
        return this.Id;
    }

    public int getSuitId() {
        return this.suitId;
    }

    public int getLevel() {
        return this.level;
    }

    public int getCupId() {
        return this.cupId;
    }

    public int getLeatherId() {
        return this.leatherId;
    }

    public int getCapId() {
        return this.capId;
    }

    public int getFlowerId() {
        return this.flowerId;
    }

    public int getSandId() {
        return this.sandId;
    }

    @Override
    public void onLoad() {
        GameData.getcodexReliquaryArrayList().add(this);
        GameData.getcodexReliquaryIdMap().put(this.getSuitId(), this);
    }
}
