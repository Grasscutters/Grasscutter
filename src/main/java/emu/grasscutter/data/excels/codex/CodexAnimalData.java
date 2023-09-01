package emu.grasscutter.data.excels.codex;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;
import lombok.Getter;

@ResourceType(name = {"AnimalCodexExcelConfigData.json"})
@Getter
public class CodexAnimalData extends GameResource {
    @Getter(onMethod_ = @Override)
    private int Id;

    private String type;
    private int describeId;
    private int sortOrder;

    @SerializedName(
            value = "countType",
            alternate = {"OCCLHPBCDGL"})
    private CountType countType;

    public enum CountType {
        CODEX_COUNT_TYPE_KILL,
        CODEX_COUNT_TYPE_CAPTURE
    }
}
