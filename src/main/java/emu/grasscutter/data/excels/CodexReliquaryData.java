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
        return sortOrder;
    }

    public int getId() {
        return Id;
    }

    public int getSuitId() {
        return suitId;
    }

    public int getLevel() {
        return level;
    }

    public int getCupId() {
        return cupId;
    }

    public int getLeatherId() {
        return leatherId;
    }

    public int getCapId() {
        return capId;
    }

    public int getFlowerId() {
        return flowerId;
    }

    public int getSandId() {
        return sandId;
    }

    @Override
    public void onLoad() {
        GameData.getcodexReliquaryArrayList().add(this);
        GameData.getcodexReliquaryIdMap().put(getSuitId(), this);
    }
}
