package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.props.FightProperty;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.List;

@ResourceType(name = "ReliquaryLevelExcelConfigData.json")
public class ReliquaryLevelData extends GameResource {
    private int id;
    private Int2ObjectMap<Float> propMap;

    private int rank;
    private int level;
    private int exp;
    private List<RelicLevelProperty> addProps;

    @Override
    public int getId() {
        return this.id;
    }

    public int getRank() {
        return this.rank;
    }

    public int getLevel() {
        return this.level;
    }

    public int getExp() {
        return this.exp;
    }

    public float getPropValue(FightProperty prop) {
        return this.getPropValue(prop.getId());
    }

    public float getPropValue(int id) {
        return this.propMap.get(id);
    }

    @Override
    public void onLoad() {
        this.id = (this.rank << 8) + this.getLevel();
        this.propMap = new Int2ObjectOpenHashMap<>();
        for (RelicLevelProperty p : this.addProps) {
            this.propMap.put(FightProperty.getPropByName(p.getPropType()).getId(), (Float) p.getValue());
        }
    }

    public class RelicLevelProperty {
        private String propType;
        private float value;

        public String getPropType() {
            return this.propType;
        }

        public float getValue() {
            return this.value;
        }
    }
}
