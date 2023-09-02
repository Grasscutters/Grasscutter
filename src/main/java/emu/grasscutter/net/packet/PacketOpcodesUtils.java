package emu.grasscutter.net.packet;

import emu.grasscutter.*;
import emu.grasscutter.utils.JsonUtils;
import it.unimi.dsi.fastutil.ints.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class PacketOpcodesUtils {
    public static final Set<Integer> LOOP_PACKETS =
            Set.of(
                    PacketOpcodes.PingReq,
                    PacketOpcodes.PingRsp,
                    PacketOpcodes.WorldPlayerRTTNotify,
                    PacketOpcodes.UnionCmdNotify,
                    PacketOpcodes.QueryPathReq,
                    PacketOpcodes.QueryPathRsp,

                    // Satiation sends these every tick
                    PacketOpcodes.PlayerTimeNotify,
                    PacketOpcodes.PlayerGameTimeNotify,
                    PacketOpcodes.AvatarPropNotify,
                    PacketOpcodes.AvatarSatiationDataNotify);
    private static final Int2ObjectMap<String> opcodeMap;

    static {
        opcodeMap = new Int2ObjectOpenHashMap<>();

        var fields = PacketOpcodes.class.getFields();
        for (var f : fields) {
            if (f.getType().equals(int.class)) {
                try {
                    opcodeMap.put(f.getInt(null), f.getName());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    public static String getOpcodeName(int opcode) {
        if (opcode <= 0) return "UNKNOWN";
        return opcodeMap.getOrDefault(opcode, "UNKNOWN");
    }

    public static void dumpPacketIds() {
        try (var writer = new FileWriter("./PacketIds_" + GameConstants.VERSION + ".json")) {
            // Create sorted tree map
            var packetIds =
                    opcodeMap.int2ObjectEntrySet().stream()
                            .filter(e -> e.getIntKey() > 0)
                            .collect(
                                    Collectors.toMap(
                                            Int2ObjectMap.Entry::getIntKey,
                                            Int2ObjectMap.Entry::getValue,
                                            (k, v) -> v,
                                            TreeMap::new));
            // Write to file
            writer.write(JsonUtils.encode(packetIds));
            Grasscutter.getLogger().info("Dumped packet IDs.");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
