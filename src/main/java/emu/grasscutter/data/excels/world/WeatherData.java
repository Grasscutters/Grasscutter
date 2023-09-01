package emu.grasscutter.data.excels.world;

import emu.grasscutter.data.*;
import emu.grasscutter.game.props.ClimateType;
import lombok.Getter;

@ResourceType(name = "WeatherExcelConfigData.json")
public class WeatherData extends GameResource {
    @Getter private int areaID;
    @Getter private int weatherAreaId;
    @Getter private String maxHeightStr;
    @Getter private int gadgetID;
    @Getter private boolean isDefaultValid;
    @Getter private String templateName;
    @Getter private int priority;
    @Getter private String profileName;
    @Getter private ClimateType defaultClimate;
    @Getter private boolean isUseDefault;
    @Getter private int sceneID;

    @Override
    public int getId() {
        return this.areaID;
    }
}
