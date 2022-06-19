package emu.grasscutter.game.drop;

import java.util.List;

public class DropInfo {
    public int getMonsterId() {
        return this.monsterId;
    }

    public List<DropData> getDropDataList() {
        return this.dropDataList;
    }

    private int monsterId;
    private List<DropData> dropDataList;
}
