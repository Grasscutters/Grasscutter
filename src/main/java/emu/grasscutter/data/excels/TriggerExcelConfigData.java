package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.Getter;

@ResourceType(name = "TriggerExcelConfigData.json") @Getter
public class TriggerExcelConfigData extends GameResource {
    @Getter private int id;
    private int sceneId;
    private int groupId;
    private String triggerName;
}
