package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import emu.grasscutter.game.props.ServerBuffType;
import lombok.Getter;

@ResourceType(name = "BuffExcelConfigData.json")
@Getter
public class BuffData extends GameResource {
    private int groupId;
    private int serverBuffId;
    private float time;
    private boolean isPersistent;
    private ServerBuffType serverBuffType;
    private String abilityName;
    private String modifierName;

    @Override
    public int getId() {
        return this.serverBuffId;
    }

    public void onLoad() {
        this.serverBuffType =
                this.serverBuffType != null ? this.serverBuffType : ServerBuffType.SERVER_BUFF_NONE;
    }
}
