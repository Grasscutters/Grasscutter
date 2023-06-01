package emu.grasscutter.game.quest;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.data.excels.quest.QuestData.QuestAcceptCondition;
import emu.grasscutter.data.excels.quest.QuestData.QuestContentCondition;
import emu.grasscutter.data.excels.quest.QuestData.QuestExecParam;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.conditions.BaseCondition;
import emu.grasscutter.game.quest.content.BaseContent;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;
import emu.grasscutter.server.game.BaseGameSystem;
import emu.grasscutter.server.game.GameServer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.reflections.Reflections;

@SuppressWarnings("unchecked")
public class QuestSystem extends BaseGameSystem {
    private final Int2ObjectMap<BaseCondition> condHandlers;
    private final Int2ObjectMap<BaseContent> contHandlers;
    private final Int2ObjectMap<QuestExecHandler> execHandlers;

    public QuestSystem(GameServer server) {
        super(server);

        this.condHandlers = new Int2ObjectOpenHashMap<>();
        this.contHandlers = new Int2ObjectOpenHashMap<>();
        this.execHandlers = new Int2ObjectOpenHashMap<>();

        this.registerHandlers();
    }

    public void registerHandlers() {
        this.registerHandlers(
                this.condHandlers, "emu.grasscutter.game.quest.conditions", BaseCondition.class);
        this.registerHandlers(
                this.contHandlers, "emu.grasscutter.game.quest.content", BaseContent.class);
        this.registerHandlers(
                this.execHandlers, "emu.grasscutter.game.quest.exec", QuestExecHandler.class);
    }

    public <T> void registerHandlers(Int2ObjectMap<T> map, String packageName, Class<T> clazz) {
        Reflections reflections = new Reflections(packageName);
        var handlerClasses = reflections.getSubTypesOf(clazz);

        for (var obj : handlerClasses) {
            this.registerHandler(map, obj);
        }
    }

    public <T> void registerHandler(Int2ObjectMap<T> map, Class<? extends T> handlerClass) {
        try {
            int value = 0;
            if (handlerClass.isAnnotationPresent(QuestValueExec.class)) {
                QuestValueExec opcode = handlerClass.getAnnotation(QuestValueExec.class);
                value = opcode.value().getValue();
            } else if (handlerClass.isAnnotationPresent(QuestValueContent.class)) {
                QuestValueContent opcode = handlerClass.getAnnotation(QuestValueContent.class);
                value = opcode.value().getValue();
            } else if (handlerClass.isAnnotationPresent(QuestValueCond.class)) {
                QuestValueCond opcode = handlerClass.getAnnotation(QuestValueCond.class);
                value = opcode.value().getValue();
            } else {
                return;
            }

            if (value <= 0) {
                return;
            }

            map.put(value, handlerClass.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO make cleaner

    public boolean triggerCondition(
            Player owner,
            QuestData questData,
            QuestAcceptCondition condition,
            String paramStr,
            int... params) {
        BaseCondition handler = condHandlers.get(condition.getType().getValue());

        if (handler == null || questData == null) {
            Grasscutter.getLogger()
                    .debug("Could not trigger condition {} at {}", condition.getType().getValue(), questData);
            return false;
        }

        return handler.execute(owner, questData, condition, paramStr, params);
    }

    public boolean triggerContent(
            GameQuest quest, QuestContentCondition condition, String paramStr, int... params) {
        BaseContent handler = contHandlers.get(condition.getType().getValue());

        if (handler == null || quest.getQuestData() == null) {
            Grasscutter.getLogger()
                    .debug(
                            "Could not trigger content {} at {}",
                            condition.getType().getValue(),
                            quest.getQuestData());
            return false;
        }

        return handler.execute(quest, condition, paramStr, params);
    }

    public void triggerExec(GameQuest quest, QuestExecParam execParam, String... params) {
        QuestExecHandler handler = execHandlers.get(execParam.getType().getValue());

        if (handler == null || quest.getQuestData() == null) {
            Grasscutter.getLogger()
                    .debug(
                            "Could not trigger exec {} at {}",
                            execParam.getType().getValue(),
                            quest.getQuestData());
            return;
        }

        QuestManager.eventExecutor.submit(
                () -> {
                    if (!handler.execute(quest, execParam, params)) {
                        Grasscutter.getLogger()
                                .debug(
                                        "Execute trigger failed for {} at {}.",
                                        execParam.getType().name(),
                                        quest.getQuestData());
                    }
                });
    }
}
