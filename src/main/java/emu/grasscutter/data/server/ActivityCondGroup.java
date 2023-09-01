package emu.grasscutter.data.server;

import lombok.Data;

import java.util.List;

@Data
public class ActivityCondGroup {
    int condGroupId;
    List<Integer> condIds;
}
