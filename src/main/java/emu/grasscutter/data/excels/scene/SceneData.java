package emu.grasscutter.data.excels.scene;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;
import emu.grasscutter.game.props.SceneType;
import lombok.Getter;

import java.util.List;

@ResourceType(name = "SceneExcelConfigData.json")
@Getter
public final class SceneData extends GameResource {
    @Getter(onMethod_ = @Override)
    private int id;

    @SerializedName("type")
    private SceneType sceneType;

    private String scriptData;
    private String levelEntityConfig;
    private List<Integer> specifiedAvatarList;
}
