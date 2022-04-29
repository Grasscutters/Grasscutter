package emu.grasscutter.game.scenescript.runtime.scriptlib;

import lombok.Data;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GetGroupVariableValueImpl extends TwoArgFunction {
    // int GetGroupVariableValue(context: LuaValue, groupId: int)
    public LuaValue call(LuaValue context, LuaValue groupId) {
        GroupVariableValue result = new GroupVariableValue();
        // TODO: implement
        return result.toLuaTable();
    }

    @Data
    public class GroupVariableValue {
        List<Integer> crucibleTimer;
        List<Integer> crucibleTimerInfo;
        int groupId;
        int gadgetCrucible;
        int gadgetPrepare;
        int stageBonusSuite;
        int bonusTime;
        int stageGroupWater;
        int stageGroupFire;
        int stageGroupElectric;
        int stageGroupIce;
        int stageGroupBonus;
        int timerExtraGroup;
        int monsterBonusGroup;
        List<MonsterBonusSuite> monsterBonusSuites;

        public GroupVariableValue() {
        }

        public GroupVariableValue(LuaTable table) {
            var crucibleTimerTable = ((LuaTable) table.get("crucible_timer"));
            this.crucibleTimer = Arrays.stream(crucibleTimerTable.keys()).map(LuaValue::toint).toList();
            var crucibleTimerInfoTable = ((LuaTable) table.get("crucible_timer_info"));
            this.crucibleTimerInfo = Arrays.stream(crucibleTimerInfoTable.keys()).map(LuaValue::toint).toList();
            this.groupId = table.get("group_id").toint();
            this.gadgetCrucible = table.get("gadget_crucible").toint();
            this.gadgetPrepare = table.get("gadget_prepare").toint();
            this.stageBonusSuite = table.get("stage_bonus_suite").toint();
            this.bonusTime = table.get("bonus_time").toint();
            this.stageGroupWater = table.get("stage_group_Water").toint();
            this.stageGroupFire = table.get("stage_group_Fire").toint();
            this.stageGroupElectric = table.get("stage_group_Electric").toint();
            this.stageGroupIce = table.get("stage_group_Ice").toint();
            this.stageGroupBonus = table.get("stage_group_bonus").toint();
            this.timerExtraGroup = table.get("timer_extra_group").toint();
            this.monsterBonusGroup = table.get("monster_bonus_group").toint();
            var monsterBonusSuiteTable = ((LuaTable) table.get("monster_bonus_suite"));
            for (LuaValue key : monsterBonusSuiteTable.keys()) {
                this.monsterBonusSuites.add(new MonsterBonusSuite((LuaTable) monsterBonusSuiteTable.get(key)));
            }
        }

        public LuaTable toLuaTable() {
            LuaTable table = new LuaTable();
            LuaTable crucibleTimerTable = new LuaTable();
            for (int i = 0; i < crucibleTimer.size(); i++) {
                crucibleTimerTable.set(i + 1, LuaValue.valueOf(crucibleTimer.get(i)));
            }
            table.set("crucible_timer", crucibleTimerTable);
            LuaTable crucibleTimerInfoTable = new LuaTable();
            for (int i = 0; i < crucibleTimerInfo.size(); i++) {
                crucibleTimerInfoTable.set(i + 1, LuaValue.valueOf(crucibleTimerInfo.get(i)));
            }
            table.set("crucible_timer_info", crucibleTimerInfoTable);
            table.set("group_id", groupId);
            table.set("gadget_crucible", gadgetCrucible);
            table.set("gadget_prepare", gadgetPrepare);
            table.set("stage_bonus_suite", stageBonusSuite);
            table.set("bonus_time", bonusTime);
            table.set("stage_group_Water", stageGroupWater);
            table.set("stage_group_Fire", stageGroupFire);
            table.set("stage_group_Electric", stageGroupElectric);
            table.set("stage_group_Ice", stageGroupIce);
            table.set("stage_group_bonus", stageGroupBonus);
            table.set("timer_extra_group", timerExtraGroup);
            table.set("monster_bonus_group", monsterBonusGroup);
            LuaTable monsterBonusSuiteTable = new LuaTable();
            for (int i = 0; i < monsterBonusSuites.size(); i++) {
                monsterBonusSuiteTable.set(i + 1, monsterBonusSuites.get(i).toLuaTable());
            }
            table.set("monster_bonus_suite", monsterBonusSuiteTable);
            return table;
        }

        @Data
        public class MonsterBonusSuite {
            String name;
            List<Integer> suite;

            public MonsterBonusSuite(LuaTable table) {
                this.name = table.get("name").toString();
                var suiteTable = ((LuaTable) table.get("suite"));
                suite = Arrays.stream(suiteTable.keys()).map(LuaValue::toint).toList();
            }

            LuaTable toLuaTable() {
                LuaTable table = new LuaTable();
                table.set("name", name);
                LuaTable suiteTable = new LuaTable();
                AtomicInteger i = new AtomicInteger(1);
                suite.forEach(val -> suiteTable.set(i.getAndIncrement(), LuaValue.valueOf(val)));
                return table;
            }

        }

    }
}