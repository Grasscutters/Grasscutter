package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "DungeonExcelConfigData.json")
public class DungeonData extends GameResource {
    private int id;
    private int sceneId;
    private int showLevel;
    private int passRewardPreviewID;
    private String involveType; // TODO enum

    private RewardPreviewData previewData;

    private int statueCostID;
    private int statueCostCount;

    @Override
    public int getId() {
        return this.id;
    }

    public int getSceneId() {
        return this.sceneId;
    }

    public int getShowLevel() {
        return this.showLevel;
    }

    public RewardPreviewData getRewardPreview() {
        return this.previewData;
    }

    public int getStatueCostID() {
        return this.statueCostID;
    }

    public int getStatueCostCount() {
        return this.statueCostCount;
    }

    @Override
    public void onLoad() {
        if (this.passRewardPreviewID > 0) {
            this.previewData = GameData.getRewardPreviewDataMap().get(this.passRewardPreviewID);
        }
    }
}
