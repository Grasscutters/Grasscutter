package emu.grasscutter.game.drop;

import java.util.List;

@SuppressWarnings("deprecation")
public class DropInfo {
    private int monsterId;
    private List<DropData> dropDataList;

    public int getMonsterId() {
        return monsterId;
    }

    public List<DropData> getDropDataList() {
        return dropDataList;
    }
}
