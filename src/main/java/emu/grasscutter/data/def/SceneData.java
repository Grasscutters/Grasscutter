package emu.grasscutter.data.def;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

import emu.grasscutter.game.props.SceneType;

@ResourceType(name = "SceneExcelConfigData.json")
public class SceneData extends GameResource {
	private int Id;
	private SceneType Type;
	private String ScriptData;

	    
	@Override
	public int getId() {
		return this.Id;
	}

	public SceneType getSceneType() {
		return Type;
	}

	public String getScriptData() {
		return ScriptData;
	}

	@Override
	public void onLoad() {

	}
}
