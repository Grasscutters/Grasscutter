package emu.grasscutter.data.server;

import java.util.List;
import lombok.Data;

@Data
public class ActivityCondGroup {
    int condGroupId;
    List<Integer> condIds;
}
