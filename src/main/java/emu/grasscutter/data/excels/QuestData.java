package emu.grasscutter.data.excels;

import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.quest.enums.LogicType;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@ResourceType(name = "QuestExcelConfigData.json")
@Getter
@ToString
public class QuestData extends GameResource {
	private int subId;
	private int mainId;
	private int order;
	private long descTextMapHash;

	private boolean finishParent;
	private boolean isRewind;

	private LogicType acceptCondComb;
	private LogicType finishCondComb;
	private LogicType failCondComb;

	private List<QuestCondition> acceptCond;
	private List<QuestCondition> finishCond;
	private List<QuestCondition> failCond;
	private List<QuestExecParam> beginExec;
	private List<QuestExecParam> finishExec;
	private List<QuestExecParam> failExec;

	public int getId() {
		return subId;
	}

	public int getMainId() {
		return mainId;
	}

	public int getOrder() {
		return order;
	}

	public long getDescTextMapHash() {
		return descTextMapHash;
	}

	public boolean finishParent() {
		return finishParent;
	}

	public boolean isRewind() {
		return isRewind;
	}

	public LogicType getAcceptCondComb() {
		return acceptCondComb;
	}

	public List<QuestCondition> getAcceptCond() {
		return acceptCond;
	}

	public LogicType getFinishCondComb() {
		return finishCondComb;
	}

	public List<QuestCondition> getFinishCond() {
		return finishCond;
	}

	public LogicType getFailCondComb() {
		return failCondComb;
	}

	public List<QuestCondition> getFailCond() {
		return failCond;
	}

	public void onLoad() {
		this.acceptCond = acceptCond.stream().filter(p -> p.type != null).toList();
		this.finishCond = finishCond.stream().filter(p -> p.type != null).toList();
		this.failCond = failCond.stream().filter(p -> p.type != null).toList();

        this.beginExec = beginExec.stream().filter(p -> p.type != null).toList();
        this.finishExec = finishExec.stream().filter(p -> p.type != null).toList();
        this.failExec = failExec.stream().filter(p -> p.type != null).toList();
	}

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
	public class QuestExecParam {
        @SerializedName("_type")
		QuestTrigger type;
        @SerializedName("_param")
		String[] param;
        @SerializedName("_count")
		String count;
	}

    @Data
	public static class QuestCondition {
        @SerializedName("_type")
		private QuestTrigger type;
        @SerializedName("_param")
		private int[] param;
        @SerializedName("_param_str")
		private String paramStr;
        @SerializedName("_count")
		private String count;

	}
}
