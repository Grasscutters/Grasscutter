package emu.grasscutter.scripts.data;

import java.util.List;

import emu.grasscutter.scripts.ScriptLoader;
import emu.grasscutter.scripts.engine.LuaTable;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class SceneGarbage {
	public List<SceneGadget> gadgets;

	public SceneGarbage(LuaTable garbageData, SceneGroup group){
		gadgets = ScriptLoader.getSerializer().toList(SceneGadget.class, garbageData.get("gadgets"));
		gadgets.forEach(m -> m.group = group);
	}
}
