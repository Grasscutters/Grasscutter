package emu.grasscutter.loot;

import com.google.gson.reflect.TypeToken;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.DataLoader;
import emu.grasscutter.game.inventory.GameItem;
import it.unimi.dsi.fastutil.Pair;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class LootRegistry {
    public static final LootTable DEFAULT_LOOT = new LootTable() {
        @Override
        public List<GameItem> loot(LootContext ctx) {
            // TODO Return nothing before release
            return List.of(new GameItem(100001));
        }
    };


    private final String name;
    private final HashMap<Predicate<Integer>, LootTable> rules;

    public LootRegistry(String name, HashMap<Predicate<Integer>, LootTable> ret) {
        this.name = name;
        this.rules = ret;
    }

    public LootTable getLootTable (int matcher) {
        for (var k : rules.entrySet()) {
            if (k.getKey().test(matcher)) {
                return k.getValue();
            }
        }
        Grasscutter.getLogger().debug("Loot table not found for " + matcher + " in registry " + name);
        return DEFAULT_LOOT;
    }

    private static Predicate<Integer> getLootTablePredicate(String tester) {
        var parts = tester.split(",");
        var cond = new ArrayList<Pair<Integer, Integer>>();
        for (var p : parts) {
            var f = p.split("-");
            if (f.length == 2) {
                cond.add(Pair.of(Integer.parseInt(f[0]), Integer.parseInt(f[1])));
            } else if (f.length == 1) {
                cond.add(Pair.of(Integer.parseInt(f[0]), Integer.parseInt(f[0])));
            } else {
                throw new RuntimeException("Invalid loot condition selector");
            }
        }
        return i -> cond.stream().anyMatch(c -> i >= c.left() && i <= c.right());
    }

    private static LootTable loadTableFromDisk(String name) {
        try (Reader fileReader = new InputStreamReader(DataLoader.load("loot/" + name))) {
            return Grasscutter.getGsonFactory().fromJson(fileReader, LootTable.class);
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load drop data.", e);
            return DEFAULT_LOOT;
        }
    }

    public static LootRegistry getLootRegistry(String name) {
        try (Reader fileReader = new InputStreamReader(DataLoader.load(name))) {
            HashMap<Predicate<Integer>, LootTable> ret = new HashMap<>();
            Type type = new TypeToken<Map<String, String>>() {}.getType();
            Map<String, String> rules = Grasscutter.getGsonFactory().fromJson(fileReader, type);
            rules.forEach((key, value) -> ret.put(getLootTablePredicate(key), loadTableFromDisk(value)));
            return new LootRegistry(name, ret);
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load drop registry", e);
            return new LootRegistry(name, new HashMap<>());
        }
    }
}
