package emu.grasscutter.data.binout;

import dev.morphia.annotations.Entity;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.quest.enums.QuestType;
import java.util.*;
import lombok.Data;
import lombok.Getter;

public class MainQuestData {
    @Getter private int id;
    private int ICLLDPJFIMA;
    @Getter private int series;
    @Getter private QuestType type;

    @Getter private long titleTextMapHash;
    @Getter private int[] suggestTrackMainQuestList;
    @Getter private int[] rewardIdList;

    @Getter private SubQuestData[] subQuests;
    @Getter private List<TalkData> talks;
    private long[] preloadLuaList;

    public void onLoad() {
        if (this.talks == null) this.talks = new ArrayList<>();
        if (this.subQuests == null) this.subQuests = new SubQuestData[0];

        this.talks = this.talks.stream().filter(Objects::nonNull).toList();
        // Apply talk data to the quest talk map.
        this.talks.forEach(talkData -> GameData.getQuestTalkMap().put(talkData.getId(), this.getId()));
        // Apply additional sub-quest data to sub-quests.
        Arrays.stream(this.subQuests)
                .forEach(
                        quest -> {
                            var questData = GameData.getQuestDataMap().get(quest.getSubId());
                            if (questData != null) questData.applyFrom(quest);
                        });
    }

    @Data
    public static class SubQuestData {
        private int subId;
        private int order;
        private boolean isMpBlock;
        private boolean isRewind, finishParent;
    }

    @Data
    @Entity
    public static class TalkData {
        private int id;
        private String heroTalk;

        public TalkData() {}

        public TalkData(int id, String heroTalk) {
            this.id = id;
            this.heroTalk = heroTalk;
        }
    }
}
