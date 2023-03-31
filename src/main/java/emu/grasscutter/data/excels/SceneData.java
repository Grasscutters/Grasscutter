package emu.grasscutter.data.excels;

import com.google.gson.annotations.SerializedName;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

import emu.grasscutter.game.props.SceneType;
import lombok.Getter;

@ResourceType(name = "SceneExcelConfigData.json")
@Getter
public class SceneData extends GameResource {
    @Getter(onMethod_ = @Override)
    private int id;
    @SerializedName("type")
    private SceneType sceneType;
    private String scriptData;
}
