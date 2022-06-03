package emu.grasscutter.data.custom;

import emu.grasscutter.utils.Position;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SceneNpcBornEntry {
    int id;
    int configId;
    Position pos;
    Position rot;
    int groupId;
    List<Integer> suiteIdList;
}
