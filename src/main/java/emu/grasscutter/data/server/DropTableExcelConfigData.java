package emu.grasscutter.data.server;

import lombok.Data;

@Data
public final class DropTableExcelConfigData {
    private int id;
    private int randomType;
    private int dropLevel;
    private DropVectorEntry[] dropVec;
    private int nodeType;
    private boolean fallToGround;
    private int sourceType;
    private int everydayLimit;
    private int historyLimit;
    private int activityLimit;

    @Data
    public static class DropVectorEntry {
        private int itemId;
        private String countRange;
        private int weight;
    }
}
