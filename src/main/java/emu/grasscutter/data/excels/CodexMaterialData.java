package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = {"MaterialCodexExcelConfigData.json"})
public class CodexMaterialData extends GameResource {
    private int Id;
    private int materialId;
    private int sortOrder;

    public int getSortOrder() {
        return this.sortOrder;
    }

    public int getMaterialId() {
        return this.materialId;
    }

    public int getId() {
        return this.Id;
    }

    @Override
    public void onLoad() {
        GameData.getCodexMaterialDataIdMap().put(this.getMaterialId(), this);
    }
}
