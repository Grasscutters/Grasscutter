package emu.grasscutter.game.player;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Transient;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.quest.enums.QuestContent;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Map;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;

/** Tracks progress the player made in the world, like obtained items, seen characters and more */
@Entity
public class PlayerProgress {
    @Getter @Transient private final Player player;

    @Getter private Map<Integer, ItemEntry> itemHistory;

    /*
     * A list of dungeon IDs which have been completed.
     * This only applies to one-time dungeons.
     */
    @Getter private IntArrayList completedDungeons;

    // keep track of EXEC_ADD_QUEST_PROGRESS count, will be used in CONTENT_ADD_QUEST_PROGRESS
    // not sure where to put this, this should be saved to DB but not to individual quest, since
    // it will be hard to loop and compare
    private Map<Integer, Integer> questProgressCountMap;

    public PlayerProgress(Player player) {
        this.player = player;

        this.questProgressCountMap = new Int2IntOpenHashMap();
        this.completedDungeons = new IntArrayList();
        this.itemHistory = new Int2ObjectOpenHashMap<>();
    }

    /**
     * Marks a dungeon as completed.
     * Triggers the quest event.
     *
     * @param dungeonId The dungeon which was completed.
     */
    public void markDungeonAsComplete(int dungeonId) {
        if (this.getCompletedDungeons().contains(dungeonId))
            return;

        // Mark the dungeon as completed.
        this.getCompletedDungeons().add(dungeonId);
        // Trigger the completion event.
        this.getPlayer().getQuestManager().queueEvent(
            QuestContent.QUEST_CONTENT_FINISH_DUNGEON, dungeonId
        );

        Grasscutter.getLogger().debug("Dungeon {} has been marked complete for {}.",
            dungeonId, this.getPlayer().getUid());
    }

    public boolean hasPlayerObtainedItemHistorically(int itemId) {
        return itemHistory.containsKey(itemId);
    }

    public int addToItemHistory(int itemId, int count) {
        val itemEntry = itemHistory.computeIfAbsent(itemId, (key) -> new ItemEntry(itemId));
        return itemEntry.addToObtainedCount(count);
    }

    public int getCurrentProgress(int progressId) {
        return questProgressCountMap.getOrDefault(progressId, -1);
    }

    public int addToCurrentProgress(int progressId, int count) {
        return questProgressCountMap.merge(progressId, count, Integer::sum);
    }

    @Entity
    @NoArgsConstructor
    public static class ItemEntry {
        @Getter private int itemId;
        @Getter @Setter private int obtainedCount;

        ItemEntry(int itemId) {
            this.itemId = itemId;
        }

        int addToObtainedCount(int amount) {
            this.obtainedCount += amount;
            return this.obtainedCount;
        }
    }
}
