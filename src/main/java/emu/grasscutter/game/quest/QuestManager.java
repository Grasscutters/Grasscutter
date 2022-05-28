package emu.grasscutter.game.quest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.data.excels.QuestData.QuestCondition;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.game.quest.enums.LogicType;
import emu.grasscutter.game.quest.enums.QuestState;
import emu.grasscutter.server.packet.send.PacketFinishedParentQuestUpdateNotify;
import emu.grasscutter.server.packet.send.PacketQuestListUpdateNotify;
import emu.grasscutter.server.packet.send.PacketQuestProgressUpdateNotify;
import emu.grasscutter.server.packet.send.PacketServerCondMeetQuestListUpdateNotify;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

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

		// Save main quest
		mainQuest.save();

		// Send packet
		getPlayer().sendPacket(new PacketQuestListUpdateNotify(quest));

		return quest;
	}
	
	public void triggerEvent(QuestTrigger condType, int... params) {
		Set<GameQuest> changedQuests = new HashSet<>();
		
		this.forEachActiveQuest(quest -> {
			QuestData data = quest.getData();
			
			for (int i = 0; i < data.getFinishCond().length; i++) {
				if (quest.getFinishProgressList() == null || quest.getFinishProgressList()[i] == 1) {
					continue;
				}
				
				QuestCondition condition = data.getFinishCond()[i];
				
				if (condition.getType() != condType) {
					continue;
				}
				
				boolean result = getPlayer().getServer().getQuestHandler().triggerContent(quest, condition, params);
				
				if (result) {
					quest.getFinishProgressList()[i] = 1;
					
					changedQuests.add(quest);
				}
			}
		});
		
		for (GameQuest quest : changedQuests) {
			LogicType logicType = quest.getData().getFailCondComb();
			int[] progress = quest.getFinishProgressList();
			
			// Handle logical comb
			boolean finish = LogicType.calculate(logicType, progress);

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
