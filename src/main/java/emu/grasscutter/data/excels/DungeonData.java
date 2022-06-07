package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

import emu.grasscutter.game.props.SceneType;

@ResourceType(name = "DungeonExcelConfigData.json")
public class DungeonData extends GameResource {
	private int id;
	private int sceneId;
	private int showLevel;
	private int passRewardPreviewID;
	private String involveType; // TODO enum
	
	private RewardPreviewData previewData;
	    
	@Override
	public int getId() {
		return this.id;
	}

	public int getSceneId() {
		return sceneId;
	}
	
	public int getShowLevel() {
		return showLevel;
	}

	public RewardPreviewData getRewardPreview() {
		return previewData;
	}

	@Override
	public void onLoad() {
		if (this.passRewardPreviewID > 0) {
			this.previewData = GameData.getRewardPreviewDataMap().get(this.passRewardPreviewID);
		}
	}
}
