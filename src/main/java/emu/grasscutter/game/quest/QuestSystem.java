package emu.grasscutter.game.quest;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.QuestData.QuestCondition;
import emu.grasscutter.data.excels.QuestData.QuestExecParam;
import emu.grasscutter.game.quest.handlers.QuestBaseHandler;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;
import emu.grasscutter.server.game.BaseGameSystem;
import emu.grasscutter.server.game.GameServer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.reflections.Reflections;

@SuppressWarnings("unchecked")
public class QuestSystem extends BaseGameSystem {
    private final Int2ObjectMap<QuestBaseHandler> condHandlers;
    private final Int2ObjectMap<QuestBaseHandler> contHandlers;
    private final Int2ObjectMap<QuestExecHandler> execHandlers;

    public QuestSystem(GameServer server) {
        super(server);

        this.condHandlers = new Int2ObjectOpenHashMap<>();
        this.contHandlers = new Int2ObjectOpenHashMap<>();
        this.execHandlers = new Int2ObjectOpenHashMap<>();

        this.registerHandlers();
    }

    public void registerHandlers() {
        this.registerHandlers(this.condHandlers, "emu.grasscutter.game.quest.conditions", QuestBaseHandler.class);
        this.registerHandlers(this.contHandlers, "emu.grasscutter.game.quest.content", QuestBaseHandler.class);
        this.registerHandlers(this.execHandlers, "emu.grasscutter.game.quest.exec", QuestExecHandler.class);
    }

    public <T> void registerHandlers(Int2ObjectMap<T> map, String packageName, Class<T> clazz) {
        Reflections reflections = new Reflections(packageName);
        var handlerClasses = reflections.getSubTypesOf(clazz);

        for (var obj : handlerClasses) {
            this.registerPacketHandler(map, obj);
        }
    }

    public <T> void registerPacketHandler(Int2ObjectMap<T> map, Class<? extends T> handlerClass) {
        try {
            QuestValue opcode = handlerClass.getAnnotation(QuestValue.class);

            if (opcode == null || opcode.value().getValue() <= 0) {
                return;
            }

            map.put(opcode.value().getValue(), handlerClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO make cleaner

    public boolean triggerCondition(GameQuest quest, QuestCondition condition, String paramStr, int... params) {
        QuestBaseHandler handler = condHandlers.get(condition.getType().getValue());

        if (handler == null || quest.getQuestData() == null) {
            Grasscutter.getLogger().debug("Could not trigger condition {} at {}", condition.getType().getValue(), quest.getQuestData());
            return false;
        }

        return handler.execute(quest, condition, paramStr, params);
    }

    public boolean triggerContent(GameQuest quest, QuestCondition condition, String paramStr, int... params) {
        QuestBaseHandler handler = contHandlers.get(condition.getType().getValue());

        if (handler == null || quest.getQuestData() == null) {
            Grasscutter.getLogger().debug("Could not trigger content {} at {}", condition.getType().getValue(), quest.getQuestData());
            return false;
        }

        return handler.execute(quest, condition, paramStr, params);
    }

    public boolean triggerExec(GameQuest quest, QuestExecParam execParam, String... params) {
        QuestExecHandler handler = execHandlers.get(execParam.getType().getValue());

        if (handler == null || quest.getQuestData() == null) {
            Grasscutter.getLogger().debug("Could not trigger exec {} at {}", execParam.getType().getValue(), quest.getQuestData());
            return false;
        }

        return handler.execute(quest, execParam, params);
    }
}
