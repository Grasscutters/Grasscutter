package emu.grasscutter.game.world;

import emu.grasscutter.loot.LootTable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChestReward {
    List<String> objNames;
    String lootTable;
    transient LootTable table;

}
