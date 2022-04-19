package emu.grasscutter.data.def;

import emu.grasscutter.data.GenshinData;
import emu.grasscutter.data.GenshinResource;
import emu.grasscutter.data.ResourceType;

import emu.grasscutter.game.props.SceneType;

@ResourceType(name = "SceneExcelConfigData.json")
public class SceneData extends GenshinResource {
	private int Id;
	private SceneType SceneType;
	private String ScriptData;
	    
	@Override
	public int getId() {
		return this.Id;
	}

	public SceneType getSceneType() {
		return SceneType;
	}

	public String getScriptData() {
		return ScriptData;
	}

	@Override
	public void onLoad() {
		
	}
}
