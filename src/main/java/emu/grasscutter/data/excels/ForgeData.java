package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.ItemParamData;

import java.util.List;

@ResourceType(name = {"ForgeExcelConfigData.json"}, loadPriority = LoadPriority.HIGHEST)
public class ForgeData extends GameResource {
    private int id;
    private int playerLevel;
    private int forgeType;
    private int resultItemId;
    private int resultItemCount;
    private int forgeTime;
    private int queueNum;
    private int scoinCost;
    private int priority;
    private List<ItemParamData> materialItems;

    @Override
    public int getId() {
        return this.id;
    }

    public int getPlayerLevel() {
        return this.playerLevel;
    }

    public int getForgeType() {
        return this.forgeType;
    }

    public int getResultItemId() {
        return this.resultItemId;
    }

    public int getResultItemCount() {
        return this.resultItemCount;
    }

    public int getForgeTime() {
        return this.forgeTime;
    }

    public int getQueueNum() {
        return this.queueNum;
    }

    public int getScoinCost() {
        return this.scoinCost;
    }

    public int getPriority() {
        return this.priority;
    }

    public List<ItemParamData> getMaterialItems() {
        return this.materialItems;
    }

    @Override
    public void onLoad() {
    }
}
