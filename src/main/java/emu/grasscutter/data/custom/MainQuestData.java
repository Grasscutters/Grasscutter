package emu.grasscutter.data.custom;

import emu.grasscutter.game.quest.enums.LogicType;
import emu.grasscutter.game.quest.enums.QuestTrigger;
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
	
	public static class SubQuestData {
		private int subId;

		public int getSubId() {
			return subId;
		}
	}
}
