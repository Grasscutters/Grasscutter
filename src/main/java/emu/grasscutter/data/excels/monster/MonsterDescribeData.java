package emu.grasscutter.data.excels.monster;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;
import emu.grasscutter.data.ResourceType.LoadPriority;
import lombok.Getter;

@ResourceType(name = "MonsterDescribeExcelConfigData.json", loadPriority = LoadPriority.HIGH)
@Getter
public class MonsterDescribeData extends GameResource {
    @Getter(onMethod_ = @Override)
    private int id;

    private long nameTextMapHash;

    @SerializedName(
            value = "titleId",
            alternate = {"titleID"})
    private int titleId;

    @SerializedName(
            value = "specialNameLabId",
            alternate = {"specialNameLabID"})
    private int specialNameLabId;

    private MonsterSpecialNameData specialNameData;
}
