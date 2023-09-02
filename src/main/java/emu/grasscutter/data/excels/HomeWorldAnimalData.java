package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@ResourceType(name = "HomeworldAnimalExcelConfigData.json")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class HomeWorldAnimalData extends GameResource {
    int furnitureID;
    int monsterID;
    int isRebirth;
    int rebirthCD;

    @Override
    public int getId() {
        return this.furnitureID;
    }
}
