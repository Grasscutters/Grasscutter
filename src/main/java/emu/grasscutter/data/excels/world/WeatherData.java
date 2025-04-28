package emu.grasscutter.data.excels.world;

import emu.grasscutter.data.*;
import emu.grasscutter.game.props.ClimateType;
import lombok.Getter;

@Getter
@ResourceType(name = "WeatherExcelConfigData.json")
public class WeatherData extends GameResource {
    private int areaID;
    private int weatherAreaId;
    private String maxHeightStr;
    private int gadgetID;
    private boolean isDefaultValid;
    private String templateName;
    private int priority;
    private String profileName;
    private ClimateType defaultClimate;
    private boolean isUseDefault;
    private int sceneID;

    @Override
    public int getId() {
        return this.areaID;
    }
}
