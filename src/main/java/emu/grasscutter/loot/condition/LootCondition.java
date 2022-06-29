package emu.grasscutter.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;
import emu.grasscutter.loot.LootContext;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Loot Function are utilities to alter the result of the loot table
 */
@JsonAdapter(LootCondition.LootConditionDeserializer.class)
public abstract class LootCondition {

    public abstract String getName();

    protected Map<String, JsonElement> args;

    public LootCondition(Map<String, JsonElement> args) {
        this.args = args;
    }

    protected void throwArgumentError(String arg, String expected) {
        throw new RuntimeException(String.format("Loot function %s expected %s for argument %s.", getName(), arg, expected));
    }

    public abstract boolean test(LootContext ctx);


    static class LootConditionDeserializer implements JsonDeserializer<LootCondition> {
        @Override
        public LootCondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            var obj = json.getAsJsonObject();
            var typeName = obj.get("condition").getAsString();
            var args = obj.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            return switch(typeName) {
                case "monster_level" -> new MonsterLevelCondition(args);
                default -> throw new JsonParseException("Loot function " + typeName + " does not exist");
            };
        }
    }
}
