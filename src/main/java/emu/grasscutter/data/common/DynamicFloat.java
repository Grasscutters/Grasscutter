package emu.grasscutter.data.common;

import java.util.List;

import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.objects.Object2FloatArrayMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import lombok.val;

public class DynamicFloat {
    public static DynamicFloat ZERO = new DynamicFloat(0f);

    public static class StackOp {
        enum Op {CONSTANT, KEY, ADD, SUB, MUL, DIV};
        public Op op;
        public float fValue;
        public String sValue;

        public StackOp(String s) {
            switch (s.toUpperCase()) {
                case "ADD" -> this.op = Op.ADD;
                case "SUB" -> this.op = Op.SUB;
                case "MUL" -> this.op = Op.MUL;
                case "DIV" -> this.op = Op.DIV;
                default -> {
                    this.op = Op.KEY;
                    this.sValue = s;
                }
            }
        }

        public StackOp(float f) {
            this.op = Op.CONSTANT;
            this.fValue = f;
        }
    }
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

    public DynamicFloat(List<StackOp> ops) {
        this.dynamic = true;
        this.ops = ops;
    }

    public float get() {
        return this.get(new Object2FloatArrayMap<String>());
    }

    public float get(Object2FloatMap<String> props) {
        if (!dynamic)
            return constant;

        val fl = new FloatArrayList();
        for (var op : this.ops) {
            switch (op.op) {
                case CONSTANT -> fl.push(op.fValue);
                case KEY -> fl.push(props.getOrDefault(op.sValue, 0f));
                case ADD -> fl.push(fl.popFloat() + fl.popFloat());
                case SUB -> fl.push(-fl.popFloat() + fl.popFloat());  // [f0, f1, f2] -> [f0, f1-f2]  (opposite of RPN order)
                case MUL -> fl.push(fl.popFloat() * fl.popFloat());
                case DIV -> fl.push((1f/fl.popFloat()) * fl.popFloat());  // [f0, f1, f2] -> [f0, f1/f2]
            }
        }
        return fl.popFloat();  // well-formed data will always have only one value left at this point
    }
}
