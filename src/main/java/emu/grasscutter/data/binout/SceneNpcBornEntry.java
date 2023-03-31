package emu.grasscutter.data.binout;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.utils.Position;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SceneNpcBornEntry {
    @SerializedName(value = "id", alternate = {"_id", "ID"})
    int id;

    @SerializedName(value = "configId", alternate = {"_configId"})
    int configId;

    @SerializedName(value = "pos", alternate = {"_pos"})
    Position pos;

    @SerializedName(value = "rot", alternate = {"_rot"})
    Position rot;

    @SerializedName(value = "groupId", alternate = {"_groupId"})
    int groupId;

    @SerializedName(value = "suiteIdList", alternate = {"_suiteIdList"})
    List<Integer> suiteIdList;
}
