package emu.grasscutter.game.quest;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import com.google.protobuf.ByteString;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.QuestData;
import emu.grasscutter.data.def.QuestData.QuestCondition;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.game.quest.enums.LogicType;
import emu.grasscutter.game.quest.enums.QuestState;
import emu.grasscutter.net.proto.WindSeedClientNotifyOuterClass;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.FileUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import static emu.grasscutter.Configuration.SCRIPT;

public class QuestManager {
	private final Player player;
	private final Int2ObjectMap<GameMainQuest> quests;
	
	public QuestManager(Player player) {
		this.player = player;
		this.quests = new Int2ObjectOpenHashMap<>();
	}

	public Player getPlayer() {
		return player;
	}

	public Int2ObjectMap<GameMainQuest> getQuests() {
		return quests;
	}
	
	public GameMainQuest getMainQuestById(int mainQuestId) {
		return getQuests().get(mainQuestId);
	}
	
	public GameQuest getQuestById(int questId) {
		QuestData questConfig = GameData.getQuestDataMap().get(questId);
		if (questConfig == null) {
			return null;
		}
		
		GameMainQuest mainQuest = getQuests().get(questConfig.getMainId());
		
		if (mainQuest == null) {
			return null;
		}
		
		return mainQuest.getChildQuests().get(questId);
	}
	
	public void forEachQuest(Consumer<GameQuest> callback) {
		for (GameMainQuest mainQuest : getQuests().values()) {
			for (GameQuest quest : mainQuest.getChildQuests().values()) {
				callback.accept(quest);
			}
		}
	}

	public void forEachMainQuest(Consumer<GameMainQuest> callback) {
		for (GameMainQuest mainQuest : getQuests().values()) {
			callback.accept(mainQuest);
		}
	}
	
	// TODO
	public void forEachActiveQuest(Consumer<GameQuest> callback) {
		for (GameMainQuest mainQuest : getQuests().values()) {
			for (GameQuest quest : mainQuest.getChildQuests().values()) {
				if (quest.getState() != QuestState.QUEST_STATE_FINISHED) {
					callback.accept(quest);
				}
			}
		}
	}
	
	public GameMainQuest addMainQuest(QuestData questConfig) {
		GameMainQuest mainQuest = new GameMainQuest(getPlayer(), questConfig.getMainId());
		getQuests().put(mainQuest.getParentQuestId(), mainQuest);

		getPlayer().sendPacket(new PacketFinishedParentQuestUpdateNotify(mainQuest));
		
		return mainQuest;
	}
	
	public GameQuest addQuest(int questId) {
		QuestData questConfig = GameData.getQuestDataMap().get(questId);
		if (questConfig == null) {
			return null;
		}
		
		// Main quest
		GameMainQuest mainQuest = this.getMainQuestById(questConfig.getMainId());

		// Create main quest if it doesnt exist
		if (mainQuest == null) {

			mainQuest = addMainQuest(questConfig);
		}
		// Sub quest
		GameQuest quest = mainQuest.getChildQuestById(questId);

		if (quest != null) {
			return null;
		}

		// Create
		quest = new GameQuest(mainQuest, questConfig);
		for (QuestData.QuestExec beginExec : questConfig.getBeginExecs()) {
			getPlayer().getServer().getQuestHandler().triggerExec(quest,beginExec,beginExec.getParam());
		}
		// Save main quest
		mainQuest.save();

		// Send packet
		getPlayer().sendPacket(new PacketServerCondMeetQuestListUpdateNotify(quest));
		getPlayer().sendPacket(new PacketQuestListUpdateNotify(quest));

		return quest;
	}
	
