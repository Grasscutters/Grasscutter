package emu.grasscutter.loot.function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.loot.LootContext;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Loot Function are utilities to alter the result of the loot table
 */
@JsonAdapter(LootFunction.LootFunctionDeserializer.class)
public abstract class LootFunction {

    public abstract String getName();

    /**
     * If true, the function will only be called once regardless of the count of the items.
     * If true, the GameItem passed will be always null.
     *
     * @return if the function is not an item modifier
     */
    public abstract boolean hasSideEffect();


    protected Map<String, JsonElement> args;

    public LootFunction(Map<String, JsonElement> args) {
        this.args = args;
    }

    protected void throwArgumentError(String arg, String expected) {
        throw new RuntimeException(String.format("Loot function %s expected %s for argument %s.", getName(), arg, expected));
    }
    public boolean isItemModifier () {
        return !hasSideEffect();
    }

    /**
     * Run loot function on specified {@link GameItem}
     * @param ctx The loot context
     * @param item The item being modified
     */
    public abstract void run(LootContext ctx, GameItem item);


    static class LootFunctionDeserializer implements JsonDeserializer<LootFunction> {
        @Override
        public LootFunction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            var obj = json.getAsJsonObject();
            var typeName = obj.get("function").getAsString();
            var args = obj.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            return switch(typeName) {
                case "set_count" -> new SetCountFunction(args);
                case "set_data" -> new SetDataFunction(args);
                case "inc_data" -> new IncDataFunction(args);
                default -> throw new JsonParseException("Loot function " + typeName + " does not exist");
            };
        }
    }
}
