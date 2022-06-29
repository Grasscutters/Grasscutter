package emu.grasscutter.loot.number;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.loot.LootContext;

import java.lang.reflect.Type;
import java.util.Optional;

@JsonAdapter(NumberProvider.NumberProviderDeserializer.class)
public interface NumberProvider {
    String getName();
    default Number roll() {
        return this.roll(null);
    }

    Number roll(LootContext ctx);

    static NumberProvider of(int count) {
        return new ConstantNumberProvider(count);
    }

    static Optional<NumberProvider> parse(JsonElement json) {
        if (json == null) return Optional.empty();

        if (json.isJsonPrimitive()) {
            Number num = json.getAsNumber();
            return Optional.of(new ConstantNumberProvider(num));
        } else {
            var obj = json.getAsJsonObject();
            return Optional.ofNullable(switch (obj.get("type").getAsString()) {
                case "constant" -> new ConstantNumberProvider(obj.get("value").getAsNumber());
                case "uniform" -> new UniformNumberProvider(parse(obj.get("min")).orElseThrow(), parse(obj.get("max")).orElseThrow());
                case "binomial" -> new BinomialNumberProvider(parse(obj.get("n")).orElseThrow(), parse(obj.get("q")).orElseThrow());
                case "lerp" -> new LerpNumberProvider(parse(obj.get("value")).orElseThrow(), Grasscutter.getGsonFactory().fromJson(obj.get("coords"), int[][].class));
                case "get_data" -> new DataNumberProvider(obj.get("name").getAsString());
                default -> null;
            });
        }
    }

    class NumberProviderDeserializer implements JsonDeserializer<NumberProvider> {
        @Override
        public NumberProvider deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return parse(json).orElseThrow(() -> new JsonParseException("Cannot parse number"));
        }
    }

}