	public void triggerEvent(QuestTrigger condType, int... params) {
		Set<GameQuest> changedQuests = new HashSet<>();

		this.forEachActiveQuest(quest -> {
			QuestData data = quest.getData();
			int index=0;
			for (int i = 0; i < data.getFinishCond().length; i++) {
				int count = data.getFinishCond()[i].getCount();
				if(count==0){
					index++;
					count=1;
				}else{
					index+=count;
				}
				if (quest.getFinishProgressList() == null) {
					continue;
				}
				if(count==1){
					if(quest.getFinishProgressList()[index-1]==1){
						continue;
					}
				}
				else {
					boolean isContinue=true;
					for(int t=index-count;t<index;t++){
						if(quest.getFinishProgressList()[t]==0){
							isContinue=false;
							break;
						}
					}
					if(isContinue){
						continue;
					}

				}
				
				QuestCondition condition = data.getFinishCond()[i];
				
				if (condition.getType() != condType) {
					continue;
				}
				
				boolean result = getPlayer().getServer().getQuestHandler().triggerContent(quest, condition, params);
				
				if (result) {
					if(count==1) {
						quest.getFinishProgressList()[index-1] = 1;
					}
					else{
						for(int i1=index-count;i<index;i++){
							if(quest.getFinishProgressList()[i1]==0){
								quest.getFinishProgressList()[i1] = 1;
								break;
							}
						}
					}
					changedQuests.add(quest);
				}
			}
		});
		
		for (GameQuest quest : changedQuests) {
			LogicType logicType = quest.getData().getFailCondComb();
			int[] progress = quest.getFinishProgressList();
			
			// Handle logical comb
			boolean finish = LogicType.calculate(logicType, progress,true);

			// Finish
			if (finish) {
				quest.finish();
			} else {
				getPlayer().sendPacket(new PacketQuestProgressUpdateNotify(quest));
				quest.save();
			}
		}
	}

	public void triggerEvent(QuestTrigger condType, String... params) {
		Set<GameQuest> changedQuests = new HashSet<>();

		this.forEachActiveQuest(quest -> {
			QuestData data = quest.getData();
			int index=0;
			for (int i = 0; i < data.getFinishCond().length; i++) {
				int count = data.getFinishCond()[i].getCount();
				if(count==0){
					index++;
					count=1;
				}else{
					index+=count;
				}
				if (quest.getFinishProgressList() == null ) {
					continue;
				}
				if(count==1){
					if(quest.getFinishProgressList()[index-1]==1){
						continue;
					}
				}
				else {
					boolean isContinue=true;
					for(int t=index-count;t<index;t++){
						if(quest.getFinishProgressList()[t]==0){
							isContinue=false;
							break;
						}
					}
					if(isContinue){
						continue;
					}

				}

				QuestCondition condition = data.getFinishCond()[i];

				if (condition.getType() != condType) {
					continue;
				}

				boolean result = getPlayer().getServer().getQuestHandler().triggerContent(quest, condition, params);

				if (result) {
					if(count==1) {
						quest.getFinishProgressList()[index-1] = 1;
					}
					else{
						for(int i1=index-count;i1<index;i1++){
							if(quest.getFinishProgressList()[i1]==0){
								quest.getFinishProgressList()[i1] = 1;
								break;
							}
						}
					}

					changedQuests.add(quest);
				}
			}
		});

		for (GameQuest quest : changedQuests) {
			LogicType logicType = quest.getData().getFailCondComb();
			int[] progress = quest.getFinishProgressList();

			// Handle logical comb
		    boolean finish = LogicType.calculate(logicType, progress,true);

			// Finish
			if (finish) {
				quest.finish();
			} else {
				getPlayer().sendPacket(new PacketQuestProgressUpdateNotify(quest));
				quest.save();
			}
		}
	}
	public void loadFromDatabase() {
		List<GameMainQuest> quests = DatabaseHelper.getAllQuests(getPlayer());
		
		for (GameMainQuest mainQuest : quests) {
			mainQuest.setOwner(this.getPlayer());
			
			for (GameQuest quest : mainQuest.getChildQuests().values()) {
				quest.setMainQuest(mainQuest);
				quest.setConfig(GameData.getQuestDataMap().get(quest.getQuestId()));
			}
			
			this.getQuests().put(mainQuest.getParentQuestId(), mainQuest);
		}
	}
}
