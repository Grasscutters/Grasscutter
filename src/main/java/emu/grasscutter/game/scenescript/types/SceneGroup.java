package emu.grasscutter.game.scenescript.types;

import lombok.Data;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.Arrays;
import java.util.List;

public class SceneGroup {
    List<Gadget> gadgets;
    List<Monster> monsters;
    List<Region> regions;
    List<Suite> suites;
    List<Trigger> triggers;

    @Data
    public static class Monster {
        int configId;
        int monsterId;
        boolean disableWander;
        int level;
        Vec3 pos;
        Vec3 rot;
        int posId;

        public Monster(int configId, int monsterId, boolean disableWander, int level, Vec3 pos, Vec3 rot
                , int posId) {
            this.configId = configId;
            this.monsterId = monsterId;
            this.disableWander = disableWander;
            this.level = level;
            this.pos = pos;
            this.rot = rot;
            this.posId = posId;
        }

        public static Monster fromLuaTable(LuaTable table) {
            return new Monster(
                    table.get("config_id").toint(),
                    table.get("monster_id").toint(),
                    table.get("disable_wander").toboolean(),
                    table.get("level").toint(),
                    Vec3.fromLuaTable((LuaTable) table.get("pos")),
                    Vec3.fromLuaTable((LuaTable) table.get("rot")),
                    table.get("pos_id").toint()
            );
        }
    }

    @Data
    public static class Region {
        int configId;
        Vec3 pos;
        Vec3 size;
        int shape;

        public Region(int configId, Vec3 pos, int shape, Vec3 size) {
            this.configId = configId;
            this.pos = pos;
            this.shape = shape;
            this.size = size;
        }

        public static Region fromLuaTable(LuaTable table) {
            return new Region(
                    table.get("config_id").toint(),
                    Vec3.fromLuaTable((LuaTable) table.get("pos")),
                    table.get("shape").toint(),
                    Vec3.fromLuaTable((LuaTable) table.get("size"))
            );
        }
    }

    @Data
    public static class Suite {
        List<Integer> gadgets;
        List<Integer> monsters;
        int randWeight;
        List<Integer> regions;
        List<String> triggers;

        public Suite(List<Integer> gadgets, List<Integer> monsters, int randWeight, List<Integer> regions,
                     List<String> triggers) {
            this.gadgets = gadgets;
            this.monsters = monsters;
            this.randWeight = randWeight;
            this.regions = regions;
            this.triggers = triggers;
        }

        public static Suite fromLuaTable(LuaTable table) {

            var gadgetTable = ((LuaTable) table.get("gadgets"));
            var monsterTable = ((LuaTable) table.get("monsters"));
            var regionTable = ((LuaTable) table.get("regions"));
            var triggerTable = ((LuaTable) table.get("triggers"));

            return new Suite(
                    Arrays.stream(gadgetTable.keys())
                            .map(LuaValue::toint).toList(),
                    Arrays.stream(monsterTable.keys())
                            .map(LuaValue::toint).toList(),
                    table.get("rand_weight").toint(),
                    Arrays.stream(regionTable.keys())
                            .map(LuaValue::toint).toList(),
                    Arrays.stream(triggerTable.keys())
                            .map(LuaValue::toString).toList()
            );
        }

    }

    public static class Trigger {

        String action;
        String condition;
        int configId;
        int event;
        boolean forbidGuest;
        String name;
        String source;

        public Trigger(String action, String condition, int configId, int event, boolean forbidGuest,
                       String name, String source) {
            this.action = action;
            this.condition = condition;
            this.configId = configId;
            this.event = event;
            this.forbidGuest = forbidGuest;
            this.name = name;
            this.source = source;
        }

        public Trigger fromLuaTable(LuaTable table) {
            return new Trigger(
                    table.get("action").toString(),
                    table.get("condition").toString(),
                    table.get("config_id").checkint(),
                    table.get("event").checkint(),
                    table.get("forbid_guest").toboolean(),
                    table.get("name").toString(),
                    table.get("source").toString()
            );
        }
    }

    @Data
    public static class Gadget {
        int configId;
        int gadgetId;
        int level;
        Vec3 pos;
        Vec3 rot;

        private Gadget(int configId, int gadgetId, int level, Vec3 pos, Vec3 rot) {
            this.configId = configId;
            this.gadgetId = gadgetId;
            this.level = level;
            this.pos = pos;
            this.rot = rot;
        }

        public static Gadget fromLuaTable(LuaTable table) {
            return new Gadget(
                    table.get("config_id").toint(),
                    table.get("gadget_id").toint(),
                    table.get("level").toint(),
                    Vec3.fromLuaTable((LuaTable) table.get("pos")),
                    Vec3.fromLuaTable((LuaTable) table.get("rot"))
            );
        }
    }
    // TODO: struct variables
    // TODO: struct init_config
    // TODO: triggers bindings

}
