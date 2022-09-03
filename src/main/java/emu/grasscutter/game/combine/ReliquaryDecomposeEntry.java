package emu.grasscutter.game.combine;

import java.util.List;

public class ReliquaryDecomposeEntry {
    private int configId;
    private List<Integer> items;

    public int getConfigId() {
        return configId;
    }
    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public List<Integer> getItems() {
        return items;
    }
    public void setItems(List<Integer> items) {
        this.items = items;
    } 
}
