package emu.grasscutter.data.binout.config.fields;

import lombok.Data;

import java.util.Map;
import java.util.Set;

/**
 * Contains information about the entities SGVs
 */
@Data
public class ConfigGlobalValue {
    Set<String> serverGlobalValues;
    Map<String, Float> initServerGlobalValues;
}
