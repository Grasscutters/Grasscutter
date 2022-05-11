package emu.grasscutter.data.custom;

import emu.grasscutter.data.custom.QuestConfigData.SubQuestConfigData;

public class QuestConfig {
	private final QuestConfigData mainQuest;
	private final SubQuestConfigData subQuest;
	
	public QuestConfig(QuestConfigData mainQuest, SubQuestConfigData subQuest) {
		this.mainQuest = mainQuest;
		this.subQuest = subQuest;
	}

	public int getId() {
		return subQuest.getSubId();
	}

	public QuestConfigData getMainQuest() {
		return mainQuest;
	}

	public SubQuestConfigData getSubQuest() {
		return subQuest;
	}
}
