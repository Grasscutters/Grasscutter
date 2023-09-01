package emu.grasscutter.data.excels.world;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;
import emu.grasscutter.game.props.ElementType;
import lombok.Getter;

@ResourceType(name = "WorldAreaConfigData.json")
public class WorldAreaData extends GameResource {
    private int ID;
    @Getter private ElementType elementType;

    @Getter
    @SerializedName("AreaNameTextMapHash")
    private long textMapHash;

    @Getter
    @SerializedName("AreaID1")
    private int parentArea;

    @Getter
    @SerializedName("AreaID2")
    private int childArea;

    @Getter
    @SerializedName("SceneID")
    private int sceneId;

    @Override
    public int getId() {
        return (this.childArea << 16) + this.parentArea;
    }
}
