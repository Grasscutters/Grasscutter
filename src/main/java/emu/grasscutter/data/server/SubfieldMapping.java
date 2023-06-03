package emu.grasscutter.data.server;

import lombok.Data;

@Data
public class SubfieldMapping {
    private int entityId;
    private SubfieldMappingEntry[] subfields;

    @Data
    public class SubfieldMappingEntry {
        private String subfieldName;
        private int drop_id;
    }
}
