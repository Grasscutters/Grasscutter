package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = {"AnimalCodexExcelConfigData.json"})
public class CodexAnimalData extends GameResource {
    private int Id;
    private String type;
    private int describeId;
    private int sortOrder;
    private CodexAnimalUnlockCondition OCCLHPBCDGL;

    @Override
    public int getId() {
        return Id;
    }

    public String getType() {
        return type;
    }

    public int getDescribeId() {
        return describeId;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public CodexAnimalUnlockCondition getUnlockCondition() {
        return OCCLHPBCDGL;
    }

    public enum CodexAnimalUnlockCondition {
        CODEX_COUNT_TYPE_KILL,
        CODEX_COUNT_TYPE_CAPTURE
    }
}
