package emu.grasscutter.data.excels.codex;

import emu.grasscutter.data.*;
import lombok.Getter;

@Getter
@ResourceType(name = {"MaterialCodexExcelConfigData.json"})
public class CodexMaterialData extends GameResource {
    private int Id;
    private int materialId;
    private int sortOrder;

    @Override
    public void onLoad() {
        GameData.getCodexMaterialDataIdMap().put(this.getMaterialId(), this);
    }
}
