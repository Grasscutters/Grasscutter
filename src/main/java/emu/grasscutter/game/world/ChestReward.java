package emu.grasscutter.game.world;

import emu.grasscutter.game.inventory.ItemDef;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChestReward {
    List<String> objNames;
    int advExp;
    int resin;
    int mora;
    int sigil;
    List<ItemDef> content;
    int randomCount;
    List<ItemDef> randomContent;

}
