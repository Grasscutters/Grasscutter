package emu.grasscutter.game.quest;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Transient;
import emu.grasscutter.data.custom.QuestConfig;
import emu.grasscutter.data.custom.QuestConfigData.SubQuestConfigData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.enums.QuestState;
import emu.grasscutter.net.proto.QuestOuterClass.Quest;
import emu.grasscutter.server.packet.send.PacketQuestProgressUpdateNotify;
import emu.grasscutter.utils.Utils;

@Entity
public class GameQuest {
	@Transient private GameMainQuest mainQuest;
	@Transient private QuestConfig config;
	
	private int questId;
	private int mainQuestId;
	private QuestState state;
	
	private int startTime;
	private int acceptTime;
	private int finishTime;
	
	private int[] finishProgressList;
	private int[] failProgressList;
	
	@Deprecated // Morphia only. Do not use.
	public GameQuest() {}
	
	public GameQuest(GameMainQuest mainQuest, QuestConfig config) {
		this.mainQuest = mainQuest;
		this.questId = config.getId();
		this.mainQuestId = config.getMainQuest().getId();
		this.config = config;
		this.acceptTime = Utils.getCurrentSeconds();
		this.startTime = this.acceptTime;
		this.state = QuestState.QUEST_STATE_UNFINISHED;
		
		if (config.getSubQuest().getFinishCond() != null) {
			this.finishProgressList = new int[config.getSubQuest().getFinishCond().length];
		}
		
		if (config.getSubQuest().getFailCond() != null) {
			this.failProgressList = new int[config.getSubQuest().getFailCond().length];
		}
		
		this.mainQuest.getChildQuests().put(this.questId, this);
	}
	
	public GameMainQuest getMainQuest() {
		return mainQuest;
	}

	public void setMainQuest(GameMainQuest mainQuest) {
		this.mainQuest = mainQuest;
	}
	
	public Player getOwner() {
		return getMainQuest().getOwner();
	}

	public int getQuestId() {
		return questId;
	}

	public int getMainQuestId() {
		return mainQuestId;
	}

	public QuestConfig getConfig() {
		return config;
	}

	public void setConfig(QuestConfig config) {
		if (this.getQuestId() != config.getId()) return;
		this.config = config;
	}

	public QuestState getState() {
		return state;
	}

	public void setState(QuestState state) {
		this.state = state;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(int acceptTime) {
		this.acceptTime = acceptTime;
	}

	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}
	
	public int[] getFinishProgressList() {
		return finishProgressList;
	}
	
	public void setFinishProgress(int index, int value) {
		finishProgressList[index] = value;
	}

	public int[] getFailProgressList() {
		return failProgressList;
	}
	
	public void setFailProgress(int index, int value) {
		failProgressList[index] = value;
	}

	public void finish() {
		this.state = QuestState.QUEST_STATE_FINISHED;
		this.finishTime = Utils.getCurrentSeconds();
		
		if (this.getFinishProgressList() != null) {
			for (int i = 0 ; i < getFinishProgressList().length; i++) {
				getFinishProgressList()[i] = 1;
			}
		}
		
		this.getOwner().getSession().send(new PacketQuestProgressUpdateNotify(this));
		
		// Finish main quest if all child quests are done
		this.tryFinishMainQuest();
		this.save();
	}
	
	public boolean tryFinishMainQuest() {
		try {
			SubQuestConfigData subQuestData = getConfig().getMainQuest().getSubQuests()[getConfig().getMainQuest().getSubQuests().length - 1];
			
			if (subQuestData.getSubId() == this.getQuestId()) {
				getMainQuest().finish();
				return true;
			}
		} catch (Exception e) {
			
		}

		return false;
	}
	
	public void save() {
		getMainQuest().save();
	}
	
	public Quest toProto() {
		Quest.Builder proto = Quest.newBuilder()
				.setQuestId(this.getQuestId())
				.setState(this.getState().getValue())
				.setParentQuestId(this.getMainQuestId())
				.setStartTime(this.getStartTime())
				.setStartGameTime(438)
				.setAcceptTime(this.getAcceptTime());
		
		if (this.getFinishProgressList() != null) {
			for (int i : this.getFinishProgressList()) {
				proto.addFinishProgressList(i);
			}
		}
		
		if (this.getFailProgressList() != null) {
			for (int i : this.getFailProgressList()) {
				proto.addFailProgressList(i);
			}
		}
		
		return proto.build();
	}
}
