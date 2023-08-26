package emu.grasscutter.data.excels.giving;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;
import java.util.List;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@ResourceType(name = "GivingGroupExcelConfigData.json")
public final class GivingGroupData extends GameResource {
    @SerializedName(value = "id", alternate = "Id")
    private int id;

    @SerializedName(value = "itemIds", alternate = "ItemIds")
    private List<Integer> itemIds;

    private int finishTalkId;
    private int mistakeTalkId;
}
