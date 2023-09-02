package emu.grasscutter.data.common;

import emu.grasscutter.data.excels.ProudSkillData;
import emu.grasscutter.game.ability.Ability;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.objects.*;
import java.util.*;
import lombok.*;

@Getter
public class DynamicFloat {
    public static DynamicFloat ZERO = new DynamicFloat(0f);
    public static DynamicFloat ONE = new DynamicFloat(1f);

    private List<StackOp> ops;
    private boolean dynamic = false;
    private float constant = 0f;

    public DynamicFloat(float constant) {
        this.constant = constant;
    }

    public DynamicFloat(String key) {
        this.dynamic = true;
        this.ops = List.of(new StackOp(key));
    }

    public DynamicFloat(boolean b) {
        this.dynamic = true;
        this.ops = List.of(new StackOp(String.valueOf(b)));
    }

    public DynamicFloat(List<StackOp> ops) {
        this.dynamic = true;
        this.ops = ops;
    }

    public String toString(boolean nextBoolean) {
        var key = String.valueOf(nextBoolean);
        this.ops = List.of(new StackOp(key));
        return ops.toString();
    }

    public float get() {
        return this.get(new Object2FloatArrayMap<>(), 0);
    }

    public float get(float defaultValue) {
        return this.get(new Object2FloatArrayMap<>(), defaultValue);
    }

    public float get(Ability ability, float defaultValue) {
        return this.get(ability.getAbilitySpecials(), defaultValue);
    }

    public float get(Ability ability) {
        return this.get(ability.getAbilitySpecials(), 0f);
    }

    public float get(Object2FloatMap<String> props, float defaultValue) {
        if (!this.dynamic) return constant;

        val fl = new FloatArrayList();
        for (var op : this.ops) {
            switch (op.op) {
                case CONSTANT -> fl.push(op.fValue);
                case KEY -> fl.push(props.getOrDefault(op.sValue, 0f) * (op.negative ? -1 : 1));
                case ADD -> fl.push(fl.popFloat() + fl.popFloat());
                case SUB -> fl.push(
                        -fl.popFloat() + fl.popFloat()); // [f0, f1, f2] -> [f0, f1-f2]  (opposite of RPN order)
                case MUL -> fl.push(fl.popFloat() * fl.popFloat());
                case DIV -> fl.push((1f / fl.popFloat()) * fl.popFloat()); // [f0, f1, f2] -> [f0, f1/f2]
                case NEXBOOLEAN -> fl.push(props.getOrDefault(Optional.of(op.bValue), 0f));
            }
        }

        try {
            return fl.popFloat(); // well-formed data will always have only one value left at this point
        } catch (NoSuchElementException e) {
            return defaultValue;
        }
    }

    public float get(ProudSkillData skill) {
        // Construct the map
        return get(skill.getParamListMap(), 0f);
    }

    public float get(ProudSkillData skill, float defaultValue) {
        // Construct the map
        return get(skill.getParamListMap(), defaultValue);
    }

    public static class StackOp {
        public Op op;

        public float fValue;
        public String sValue;
        public boolean bValue;
        public boolean negative = false;

        public StackOp(String s) {
            switch (s.toUpperCase()) {
                case "ADD" -> this.op = Op.ADD;
                case "SUB" -> this.op = Op.SUB;
                case "MUL" -> this.op = Op.MUL;
                case "DIV" -> this.op = Op.DIV;
                default -> {
                    if (s.startsWith("%")) {
                        s = s.substring(1);
                    } else if (s.startsWith("-%")) {
                        s = s.substring(2);
                        negative = true;
                    }

                    this.op = Op.KEY;
                    this.sValue = s;
                }
            }
        }

        public StackOp(boolean b) {
            this.op = Op.NEXBOOLEAN;
            this.bValue = Boolean.parseBoolean(String.valueOf(b));
        }

        public StackOp(float f) {
            this.op = Op.CONSTANT;
            this.fValue = f;
        }

        enum Op {
            CONSTANT,
            KEY,
            ADD,
            SUB,
            MUL,
            DIV,
            NEXBOOLEAN
        }
    }
}
