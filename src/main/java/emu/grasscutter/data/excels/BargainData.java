package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import java.util.List;
import lombok.Getter;

@Getter
@ResourceType(name = "BargainExcelConfigData.json")
public final class BargainData extends GameResource {
    @Getter private int id;
    private int questId;

    private List<Integer> dialogId;

    /**
     * This is a list of 2 integers. The first integer is the minimum value of the bargain. The second
     * integer is the maximum value of the bargain.
     */
    private List<Integer> expectedValue;

    private int space;

    private List<Integer> successTalkId;
    private int failTalkId;
    private int moodNpcId;

    /**
     * This is a list of 2 integers. The first integer is the minimum value of the mood. The second
     * integer is the maximum value of the mood.
     */
    private List<Integer> randomMood;

    private int moodAlertLimit;
    private int moodLowLimit;
    private int singleFailMoodDeduction;

    private long moodLowLimitTextTextMapHash;
    private long titleTextTextMapHash;
    private long affordTextTextMapHash;
    private long storageTextTextMapHash;
    private long moodHintTextTextMapHash;
    private long moodDescTextTextMapHash;

    private List<Integer> singleFailTalkId;

    private boolean deleteItem;
    private int itemId;
}
