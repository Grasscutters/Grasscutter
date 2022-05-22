package emu.grasscutter.data.def;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = {"ReliquaryCodexExcelConfigData.json"})
public class CodexReliquaryData extends GameResource {
    private int Id;
    private int SuitId;
    private int Level;
    private int CupId;
    private int LeatherId;
    private int CapId;
    private int FlowerId;
    private int SandId;
    private int SortOrder;

    public int getSortOrder() {
        return SortOrder;
    }

    public int getId() {
        return Id;
    }

    public int getSuitId() {
        return SuitId;
    }

    public int getLevel() {
        return Level;
    }

    public int getCupId() {
        return CupId;
    }

    public int getLeatherId() {
        return LeatherId;
    }

    public int getCapId() {
        return CapId;
    }

    public int getFlowerId() {
        return FlowerId;
    }

    public int getSandId() {
        return SandId;
    }

    @Override
    public void onLoad() {
        GameData.getcodexReliquaryArrayList().add(this);
        GameData.getcodexReliquaryIdMap().put(getSuitId(), this);
    }
}
