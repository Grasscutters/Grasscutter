package emu.grasscutter.loot.entry;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.loot.LootContext;
import emu.grasscutter.loot.LootPool;

import java.lang.reflect.Type;
import java.util.List;

@JsonAdapter(LootEntry.LootEntryDeserializer.class)
public interface LootEntry {

    /**
     * Try loot in specified context
     *
     * @return the specified item
     */
    List<GameItem> loot(LootContext ctx, LootPool pool);

    int getWeight(LootContext ctx);

    class LootEntryDeserializer implements JsonDeserializer<LootEntry> {

        @Override
        public LootEntry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            var thisType = json.getAsJsonObject().get("type");
            var type = thisType == null ? "item" : thisType.getAsString();
            return switch (type) {
                case "item" -> context.deserialize(json, ItemLootEntry.class);
                case "loot_table" -> context.deserialize(json, LootTableEntry.class);
                case "uniform_group" -> context.deserialize(json, UniformGroupLootEntry.class);
                default -> throw new JsonParseException("no such loot entry type " + thisType);
            };
        }
    }
}
