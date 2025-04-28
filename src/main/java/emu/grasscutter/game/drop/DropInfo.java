package emu.grasscutter.game.drop;

import java.util.List;
import lombok.Getter;

@Getter
@SuppressWarnings("deprecation")
public class DropInfo {
    private int monsterId;
    private List<DropData> dropDataList;
}
