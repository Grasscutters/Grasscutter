package emu.grasscutter.data.binout;

import emu.grasscutter.game.quest.enums.QuestType;

public class MainQuestData {
    private int id;
    private int series;
    private QuestType type;

    private long titleTextMapHash;
    private int[] suggestTrackMainQuestList;
    private int[] rewardIdList;

    private SubQuestData[] subQuests;

    public int getId() {
        return this.id;
    }

    public int getSeries() {
        return this.series;
    }

    public QuestType getType() {
        return this.type;
    }

    public long getTitleTextMapHash() {
        return this.titleTextMapHash;
    }

    public int[] getSuggestTrackMainQuestList() {
        return this.suggestTrackMainQuestList;
    }

    public int[] getRewardIdList() {
        return this.rewardIdList;
    }

    public SubQuestData[] getSubQuests() {
        return this.subQuests;
    }

    public static class SubQuestData {
        private int subId;

        public int getSubId() {
            return this.subId;
        }
    }
}
