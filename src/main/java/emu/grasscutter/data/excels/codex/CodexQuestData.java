package emu.grasscutter.data.excels.codex;

import emu.grasscutter.data.*;
import lombok.Getter;

@ResourceType(name = {"QuestCodexExcelConfigData.json"})
public class CodexQuestData extends GameResource {
    @Getter private int Id;
    @Getter private int parentQuestId;
    @Getter private int chapterId;
    @Getter private int sortOrder;
    private boolean isDisuse;

    public boolean getIsDisuse() {
        return isDisuse;
    }

    @Override
    public void onLoad() {
        if (!this.getIsDisuse()) {
            GameData.getCodexQuestDataIdMap().put(this.getParentQuestId(), this);
        }
    }
}
