package emu.grasscutter.data.excels;

import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.props.FightProperty;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

@ResourceType(name = "ReliquaryLevelExcelConfigData.json")
public class ReliquaryLevelData extends GameResource {
    private int id;
    private Int2FloatMap propMap;

    private int rank;
    private int level;
    private int exp;
    private List<RelicLevelProperty> addProps;

    @Override
    public int getId() {
        return this.id;
    }

    public int getRank() {
        return rank;
    }

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

    public float getPropValue(FightProperty prop) {
        return getPropValue(prop.getId());
    }

    public float getPropValue(int id) {
        return propMap.getOrDefault(id, 0f);
    }

    @Override
    public void onLoad() {
        this.id = (rank << 8) + this.getLevel();
        this.propMap = new Int2FloatOpenHashMap();
        for (RelicLevelProperty p : addProps) {
            this.propMap.put(FightProperty.getPropByName(p.getPropType()).getId(), p.getValue());
        }
    }

    public class RelicLevelProperty {
        private String propType;
        private float value;

        public String getPropType() {
            return propType;
        }

        public float getValue() {
            return value;
        }
    }
}
