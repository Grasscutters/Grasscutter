package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@ResourceType(name = "OpenStateConfigData.json", loadPriority = ResourceType.LoadPriority.HIGHEST)
@Getter
public class OpenStateData extends GameResource {
    private int id;
    private boolean defaultState;
    private boolean allowClientOpen;
    private int systemOpenUiId;
    private List<OpenStateCond> cond;

    @Getter
    public static class OpenStateCond {
        private String condType;
        private int param;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }
}
