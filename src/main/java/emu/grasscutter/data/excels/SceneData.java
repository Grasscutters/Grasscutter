package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.props.SceneType;

@ResourceType(name = "SceneExcelConfigData.json")
public class SceneData extends GameResource {
    private int id;
    private SceneType type;
    private String scriptData;

    @Override
    public int getId() {
        return this.id;
    }

    public SceneType getSceneType() {
        return this.type;
    }

    public String getScriptData() {
        return this.scriptData;
    }

    @Override
    public void onLoad() {

    }
}
