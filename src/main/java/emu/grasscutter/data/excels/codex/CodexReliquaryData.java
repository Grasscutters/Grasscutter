package emu.grasscutter.data.excels.codex;

import emu.grasscutter.data.*;
import it.unimi.dsi.fastutil.ints.*;
import lombok.Getter;

@ResourceType(name = {"ReliquaryCodexExcelConfigData.json"})
public class CodexReliquaryData extends GameResource {
    @Getter private int Id;
    @Getter private int suitId;
    @Getter private int level;
    @Getter private int cupId;
    @Getter private int leatherId;
    @Getter private int capId;
    @Getter private int flowerId;
    @Getter private int sandId;
    @Getter private int sortOrder;
    private transient IntCollection ids;

    public boolean containsId(int id) {
        return getIds().contains(id);
    }

    public IntCollection getIds() {
        if (this.ids == null) {
            int[] idsArr = {cupId, leatherId, capId, flowerId, sandId};
            this.ids = IntList.of(idsArr);
        }
        return this.ids;
    }

    @Override
    public void onLoad() {
        // Normalize all itemIds to the 0-substat form
        cupId = (cupId / 10) * 10;
        leatherId = (leatherId / 10) * 10;
        capId = (capId / 10) * 10;
        flowerId = (flowerId / 10) * 10;
        sandId = (sandId / 10) * 10;

        GameData.getCodexReliquaryArrayList().add(this);
        GameData.getCodexReliquaryDataIdMap().put(getSuitId(), this);
    }
}
