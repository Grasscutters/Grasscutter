package emu.grasscutter.data.def;

import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.props.FightProperty;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

@ResourceType(name = "ReliquaryLevelExcelConfigData.json")
public class ReliquaryLevelData extends GameResource {
	private int id;
	private Int2ObjectMap<Float> propMap;
	
	private int Rank;
	private int Level;
	private int Exp;
	private List<RelicLevelProperty> AddProps;
	
	@Override
	public int getId() {
		return this.id;
	}
	
	public int getRank() {
		return Rank;
	}
	
	public int getLevel() {
		return Level;
	}
	
	public int getExp() {
		return Exp;
	}
	
	public float getPropValue(FightProperty prop) {
		return getPropValue(prop.getId());
	}
	
	public float getPropValue(int id) {
		return propMap.get(id);
	}
	
	@Override
	public void onLoad() {
		this.id = (Rank << 8) + this.getLevel();
		this.propMap = new Int2ObjectOpenHashMap<>();
		for (RelicLevelProperty p : AddProps) {
			this.propMap.put(FightProperty.getPropByName(p.getPropType()).getId(), (Float) p.getValue());
		}
	}
	
	public class RelicLevelProperty {
		private String PropType;
		private float Value;
		
		public String getPropType() {
			return PropType;
		}
		
		public float getValue() {
			return Value;
		}
	}
}
