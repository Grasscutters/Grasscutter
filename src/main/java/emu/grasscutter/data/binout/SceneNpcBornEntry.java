package emu.grasscutter.data.binout;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.game.world.Position;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SceneNpcBornEntry {
    @SerializedName(
            value = "id",
            alternate = {"_id", "ID"})
    int id;

    @SerializedName(
            value = "configId",
            alternate = {"_configId"})
    int configId;

    @SerializedName(
            value = "pos",
            alternate = {"_pos"})
    Position pos;

    @SerializedName(
            value = "rot",
            alternate = {"_rot"})
    Position rot;

    @SerializedName(
            value = "groupId",
            alternate = {"_groupId"})
    int groupId;

    @SerializedName(
            value = "suiteIdList",
            alternate = {"_suiteIdList"})
    List<Integer> suiteIdList;
}
