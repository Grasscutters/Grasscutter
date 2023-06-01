package emu.grasscutter.data.excels;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.talk.TalkExec;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ResourceType(name = "TalkExcelConfigData.json")
@EqualsAndHashCode(callSuper = false)
@Data
public final class TalkConfigData extends GameResource {
    @SerializedName(
            value = "id",
            alternate = {"_id"})
    private int id;

    @SerializedName(
            value = "finishExec",
            alternate = {"_finishExec"})
    private List<TalkExecParam> finishExec;

    @SerializedName(
            value = "questId",
            alternate = {"_questId"})
    private int questId;

    @SerializedName(
            value = "npcId",
            alternate = {"_npcId"})
    private List<Integer> npcId;

    @Override
    public void onLoad() {
        this.finishExec =
                this.finishExec == null
                        ? List.of()
                        : this.finishExec.stream().filter(x -> x.getType() != null).toList();
    }

    @Data
    public static class TalkExecParam {
        @SerializedName(
                value = "type",
                alternate = {"_type"})
        private TalkExec type;

        @SerializedName(
                value = "param",
                alternate = {"_param"})
        private String[] param;
    }
}
