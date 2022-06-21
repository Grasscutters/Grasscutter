package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = {"QuestCodexExcelConfigData.json"})
public class CodexQuestData extends GameResource {
    private int Id;
    private int parentQuestId;
    private int chapterId;
    private int sortOrder;
    private boolean isDisuse;

    public int getParentQuestId() {
        return this.parentQuestId;
    }

    public int getId() {
        return this.Id;
    }

    public int getChapterId() {
        return this.chapterId;
    }

    public int getSortOrder() {
        return this.sortOrder;
    }

    public boolean getIsDisuse() {
        return this.isDisuse;
    }

    @Override
    public void onLoad() {
        if (!this.getIsDisuse()) {
            GameData.getCodexQuestDataIdMap().put(this.getParentQuestId(), this);
        }
    }
}
