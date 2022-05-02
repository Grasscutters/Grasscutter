package emu.grasscutter.game.drop;

import java.util.List;

public class DropInfo {
    public int getMonsterId() {
        return monsterId;
    }

    public List<DropData> getDropDataList() {
        return dropDataList;
    }

    private int monsterId;
    private List<DropData> dropDataList;
}
