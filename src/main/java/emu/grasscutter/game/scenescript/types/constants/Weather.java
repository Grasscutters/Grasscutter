package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Weather {
	public static class ClimateType extends TwoArgFunction {
		public static final int CLIMATE_NONE = 0;
		public static final int CLIMATE_SUNNY = 1;
		public static final int CLIMATE_CLOUDY = 2;
		public static final int CLIMATE_RAIN = 3;
		public static final int CLIMATE_THUNDERSTORM = 4;
		public static final int CLIMATE_SNOW = 5;
		public static final int CLIMATE_MIST = 6;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstWeather = new LuaTable();
			scriptConstWeather.set("CLIMATE_NONE", LuaValue.valueOf(CLIMATE_NONE));
			scriptConstWeather.set("CLIMATE_SUNNY", LuaValue.valueOf(CLIMATE_SUNNY));
			scriptConstWeather.set("CLIMATE_CLOUDY", LuaValue.valueOf(CLIMATE_CLOUDY));
			scriptConstWeather.set("CLIMATE_RAIN", LuaValue.valueOf(CLIMATE_RAIN));
			scriptConstWeather.set("CLIMATE_THUNDERSTORM", LuaValue.valueOf(CLIMATE_THUNDERSTORM));
			scriptConstWeather.set("CLIMATE_SNOW", LuaValue.valueOf(CLIMATE_SNOW));
			scriptConstWeather.set("CLIMATE_MIST", LuaValue.valueOf(CLIMATE_MIST));
			env.set("ClimateType", scriptConstWeather);
			env.get("package").get("loaded").set("ClimateType", scriptConstWeather);
			return env;
		}
	}
}
