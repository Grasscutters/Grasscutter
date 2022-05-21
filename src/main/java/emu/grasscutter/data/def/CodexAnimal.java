package emu.grasscutter.data.def;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = {"AnimalCodexExcelConfigData.json"})
public class CodexAnimal extends GameResource {
    private int Id;
    private String Type;
    private int DescribeId;
    private int SortOrder;
    private CodexAnimalUnlockCondition BAINKHIIMJE;

    @Override
    public int getId() {
        return Id;
    }

    public String getType() {
        return Type;
    }

    public int getDescribeId() {
        return DescribeId;
    }

    public int getSortOrder() {
        return SortOrder;
    }

    public CodexAnimalUnlockCondition getUnlockCondition() {
        return BAINKHIIMJE;
    }
}
