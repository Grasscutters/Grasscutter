package emu.grasscutter.game.scenescript.types;

import lombok.Data;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.Arrays;
import java.util.List;

public class SceneGroup {
    @Data
    public static class Monster {
        private Integer configId;
        private Integer monsterId;
        private boolean disableWander;
        private Integer level;
        private Vec3 pos;
        private Vec3 rot;
        private Integer posId;

        private Monster(Integer configId, Integer monsterId, Boolean disableWander, Integer level, Vec3 pos, Vec3 rot, Integer posId) {
            this.configId = configId;
            this.monsterId = monsterId;
            this.disableWander = disableWander;
            this.level = level;
            this.pos = pos;
            this.rot = rot;
            this.posId = posId;
        }

        private Monster(LuaTable table) {
            this.configId = table.get("config_id").toint();
            this.monsterId = table.get("monster_id").toint();
            this.disableWander = table.get("disable_wander").toboolean();
            this.level = table.get("level").toint();
            this.pos = Vec3.fromLuaTable((LuaTable) table.get("pos"));
            this.rot = Vec3.fromLuaTable((LuaTable) table.get("rot"));
            this.posId = table.get("pos_id").toint();
        }

        public Monster fromLuaTable(LuaTable table) {
            return new Monster(table);
        }
    }

    @Data
    public static class Region {
        private Integer configId;
        private Vec3 pos;
        private Vec3 size;
        private Integer shape;

        private Region(Integer configId, Vec3 pos, Integer shape, Vec3 size) {
            this.configId = configId;
            this.pos = pos;
            this.shape = shape;
            this.size = size;
        }

        private Region(LuaTable table) {
            this.configId = table.get("config_id").toint();
            this.pos = Vec3.fromLuaTable((LuaTable) table.get("pos"));
            this.shape = table.get("shape").toint();
            this.size = Vec3.fromLuaTable((LuaTable) table.get("size"));
        }

        public Region fromLuaTable(LuaTable table) {
            return new Region(table);
        }
    }

    @Data
    public static class Suite {
        private List<Integer> gadgets;
        private List<Integer> monsters;
        private Integer randWeight;
        private List<Integer> regions;
        private List<String> triggers;

        private Suite(List<Integer> gadgets, List<Integer> monsters, Integer randWeight, List<Integer> regions, List<String> triggers) {
            this.gadgets = gadgets;
            this.monsters = monsters;
            this.randWeight = randWeight;
            this.regions = regions;
            this.triggers = triggers;
        }

        private Suite(LuaTable table) {
            LuaTable gadgetTable = ((LuaTable) table.get("gadgets"));
            gadgets = Arrays.stream(gadgetTable.keys())
                    .map(LuaValue::toint).toList();
            LuaTable monsterTable = ((LuaTable) table.get("monsters"));
            monsters = Arrays.stream(monsterTable.keys())
                    .map(LuaValue::toint).toList();
            this.randWeight = table.get("rand_weight").toint();
            LuaTable regionTable = ((LuaTable) table.get("regions"));
            regions = Arrays.stream(regionTable.keys())
                    .map(LuaValue::toint).toList();
            LuaTable triggerTable = ((LuaTable) table.get("triggers"));
            triggers = Arrays.stream(triggerTable.keys())
                    .map(LuaValue::toString).toList();
        }

        public Suite fromLuaTable(LuaTable table) {
            return new Suite(table);
        }

    }

    @Data
    public class Gadget {
        private Integer configId;
        private Integer gadgetId;
        private Integer level;
        private Vec3 pos;
        private Vec3 rot;

        private Gadget(Integer configId, Integer gadgetId, Integer level, Vec3 pos, Vec3 rot) {
            this.configId = configId;
            this.gadgetId = gadgetId;
            this.level = level;
            this.pos = pos;
            this.rot = rot;
        }

        private Gadget(LuaTable table) {
            this.configId = table.get("config_id").checkint();
            this.gadgetId = table.get("gadget_id").checkint();
            this.level = table.get("level").checkint();
            this.pos = Vec3.fromLuaTable((LuaTable) table.get("pos"));
            this.rot = Vec3.fromLuaTable((LuaTable) table.get("rot"));
        }

        public Gadget fromLuaTable(LuaTable table) {
            return new Gadget(table);
        }
    }
    public static class Trigger {

        private String action;
        private String condition;
        private Integer configId;
        private Integer event;
        private boolean forbidGuest;
        private String name;
        private String source;

        public Trigger(String action, String condition, Integer configId, Integer event, boolean forbidGuest, String name, String source) {
            this.action = action;
            this.condition = condition;
            this.configId = configId;
            this.event = event;
            this.forbidGuest = forbidGuest;
            this.name = name;
            this.source = source;
        }

        public Trigger(LuaTable table) {
            this.action = table.get("action").toString();
            this.condition = table.get("condition").toString();
            this.configId = table.get("config_id").checkint();
            this.event = table.get("event").checkint();
            this.forbidGuest = table.get("forbid_guest").toboolean();
            this.name = table.get("name").toString();
            this.source = table.get("source").toString();
        }

        public Trigger fromLuaTable(LuaTable table) {
            return new Trigger(table);
        }
    }
    Gadget[] gadgets;
    Monster[] monsters;
    Region[] regions;
    Suite[] suites;
    Trigger[] triggers;
    // TODO: struct variables
    // TODO: struct init_config
    // TODO: triggers bindings

}
