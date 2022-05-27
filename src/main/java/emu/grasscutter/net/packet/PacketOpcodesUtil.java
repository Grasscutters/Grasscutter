package emu.grasscutter.net.packet;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class PacketOpcodesUtil {
	private static Int2ObjectMap<String> opcodeMap;
	
	static {
		opcodeMap = new Int2ObjectOpenHashMap<String>();

		Field[] fields = PacketOpcodes.class.getFields();

		for (Field f : fields) {
			if(f.getType().equals(int.class)) {
				try {
					opcodeMap.put(f.getInt(null), f.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String getOpcodeName(int opcode) {
		if (opcode <= 0) return "UNKNOWN";
		return opcodeMap.getOrDefault(opcode, "UNKNOWN");
	}

	public static void dumpOpcodes() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("opcodes.ini"));
			for (Int2ObjectMap.Entry<String> entry : opcodeMap.int2ObjectEntrySet()) {
				out.write(String.format("%04X=%s%s", entry.getIntKey(), entry.getValue(), System.lineSeparator()));
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
