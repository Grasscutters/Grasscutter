package emu.grasscutter.data.def;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = {"QuestCodexExcelConfigData.json"})
public class CodexQuestData extends GameResource {
    private int Id;
    private int ParentQuestId;
    private int ChapterId;
    private int SortOrder;
    private boolean IsDisuse;

    public int getParentQuestId() {
        return ParentQuestId;
    }

    public int getId() {
        return Id;
    }

    public int getChapterId() {
        return ChapterId;
    }

    public int getSortOrder() {
        return SortOrder;
    }

    public boolean getIsDisuse() {
        return IsDisuse;
    }

    @Override
    public void onLoad() {
        if(!this.getIsDisuse()) {
            GameData.getCodexQuestDataIdMap().put(this.getParentQuestId(), this);
        }
    }
}
