package emu.grasscutter.game.quest;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.custom.QuestConfig;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.enums.QuestState;
import emu.grasscutter.server.packet.send.PacketFinishedParentQuestUpdateNotify;
import emu.grasscutter.server.packet.send.PacketQuestListUpdateNotify;
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
		QuestConfig questConfig = GameData.getQuestConfigs().get(questId);
		if (questConfig == null) {
			return null;
		}
		
		GameMainQuest mainQuest = getQuests().get(questConfig.getMainQuest().getId());
		
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
	
	public GameMainQuest addMainQuest(QuestConfig questConfig) {
		GameMainQuest mainQuest = new GameMainQuest(getPlayer(), questConfig.getMainQuest().getId());
		getQuests().put(mainQuest.getParentQuestId(), mainQuest);

		getPlayer().sendPacket(new PacketFinishedParentQuestUpdateNotify(mainQuest));
		
		return mainQuest;
	}
	
	public GameQuest addQuest(int questId) {
		QuestConfig questConfig = GameData.getQuestConfigs().get(questId);
		if (questConfig == null) {
			return null;
		}
		
		// Main quest
		GameMainQuest mainQuest = this.getMainQuestById(questConfig.getMainQuest().getId());
		
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
		getPlayer().sendPacket(new PacketServerCondMeetQuestListUpdateNotify(quest));
		getPlayer().sendPacket(new PacketQuestListUpdateNotify(quest));

		return quest;
	}

	public void loadFromDatabase() {
		List<GameMainQuest> quests = DatabaseHelper.getAllQuests(getPlayer());
		
		for (GameMainQuest mainQuest : quests) {
			mainQuest.setOwner(this.getPlayer());
			
			for (GameQuest quest : mainQuest.getChildQuests().values()) {
				quest.setMainQuest(mainQuest);
				quest.setConfig(GameData.getQuestConfigs().get(quest.getQuestId()));
			}
			
			this.getQuests().put(mainQuest.getParentQuestId(), mainQuest);
		}
	}
}
