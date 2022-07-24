package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ResourceType(name = "OpenStateConfigData.json", loadPriority = ResourceType.LoadPriority.HIGHEST)
public class OpenStateData extends GameResource {
    public static String PLAYER_LEVEL_UNLOCK_COND = "OPEN_STATE_COND_PLAYER_LEVEL";
    public static String QUEST_UNLOCK_COND = "OPEN_STATE_COND_QUEST";
    public static String OFFERING_LEVEL_UNLOCK_COND = "OPEN_STATE_OFFERING_LEVEL";
    public static String REPUTATION_LEVEL_UNLOCK_COND = "OPEN_STATE_CITY_REPUTATION_LEVEL";
    public static String PARENT_QUEST_UNLOCK_COND = "OPEN_STATE_COND_PARENT_QUEST";

    private int id;
    @Getter private boolean defaultState;
    @Getter private boolean allowClientOpen;
    @Getter private int systemOpenUiId;
    @Getter private List<OpenStateCond> cond;

    public static class OpenStateCond {
        @Getter private String condType;
        @Getter private int param;
        @Getter private int param2;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void onLoad() {
        // Add this open state to the global list.
        GameData.getOpenStateList().add(this);

        // Clean up cond.
        List<OpenStateCond> cleanedConds = new ArrayList<>();
        for (var c : this.cond) {
            if (c.getCondType() != null) {
                cleanedConds.add(c);
            }
        }

        this.cond = cleanedConds;
    }
}
