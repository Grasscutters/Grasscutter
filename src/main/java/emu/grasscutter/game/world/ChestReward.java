package emu.grasscutter.game.world;

import emu.grasscutter.game.inventory.ItemDef;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
