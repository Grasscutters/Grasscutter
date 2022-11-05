package emu.grasscutter.server.webapi.arguments;

import emu.grasscutter.server.webapi.tools.ParseTools;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class ArgumentValue {
    Object val;
    Object defaultVal;

    public ArgumentValue(Object val, Object defaultVal) {
        this.val = val;
        this.defaultVal = defaultVal;
    }

    @Nullable
    public Object getVal() {
        return val;
    }

    @Nullable
    public Object getDefaultVal() {
        return defaultVal;
    }

    @Nullable
    public Object getDefaultOrValue() {
        return defaultVal == null ? val : defaultVal;
    }

    @Nullable
    public Object getValueOrDefault() {
        return val == null ? defaultVal : val;
    }

    public ArgumentValue withDefault(Function<Object, Object> valueMapper) {
        defaultVal = valueMapper.apply(defaultVal);
        return this;
        //return new ArgumentValue(val, valueMapper.apply(defaultVal);
    }

    public ArgumentValue withDefault(Object value) {
        defaultVal = value;
        return this;
        //return new ArgumentValue(val, value);
    }


    @NotNull
    public ArgumentValue valueOrDefault() {

        if(isEmpty()) {
            throw new NullPointerException();
        }
        val = val == null ? defaultVal : val;
        return this;
        //return new ArgumentValue(val == null ? defaultVal : val, defaultVal);
    }

    @NotNull
    public ArgumentValue defaultOr(Object value) {
        if(value == null && defaultVal == null) {
            throw new NullPointerException();
        }

        val = val == null ? defaultVal : value;
        return this;
        //return new ArgumentValue(val == null ? defaultVal : value, defaultVal);
    }

    @NotNull
    public ArgumentValue valueOr(Object value) {
        if(val == null && value == null) {
            throw new NullPointerException();
        }
        val = val == null ? value : val;
        return this;

        //return new ArgumentValue(val == null ? value : val, defaultVal);
    }

    public boolean isDefaultValue() {
        return val == null;
    }

    public boolean isEmpty() {
        return val == null && defaultVal == null;
    }

    public ArgumentValue emptyOr(Object value) {
        if(isEmpty()) {
            return new ArgumentValue(value, null);
        }

        return this;
    }

    public byte getAsByte() {
        if(isEmpty()) {
            throw new NullPointerException();
        }
        var innerVal = getValueOrDefault();

        if(innerVal == null) {
            throw new NullPointerException();
        }

        if(innerVal instanceof Byte) {
            return (byte) innerVal;
        }
        var converted = ParseTools.tryParseByte(innerVal.toString());
        if(converted.isEmpty()) {
            throw new ClassCastException();
        }

        return converted.get();
    }

    public char getAsChar() {
        return (char) getAsShort();
    }

    public short getAsShort() {
        if(isEmpty()) {
            throw new NullPointerException();
        }
        var innerVal = getValueOrDefault();

        if(innerVal == null) {
            throw new NullPointerException();
        }

        if(innerVal instanceof Short) {
            return (short) innerVal;
        }

        var converted = ParseTools.tryParseShort(innerVal.toString());
        if(converted.isEmpty()) {
            throw new ClassCastException();
        }

        return converted.get();
    }

    public int getAsInt() {
        if(isEmpty()) {
            throw new NullPointerException();
        }
        var innerVal = getValueOrDefault();

        if(innerVal == null) {
            throw new NullPointerException();
        }


        if(innerVal instanceof Integer) {
            return (int) innerVal;
        }
        var converted = ParseTools.tryParseInteger(innerVal.toString());
        if(converted.isEmpty()) {
            throw new ClassCastException();
        }

        return converted.get();
    }

    public long getAsLong() {
        if(isEmpty()) {
            throw new NullPointerException();
        }
        var innerVal = getValueOrDefault();

        if(innerVal == null) {
            throw new NullPointerException();
        }


        if(innerVal instanceof Long) {
            return (long) innerVal;
        }

        var converted = ParseTools.tryParseLong(innerVal.toString());
        if(converted.isEmpty()) {
            throw new ClassCastException();
        }

        return converted.get();
    }

    public float getAsFloat() {
        if(isEmpty()) {
            throw new NullPointerException();
        }
        var innerVal = getValueOrDefault();

        if(innerVal == null) {
            throw new NullPointerException();
        }

        if(innerVal instanceof Float) {
            return (float) innerVal;
        }

        var converted = ParseTools.tryParseFloat(innerVal.toString());
        if(converted.isEmpty()) {
            throw new ClassCastException();
        }

        return converted.get();
    }

    public double getAsDouble() {
        if(isEmpty()) {
            throw new NullPointerException();
        }
        var innerVal = getValueOrDefault();

        if(innerVal == null) {
            throw new NullPointerException();
        }


        if(innerVal instanceof Double) {
            return (double) innerVal;
        }

        var converted = ParseTools.tryParseDouble(innerVal.toString());
        if(converted.isEmpty()) {
            throw new ClassCastException();
        }

        return converted.get();
    }

    public boolean getAsBoolean() {
        if(isEmpty()) {
            throw new NullPointerException();
        }

        var innerVal = getValueOrDefault();

        if(innerVal == null) {
            throw new NullPointerException();
        }

        var converted = ParseTools.tryParseBoolean(innerVal.toString());

        if(innerVal instanceof Boolean) {
            return (boolean) innerVal;
        }

        if(converted.isEmpty()) {
            throw new ClassCastException();
        }

        return converted.get();
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public <T> T getAs(Class<T> clazz) {
        if(isEmpty()) {
            throw new NullPointerException();
        }

        var innerVal = getValueOrDefault();

        if(innerVal == null) {
            throw new NullPointerException();
        }

        if (innerVal.getClass() != clazz) {
            throw new ClassCastException();
        }

        return (T) innerVal;

    }

    public static ArgumentValue emptyValue = new ArgumentValue(null, null);
}
