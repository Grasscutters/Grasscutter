package emu.grasscutter.scripts.serializer;

import java.util.List;

import org.luaj.vm2.LuaTable;

public interface Serializer {
	
	public <T> List<T> toList(Class<T> type, Object obj);
	
	public <T> T toObject(Class<T> type, Object obj);
}
