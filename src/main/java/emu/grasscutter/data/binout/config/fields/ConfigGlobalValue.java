package emu.grasscutter.data.binout.config.fields;

import java.util.*;
import lombok.Data;

/** Contains information about the entities SGVs */
@Data
public class ConfigGlobalValue {
    Set<String> serverGlobalValues;
    Map<String, Float> initServerGlobalValues;
}
