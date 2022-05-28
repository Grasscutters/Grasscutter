package emu.grasscutter.game.quest;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Transient;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.MainQuestData;
import emu.grasscutter.data.binout.MainQuestData.SubQuestData;
import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.data.excels.QuestData.QuestCondition;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.enums.LogicType;
import emu.grasscutter.game.quest.enums.QuestState;
import emu.grasscutter.net.proto.QuestOuterClass.Quest;
import emu.grasscutter.server.packet.send.PacketCodexDataUpdateNotify;
import emu.grasscutter.server.packet.send.PacketQuestListUpdateNotify;
import emu.grasscutter.server.packet.send.PacketQuestProgressUpdateNotify;
import emu.grasscutter.utils.Utils;

@Entity
public class GameQuest {
	@Transient private GameMainQuest mainQuest;
	@Transient private QuestData questData;
	
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
	
	public GameQuest(GameMainQuest mainQuest, QuestData questData) {
		this.mainQuest = mainQuest;
		this.questId = questData.getId();
		this.mainQuestId = questData.getMainId();
		this.questData = questData;
		this.acceptTime = Utils.getCurrentSeconds();
		this.startTime = this.acceptTime;
		this.state = QuestState.QUEST_STATE_UNFINISHED;
		
		if (questData.getFinishCond()!= null) {
			this.finishProgressList = new int[questData.getFinishCond().length];
		}
		
		if (questData.getFailCond() != null) {
			this.failProgressList = new int[questData.getFailCond().length];
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

	public QuestData getData() {
		return questData;
	}

	public void setConfig(QuestData config) {
		if (this.getQuestId() != config.getId()) return;
		this.questData = config;
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
		this.getOwner().getSession().send(new PacketQuestListUpdateNotify(this));
		
		if (this.getData().finishParent()) {
			// This quest finishes the questline - the main quest will also save the quest to db so we dont have to call save() here
			this.getMainQuest().finish();
		} else {
			// Try and accept other quests if possible
			this.tryAcceptQuestLine();
			this.save();
		}
	}
	
	public boolean tryAcceptQuestLine() {
		try {
			MainQuestData questConfig = GameData.getMainQuestDataMap().get(this.getMainQuestId());
			
			for (SubQuestData subQuest : questConfig.getSubQuests()) {
				GameQuest quest = getMainQuest().getChildQuestById(subQuest.getSubId());
				
				if (quest == null) {
					QuestData questData = GameData.getQuestDataMap().get(subQuest.getSubId());
					
					if (questData == null || questData.getAcceptCond() == null) {
						continue;
					}
					
					int[] accept = new int[questData.getAcceptCond().length];
							
					// TODO
					for (int i = 0; i < questData.getAcceptCond().length; i++) {
						QuestCondition condition = questData.getAcceptCond()[i];
						boolean result = getOwner().getServer().getQuestHandler().triggerCondition(this, condition, condition.getParam());
						
						accept[i] = result ? 1 : 0;
					}
					
					boolean shouldAccept = LogicType.calculate(questData.getAcceptCondComb(), accept);
					
					if (shouldAccept) {
						this.getOwner().getQuestManager().addQuest(questData.getId());
					}
				}
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
