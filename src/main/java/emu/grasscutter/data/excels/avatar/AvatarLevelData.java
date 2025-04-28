package emu.grasscutter.data.excels.avatar;

import emu.grasscutter.data.*;
import lombok.Getter;

@Getter
@ResourceType(name = "AvatarLevelExcelConfigData.json")
public class AvatarLevelData extends GameResource {
    private int level;
    private int exp;

    @Override
    public int getId() {
        return this.level;
    }
}
