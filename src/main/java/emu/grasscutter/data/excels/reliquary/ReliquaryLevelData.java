package emu.grasscutter.data.excels.reliquary;

import emu.grasscutter.data.*;
import emu.grasscutter.game.props.FightProperty;
import it.unimi.dsi.fastutil.ints.*;
import java.util.List;
import lombok.Getter;

@ResourceType(name = "ReliquaryLevelExcelConfigData.json")
public class ReliquaryLevelData extends GameResource {
    @Getter(onMethod_ = @Override)
    private int id;

    private Int2FloatMap propMap;

    @Getter private int rank;
    @Getter private int level;
    @Getter private int exp;
    private List<RelicLevelProperty> addProps;

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

    @Getter
    public class RelicLevelProperty {
        private String propType;
        private float value;
    }
}
