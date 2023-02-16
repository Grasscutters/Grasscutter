package emu.grasscutter.data.common;

import java.util.List;
import java.util.Optional;

import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.objects.Object2FloatArrayMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import lombok.val;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.ast.Str;

public class DynamicFloat {
    public static DynamicFloat ZERO = new DynamicFloat(0f);

    public static class StackOp {
        enum Op { CONSTANT, KEY, ADD, SUB, MUL, DIV, NEXBOOLEAN };
        public Op op;
        public float fValue;
        public String sValue;
        public boolean bValue;

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
        public StackOp(boolean b) {
            this.op = Op.NEXBOOLEAN;
            this.bValue = Boolean.parseBoolean(String.valueOf(b));
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

    public String toString(boolean nextBoolean) {
        String key = String.valueOf(nextBoolean);
        this.ops = List.of(new StackOp(key));
        return ops.toString();
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
                case NEXBOOLEAN ->  fl.push(props.getOrDefault(Optional.of(op.bValue), 0f));
            }
        }

        return fl.popFloat();  // well-formed data will always have only one value left at this point
    }
}
