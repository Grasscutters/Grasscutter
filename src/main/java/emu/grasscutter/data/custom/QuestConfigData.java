package emu.grasscutter.data.custom;

import emu.grasscutter.game.quest.enums.LogicType;
import emu.grasscutter.game.quest.enums.QuestTriggerType;
import emu.grasscutter.game.quest.enums.QuestType;

public class QuestConfigData {
	private int id;
	private int series;
	private QuestType type;
	
	private long titleTextMapHash;
	private int[] suggestTrackMainQuestList;
	private int[] rewardIdList;
	
	private SubQuestConfigData[] subQuests;
	
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

	public SubQuestConfigData[] getSubQuests() {
		return subQuests;
	}

	public class SubQuestConfigData {
		private int subId;
		private int mainId;
		
		private LogicType acceptCondComb;
		private QuestCondition[] acceptCond;
		
		private LogicType finishCondComb;
		private QuestCondition[] finishCond;
		
		private LogicType failCondComb;
		private QuestCondition[] failCond;
		
		public int getSubId() {
			return subId;
		}
		
		public int getMainId() {
			return mainId;
		}
		
		public LogicType getAcceptCondComb() {
			return acceptCondComb;
		}
		
		public QuestCondition[] getAcceptCond() {
			return acceptCond;
		}
		
		public LogicType getFinishCondComb() {
			return finishCondComb;
		}
		
		public QuestCondition[] getFinishCond() {
			return finishCond;
		}
		
		public LogicType getFailCondComb() {
			return failCondComb;
		}
		
		public QuestCondition[] getFailCond() {
			return failCond;
		}
	}
	
	public class QuestCondition {
		private QuestTriggerType type;
		private int[] param;
		
		public QuestTriggerType getType() {
			return type;
		}
		
		public int[] getParam() {
			return param;
		}
	}
}
