package emu.grasscutter.data.def;

import java.util.Arrays;
import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.quest.enums.LogicType;
import emu.grasscutter.game.quest.enums.QuestTrigger;

@ResourceType(name = "QuestExcelConfigData.json")
public class QuestData extends GameResource {
    private int SubId;
    private int MainId;
    private int Order;
    private long DescTextMapHash;

    private boolean FinishParent;
    private boolean IsRewind;

    private LogicType AcceptCondComb;
    private QuestCondition[] acceptConditons;
    private LogicType FinishCondComb;
    private QuestCondition[] finishConditons;
    private LogicType FailCondComb;
    private QuestCondition[] failConditons;
    private QuestExec[] beginExecs;
    private QuestExec[] finishExecs;
    private QuestExec[] failExecs;

    private List<QuestParam> AcceptCond;
    private List<QuestParam> FinishCond;
    private List<QuestParam> FailCond;
    private List<QuestExecParam> BeginExec;
    private List<QuestExecParam> FinishExec;
    private List<QuestExecParam> FailExec;

    public int getId() {
        return SubId;
    }

    public int getMainId() {
        return MainId;
    }

    public int getOrder() {
        return Order;
    }

    public long getDescTextMapHash() {
        return DescTextMapHash;
    }

    public boolean finishParent() {
        return FinishParent;
    }

    public boolean isRewind() {
        return IsRewind;
    }

    public LogicType getAcceptCondComb() {
        return AcceptCondComb;
    }

    public QuestCondition[] getAcceptCond() {
        return acceptConditons;
    }

    public LogicType getFinishCondComb() {
        return FinishCondComb;
    }

    public QuestCondition[] getFinishCond() {
        return finishConditons;
    }

    public LogicType getFailCondComb() {
        return FailCondComb;
    }

    public QuestCondition[] getFailCond() {
        return failConditons;
    }

    public void onLoad() {
        this.acceptConditons = AcceptCond.stream().filter(p -> p.Type != null).map(QuestCondition::new).toArray(QuestCondition[]::new);
        AcceptCond = null;
        this.finishConditons = FinishCond.stream().filter(p -> p.Type != null).map(QuestCondition::new).toArray(QuestCondition[]::new);
        FinishCond = null;
        this.failConditons = FailCond.stream().filter(p -> p.Type != null).map(QuestCondition::new).toArray(QuestCondition[]::new);
        FailCond = null;
        this.beginExecs=BeginExec.stream().filter(p->p.Type!=null).map(QuestExec::new).toArray(QuestExec[]::new);
        BeginExec=null;
        this.finishExecs=FinishExec.stream().filter(p->p.Type!=null).map(QuestExec::new).toArray(QuestExec[]::new);
        FinishExec=null;
        this.failExecs=FailExec.stream().filter(p->p.Type!=null).map(QuestExec::new).toArray(QuestExec[]::new);
        FailExec=null;
    }

    public class QuestParam {
        QuestTrigger Type;
        int[] Param;
        int Count;
        String ParamStr;
    }

    public class QuestExecParam {
        QuestTrigger Type;
        String[] Param;
    }

    public static class QuestCondition {
        private QuestTrigger type;
        private int[] param;
        private int count;
        private String paramStr;

        public QuestCondition(QuestParam param) {
            this.type = param.Type;
            this.param = param.Param;
            this.paramStr = param.ParamStr;
            this.count = param.Count;
        }

        public QuestTrigger getType() {
            return type;
        }

        public int[] getParam() {
            return param;
        }

        public int getCount() {
            return count;
        }

        public String getParamStr() {
            return paramStr;
        }
    }

    public static class QuestExec {
        private QuestTrigger type;
        private String[] param;

        public QuestExec(QuestExecParam param) {
            this.type = param.Type;
            this.param = param.Param;
        }

        public QuestTrigger getType() {
            return type;
        }

        public String[] getParam() {
            return param;
        }
    }
}
