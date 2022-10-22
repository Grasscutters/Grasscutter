package emu.grasscutter.data.excels;

import com.google.gson.annotations.SerializedName;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.Getter;

@ResourceType(name = {"AnimalCodexExcelConfigData.json"})
public class CodexAnimalData extends GameResource {
    private int Id;
    @Getter private String type;
    @Getter private int describeId;
    @Getter private int sortOrder;
    @SerializedName(value="countType", alternate={"OCCLHPBCDGL"})
    @Getter private CountType countType;

    @Override
    public int getId() {
        return Id;
    }

    public enum CountType {
        CODEX_COUNT_TYPE_KILL,
        CODEX_COUNT_TYPE_CAPTURE
    }
}
