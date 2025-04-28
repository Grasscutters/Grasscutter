package emu.grasscutter.data.excels.quest;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.*;
import emu.grasscutter.data.binout.MainQuestData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.game.quest.enums.*;
import java.util.*;
import javax.annotation.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@ResourceType(name = "QuestExcelConfigData.json")
@ToString
public class QuestData extends GameResource {
    private int subId;
    private int mainId;
    private int order;
    private long descTextMapHash;

    private boolean finishParent;
    private boolean isRewind;

    private LogicType acceptCondComb;
    private LogicType finishCondComb;
    private LogicType failCondComb;

    private List<QuestAcceptCondition> acceptCond;
    private List<QuestContentCondition> finishCond;
    private List<QuestContentCondition> failCond;
    private List<QuestExecParam> beginExec;
    private List<QuestExecParam> finishExec;
    private List<QuestExecParam> failExec;
    private Guide guide;

    private List<Integer> trialAvatarList;
    private List<ItemParamData> gainItems;

    public static String questConditionKey(
            @Nonnull Enum<?> type, int firstParam, @Nullable String paramsStr) {
        return type.name() + firstParam + (paramsStr != null ? paramsStr : "");
    }

    // ResourceLoader not happy if you remove getId() ~~
    public int getId() {
        return subId;
    }

    public void onLoad() {
        this.acceptCond = acceptCond.stream().filter(p -> p.getType() != null).toList();
        this.finishCond = finishCond.stream().filter(p -> p.getType() != null).toList();
        this.failCond = failCond.stream().filter(p -> p.getType() != null).toList();

        this.beginExec = beginExec.stream().filter(p -> p.type != null).toList();
        this.finishExec = finishExec.stream().filter(p -> p.type != null).toList();
        this.failExec = failExec.stream().filter(p -> p.type != null).toList();

        if (this.acceptCondComb == null) this.acceptCondComb = LogicType.LOGIC_NONE;

        if (this.finishCondComb == null) this.finishCondComb = LogicType.LOGIC_NONE;

        if (this.failCondComb == null) this.failCondComb = LogicType.LOGIC_NONE;

        if (this.gainItems == null) this.gainItems = Collections.emptyList();

        this.addToCache();
    }

    public void applyFrom(MainQuestData.SubQuestData additionalData) {
        this.isRewind = additionalData.isRewind();
        this.finishParent = additionalData.isFinishParent();
    }

    private void addToCache() {
        if (this.acceptCond == null) {
            Grasscutter.getLogger().warn("missing AcceptConditions for quest {}", getSubId());
            return;
        }

        var cacheMap = GameData.getBeginCondQuestMap();
        if (getAcceptCond().isEmpty()) {
            var list =
                    cacheMap.computeIfAbsent(
                            QuestData.questConditionKey(QuestCond.QUEST_COND_NONE, 0, null),
                            e -> new ArrayList<>());
            list.add(this);
        } else {
            this.getAcceptCond()
                    .forEach(
                            questCondition -> {
                                if (questCondition.getType() == null) {
                                    Grasscutter.getLogger().warn("null accept type for quest {}", getSubId());
                                    return;
                                }

                                var key = questCondition.asKey();
                                var list = cacheMap.computeIfAbsent(key, e -> new ArrayList<>());
                                list.add(this);
                            });
        }
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class QuestExecParam {
        @SerializedName(
                value = "_type",
                alternate = {"type"})
        QuestExec type;

        @SerializedName(
                value = "_param",
                alternate = {"param"})
        String[] param;

        @SerializedName(
                value = "_count",
                alternate = {"count"})
        String count;
    }

    public static class QuestAcceptCondition extends QuestCondition<QuestCond> {}

    public static class QuestContentCondition extends QuestCondition<QuestContent> {}

    @Data
    public static class QuestCondition<TYPE extends Enum<?> & QuestTrigger> {
        @SerializedName(
                value = "_type",
                alternate = {"type"})
        private TYPE type;

        @SerializedName(
                value = "_param",
                alternate = {"param"})
        private int[] param;

        @SerializedName(
                value = "_param_str",
                alternate = {"param_str"})
        private String paramStr = "";

        @SerializedName(
                value = "_count",
                alternate = {"count"})
        private int count;

        public String asKey() {
            return questConditionKey(getType(), getParam()[0], getParamStr());
        }
    }

    @Data
    public static class Guide {
        private String type;
        private List<String> param;
        private int guideScene;
    }
}
