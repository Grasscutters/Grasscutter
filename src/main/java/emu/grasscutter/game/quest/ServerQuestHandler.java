package emu.grasscutter.game.quest;

import java.util.Set;

import org.reflections.Reflections;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.QuestData.QuestCondition;
import emu.grasscutter.game.quest.handlers.QuestBaseHandler;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

@SuppressWarnings("unchecked")
public class ServerQuestHandler {
	private final Int2ObjectMap<QuestBaseHandler> condHandlers;
	private final Int2ObjectMap<QuestBaseHandler> contHandlers;
	private final Int2ObjectMap<QuestBaseHandler> execHandlers;
	
	public ServerQuestHandler() {
		this.condHandlers = new Int2ObjectOpenHashMap<>(); 
		this.contHandlers = new Int2ObjectOpenHashMap<>();
		this.execHandlers = new Int2ObjectOpenHashMap<>();
		
		this.registerHandlers();
	}

	public void registerHandlers() {
		this.registerHandlers(this.condHandlers, "emu.grasscutter.game.quest.conditions");
		this.registerHandlers(this.contHandlers, "emu.grasscutter.game.quest.content");
		this.registerHandlers(this.execHandlers, "emu.grasscutter.game.quest.exec");
	}
	
	public void registerHandlers(Int2ObjectMap<QuestBaseHandler> map, String packageName) {
		Reflections reflections = new Reflections(packageName);
		Set<?> handlerClasses = reflections.getSubTypesOf(QuestBaseHandler.class);
		
		for (Object obj : handlerClasses) {
			this.registerPacketHandler(map, (Class<? extends QuestBaseHandler>) obj);
		}
	}

	public void registerPacketHandler(Int2ObjectMap<QuestBaseHandler> map, Class<? extends QuestBaseHandler> handlerClass) {
		try {
			QuestValue opcode = handlerClass.getAnnotation(QuestValue.class);

			if (opcode == null || opcode.value().getValue() <= 0) {
				return;
			}

			QuestBaseHandler packetHandler = (QuestBaseHandler) handlerClass.newInstance();

			map.put(opcode.value().getValue(), packetHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// TODO make cleaner
	
	public boolean triggerCondition(GameQuest quest, QuestCondition condition, int... params) {
		QuestBaseHandler handler = condHandlers.get(condition.getType().getValue());

		if (handler == null || quest.getData() == null) {
			return false;
		}
		
		return handler.execute(quest, condition, params);
	}
	
	public boolean triggerContent(GameQuest quest, QuestCondition condition, int... params) {
		QuestBaseHandler handler = contHandlers.get(condition.getType().getValue());

		if (handler == null || quest.getData() == null) {
			return false;
		}
		
		return handler.execute(quest, condition, params);
	}
	
	public boolean triggerExec(GameQuest quest, QuestCondition condition, int... params) {
		QuestBaseHandler handler = execHandlers.get(condition.getType().getValue());

		if (handler == null || quest.getData() == null) {
			return false;
		}
		
		return handler.execute(quest, condition, params);
	}
}
