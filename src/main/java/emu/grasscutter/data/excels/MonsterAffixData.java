package emu.grasscutter.data.excels;

import java.util.ArrayList;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.FightPropData;
import lombok.Getter;

@ResourceType(name = "MonsterAffixExcelConfigData.json")
public class MonsterAffixData extends GameResource {

    private int id;
    @Getter private String affix;
    @Getter private String comment;
    @Getter private String[] abilityName; //Declared as list but used as single element
    @Getter private boolean isCommon;
    @Getter private boolean preAdd;
    @Getter public String isLegal;
    @Getter public String iconPath;
    @Getter public String generalSkillIcon;

    @Override
    public int getId() {
        return id;
    }
}
