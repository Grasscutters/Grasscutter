package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.quest.enums.LogicType;
import emu.grasscutter.game.quest.enums.QuestTrigger;

import java.util.List;

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
        return this.subId;
    }

    public int getMainId() {
        return this.mainId;
    }

    public int getOrder() {
        return this.order;
    }

    public long getDescTextMapHash() {
        return this.descTextMapHash;
    }

    public boolean finishParent() {
        return this.finishParent;
    }

    public boolean isRewind() {
        return this.isRewind;
    }

    public LogicType getAcceptCondComb() {
        return this.acceptCondComb;
    }

    public QuestCondition[] getAcceptCond() {
        return this.acceptConditons;
    }

    public LogicType getFinishCondComb() {
        return this.finishCondComb;
    }

    public QuestCondition[] getFinishCond() {
        return this.finishConditons;
    }

    public LogicType getFailCondComb() {
        return this.failCondComb;
    }

    public QuestCondition[] getFailCond() {
        return this.failConditons;
    }

    public void onLoad() {
        this.acceptConditons = this.acceptCond.stream().filter(p -> p._type != null).map(QuestCondition::new).toArray(QuestCondition[]::new);
        this.acceptCond = null;
        this.finishConditons = this.finishCond.stream().filter(p -> p._type != null).map(QuestCondition::new).toArray(QuestCondition[]::new);
        this.finishCond = null;
        this.failConditons = this.failCond.stream().filter(p -> p._type != null).map(QuestCondition::new).toArray(QuestCondition[]::new);
        this.failCond = null;
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
        private final QuestTrigger type;
        private final int[] param;
        private String count;

        public QuestCondition(QuestParam param) {
            this.type = param._type;
            this.param = param._param;
        }

        public QuestTrigger getType() {
            return this.type;
        }

        public int[] getParam() {
            return this.param;
        }

        public String getCount() {
            return this.count;
        }
    }
}
