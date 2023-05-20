package emu.grasscutter.game.player;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Transient;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.quest.enums.QuestContent;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;

/** Tracks progress the player made in the world, like obtained items, seen characters and more */
@Entity
public class PlayerProgress {
    @Getter @Setter @Transient private Player player;

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

    public PlayerProgress() {
        this.questProgressCountMap = new Int2IntOpenHashMap();
        this.completedDungeons = new IntArrayList();
        this.itemHistory = new Int2ObjectOpenHashMap<>();
    }

    /**
     * Marks a dungeon as completed. Triggers the quest event.
     *
     * @param dungeonId The dungeon which was completed.
     */
    public void markDungeonAsComplete(int dungeonId) {
        if (this.getCompletedDungeons().contains(dungeonId)) return;

        // Mark the dungeon as completed.
        this.getCompletedDungeons().add(dungeonId);
        // Trigger the completion event.
        if (this.getPlayer() != null) {
            this.getPlayer()
                    .getQuestManager()
                    .queueEvent(QuestContent.QUEST_CONTENT_FINISH_DUNGEON, dungeonId);
        } else {
            Grasscutter.getLogger()
                    .warn("Unable to execute 'QUEST_CONTENT_FINISH_DUNGEON'. The player is null.");
        }

        Grasscutter.getLogger()
                .debug("Dungeon {} has been marked complete for {}.", dungeonId, this.getPlayer().getUid());
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

    public int resetCurrentProgress(int progressId) {
        return questProgressCountMap.merge(progressId,0,Integer::min);
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
