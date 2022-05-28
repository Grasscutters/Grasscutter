package emu.grasscutter.data.excels;

import java.util.Arrays;
import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.quest.enums.LogicType;
import emu.grasscutter.game.quest.enums.QuestTrigger;

@ResourceType(name = "QuestExcelConfigData.json")
public class QuestData extends GameResource {
	private int subId;
	private int mainId;
	private int order;
	private long descTextMapHash;
	
	private boolean finishParent;
	private boolean isRewind;
	
	private LogicType acceptCondComb;
	private QuestCondition[] acceptConditons;
	private LogicType finishCondComb;
	private QuestCondition[] finishConditons;
	private LogicType failCondComb;
	private QuestCondition[] failConditons;
	
	private List<QuestParam> acceptCond;
	private List<QuestParam> finishCond;
	private List<QuestParam> failCond;
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

	public QuestCondition[] getAcceptCond() {
		return acceptConditons;
	}

	public LogicType getFinishCondComb() {
		return finishCondComb;
	}

	public QuestCondition[] getFinishCond() {
		return finishConditons;
	}

	public LogicType getFailCondComb() {
		return failCondComb;
	}

	public QuestCondition[] getFailCond() {
		return failConditons;
	}

	public void onLoad() {
		this.acceptConditons = acceptCond.stream().filter(p -> p._type != null).map(QuestCondition::new).toArray(QuestCondition[]::new);
		acceptCond = null;
		this.finishConditons = finishCond.stream().filter(p -> p._type != null).map(QuestCondition::new).toArray(QuestCondition[]::new);
		finishCond = null;
		this.failConditons = failCond.stream().filter(p -> p._type != null).map(QuestCondition::new).toArray(QuestCondition[]::new);
		failCond = null;
	}
	
	public class QuestParam {
		QuestTrigger _type;
		int[] _param;
		String _count;
	}
	
	public class QuestExecParam {
		QuestTrigger _type;
		String[] _param;
		String _count;
	}
	
	public static class QuestCondition {
		private QuestTrigger type;
		private int[] param;
		private String count;
		
		public QuestCondition(QuestParam param) {
			this.type = param._type;
			this.param = param._param;
		}
		
		public QuestTrigger getType() {
			return type;
		}
		
		public int[] getParam() {
			return param;
		}

		public String getCount() {
			return count;
		}
	}
}
