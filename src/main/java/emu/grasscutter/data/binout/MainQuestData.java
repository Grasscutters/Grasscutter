package emu.grasscutter.data.binout;

import emu.grasscutter.game.quest.enums.QuestType;
import lombok.Data;

public class MainQuestData {
	private int id;
	private int series;
	private QuestType type;
	
	private long titleTextMapHash;
	private int[] suggestTrackMainQuestList;
	private int[] rewardIdList;
	
	private SubQuestData[] subQuests;
	
	public int getId() {
		return id;
	}
	
	public int getSeries() {
		return series;
	}
	
	public QuestType getType() {
		return type;
	}
	
	public long getTitleTextMapHash() {
		return titleTextMapHash;
	}
	
	public int[] getSuggestTrackMainQuestList() {
		return suggestTrackMainQuestList;
	}

	public int[] getRewardIdList() {
		return rewardIdList;
	}
	
	public SubQuestData[] getSubQuests() {
		return subQuests;
	}

    @Data
	public static class SubQuestData {
		private int subId;
        private int order;
	}
}
