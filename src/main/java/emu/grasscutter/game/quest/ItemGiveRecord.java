package emu.grasscutter.game.quest;

import dev.morphia.annotations.Entity;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.giving.GivingData.GiveMethod;
import emu.grasscutter.net.proto.GivingRecordOuterClass.GivingRecord;
import java.util.*;
import lombok.*;

@Data
@Entity
@Builder
public final class ItemGiveRecord {
    /**
     * Provides a builder for an item give record. Uses information from game resources.
     *
     * @param givingId The ID of the giving action.
     * @return A builder for an item give record.
     */
    public static ItemGiveRecord resolve(int givingId) {
        var givingData = GameData.getGivingDataMap().get(givingId);
        if (givingData == null)
            throw new RuntimeException("No giving data found for " + givingId + ".");

        var builder = ItemGiveRecord.builder().givingId(givingId).finished(false);

        // Create a map.
        var givenItems = new HashMap<Integer, Integer>();
        if (givingData.getGivingMethod() == GiveMethod.GIVING_METHOD_EXACT) {
            givingData.getExactItems().forEach(item -> givenItems.put(item.getItemId(), 0));
        } else {
            givingData
                    .getGivingGroupIds()
                    .forEach(
                            groupId -> {
                                var groupData = GameData.getGivingGroupDataMap().get((int) groupId);
                                if (groupData == null) return;

                                // Add all items in the group.
                                groupData.getItemIds().forEach(itemId -> givenItems.put(itemId, 0));
                                builder.groupId(groupId);
                            });
        }

        return builder.givenItems(givenItems).build();
    }

    private int givingId;
    private int configId;

    private int groupId;
    private int lastGroupId;

    private boolean finished;
    private Map<Integer, Integer> givenItems;

    /**
     * @return A serialized protobuf object.
     */
    public GivingRecord toProto() {
        return GivingRecord.newBuilder()
                .setGivingId(this.getGivingId())
                .setConfigId(this.getConfigId())
                .setGroupId(this.getGroupId())
                .setLastGroupId(this.getLastGroupId())
                .setIsFinished(this.isFinished())
                .setIsGadgetGiving(false)
                .putAllMaterialCntMap(this.getGivenItems())
                .build();
    }
}
