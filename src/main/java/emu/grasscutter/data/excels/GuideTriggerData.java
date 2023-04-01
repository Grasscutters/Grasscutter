package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(callSuper=false)
@ResourceType(name = "GuideTriggerExcelConfigData.json")
public class GuideTriggerData extends GameResource {
    // more like open state guide than quest guide
    private int id; // dont use, just to prevent resource loader from not functioning
    private String guideName;
    private String type;
    private String openState;

    @Override
    public int getId() {
        return this.id;
    }

    public void onLoad() {
        GameData.getGuideTriggerDataStringMap().put(getGuideName(), this);
    }
}
