package emu.grasscutter.data.excels;

import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.ItemParamData;

@ResourceType(name = {"ForgeExcelConfigData.json"}, loadPriority = LoadPriority.HIGHEST)
public class ForgeData extends GameResource {
    private int id;
    private int playerLevel;
    private int forgeType;
    private int showItemId;
    private int resultItemId;
    private int resultItemCount;
    private int forgeTime;
    private int queueNum;
    private int scoinCost;
    private int priority;
    private int forgePoint;
    private List<ItemParamData> materialItems;

    @Override
    public int getId() {
        return this.id;
    }

    public int getPlayerLevel() {
        return playerLevel;
    }

    public int getForgeType() {
        return forgeType;
    }

    public int getResultItemId() {
        return resultItemId;
    }

    public int getResultItemCount() {
        return resultItemCount;
    }

    public int getForgeTime() {
        return forgeTime;
    }

    public int getQueueNum() {
        return queueNum;
    }

    public int getScoinCost() {
        return scoinCost;
    }

    public int getPriority() {
        return priority;
    }

    public int getForgePoint() {
        return forgePoint;
    }

    public List<ItemParamData> getMaterialItems() {
        return materialItems;
    }

    public int getShowItemId() {
        return showItemId;
    }

    @Override
    public void onLoad() {
    }
}
