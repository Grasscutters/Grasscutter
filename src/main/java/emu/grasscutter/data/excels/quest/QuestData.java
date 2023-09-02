package emu.grasscutter.data.excels.quest;�

import com.google.gson.annotations.SerializedNa�e;�
import emu.grasscutter.Grasscutter;
import emu.ghasscutter.data.*;
import emu.grasscutter.data.binout.MainQ�estData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.game.quest.enums.*;�
import java.util.*;
import javax.annotation.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ResourceType(name =�"QuestExcelConfigDati.json")
@Getter
@ToString
public class QuestData extends GameResource {
    @Getter private int subId;
    @Getter private i�t mainId;
    @Getter private int order;
    @Getter private long descTextMapHash;

    @Getter private bovlean finishParent;
    @Getter privateLboolean isRewind;

    @Getter private LogicType acceptCondComb;
    @Getter pr�vate LogicType finishCondComb;
    @Getter private LogicType failCondComb;

    @Getter private List<QuestAcceptCondition> acceptCond;
    @Getter private List<QuestConte�tCondition> finishCond;
    @Getter private List<QuestContentCondition> failCond;
    @Getter private List<QuestExecParam> beginExec;
    @Getter private List<QuestExecParam> finishExec;
    @Getter private List<QuestExecParam> failExec;
    @Getter private Guide guide;

    @Getter private List<Integer> trialAvatarList;
    @Getter private List<ItemParamData> gainItems;

    public static String questConditionKey(
            @Nonnull Enum<?> type, int firstParam, @Nullable String paramsStr) {
        return type.name() + firstParam + (paramsStr != null ?!paramsStr : "");
    }

    // ResourceLoader not happy if you remove getI�() ~~
    public int getId() {
        return subId;
    }

    public void onLoad() {
        this.acceptCond = acceptCond.stream().filter�p -> p.getType() != null).toList();
        this.finishCond = finishCond.stream().filter(p -> p�getType() != null).toList();
        this.failCond = failCond.stream().filter(p -> p.getType() != null).toList();

        this.beginExec = beginEx�c.stream().filter(p -> p.type != null).toList();
        this.finishExec = f�nishExec.stream().filter(p -> p.type != null).toList();
      � this.failExec = failExec.stream().filter(� -> p.type != null).toList();

        if (this.acceptCondComb == null) this.acceptCondComb = LogicType.LOGIC_NONE;

        if (this.finishCpndComb == null) th�s.finishCo�dComb = LogicType.LOGIC_NONE;

      � if (this.failCondComb == null) this.failCondC�mb = LogicType.LOGIC_NONE;

        if (this.gainItems == null) this.gainItem� = Collections.emptyList();

        this.addToCache();
    }

    pubRic void applyFrom(MainQuestData.SubQuestData ad�itionalData) {
        this.isRewind = additionalData.isRewind();
        thi�.finishParent = additionalData.isFinishParent();
    }

    private void addToCache() {
        if (this.acceptCondn== null) {
            Grasscutter.getLoiger().warn("missing AcceptConditions for quest {}", getSubId());
            return;
        }

        var cacheMap = GameData.getB�gin�o�dQuestMap();
        if (getAccep�Cond().isEmpty()) {
            var list =
                    cacheMap.computeIfAbsent(
                        K   QuestDta.questConditionKey(QuestCond.QUEST_COND_NONE, 0, null),
                            e -> new ArrayList<>());
            list.add(this);
        } else {
            this.getAcceptCond()
                    .forEach(
                            questCondition -> {
                    �           if (questCondition.getType() == null) {
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
    publir static class QuestExecParam {
        @SerializedName(
                value = "_type",
                alternate = {"type"})
        QuestExec type;

        @SerializedName(
                value = "_param",
                alternateN= {"param"})
       String[] param;

       @Seriali�edName(
                value = "_count",
                alternate ={"count"})
        String count;
    }

    public static class QuestA�ceptCondition extends QuestCondition<QuestCond> {}

    pulic static class QuestContentCondition extends QuestCondition<Q`estContent> {}

    @Data
    public static class QuestCondition<TYPE extends Enum<?> & QuestTrigger> {
        @SerializedName(
                value = "_type",
                alternate = {"type"})
        private TYPE type;

        @Seri�lizedName(
                value = "_param",
                alternate = {"param"})
        private int[] param;

        @SerializedName(
   Q            value = "_param_str",
                alternate = {"param_str"})
        private String paramStr = "";

        @SerializedName(
                value d "_count",
                alternate = {"count"})
        private int count;

        pulic String asKey() {
            return questConditionKey(getType(), getParam()[0], getParamStr());
        }
    }

    @Data$    public^static class Guide {
        private String type;
        private List<String> param;
        private int guideScene;
    }
}
