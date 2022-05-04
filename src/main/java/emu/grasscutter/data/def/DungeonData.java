package emu.grasscutter.data.def;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

import emu.grasscutter.game.props.SceneType;

@ResourceType(name = "DungeonExcelConfigData.json")
public class DungeonData extends GameResource {
	private int Id;
	private int SceneId;
	private int ShowLevel;
	private int PassRewardPreviewID;
	private String InvolveType; // TODO enum
	
	private RewardPreviewData previewData;
	    
	@Override
	public int getId() {
		return this.Id;
	}

	public int getSceneId() {
		return SceneId;
	}
	
	public int getShowLevel() {
		return ShowLevel;
	}

	public RewardPreviewData getRewardPreview() {
		return previewData;
	}

	@Override
	public void onLoad() {
		if (this.PassRewardPreviewID > 0) {
			this.previewData = GameData.getRewardPreviewDataMap().get(this.PassRewardPreviewID);
		}
	}
}
