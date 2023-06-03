package emu.grasscutter.data.server;

import lombok.Data;

@Data
public final class SubfieldMapping {
    private int entityId;
    private SubfieldMappingEntry[] subfields;

    @Data
    public static class SubfieldMappingEntry {
        private String subfieldName;
        private int drop_id;
    }
}
