package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;

@ResourceType(name = "MonsterDescribeExcelConfigData.json", loadPriority = LoadPriority.HIGH)
public class MonsterDescribeData extends GameResource {
    private int id;
    private long nameTextMapHash;
    private int titleID;
    private int specialNameLabID;
    private String icon;

    @Override
    public int getId() {
        return this.id;
    }

    public long getNameTextMapHash() {
        return this.nameTextMapHash;
    }

    public int getTitleID() {
        return this.titleID;
    }

    public int getSpecialNameLabID() {
        return this.specialNameLabID;
    }

    public String getIcon() {
        return this.icon;
    }

    @Override
    public void onLoad() {

    }
}
