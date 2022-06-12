package emu.grasscutter.scripts.engine;

import net.sandius.rembulan.Conversions;
import net.sandius.rembulan.Table;
import net.sandius.rembulan.TableFactory;
import net.sandius.rembulan.util.TraversableHashMap;

import javax.script.Bindings;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LuaTable extends Table implements Bindings, Iterable<Map.Entry<Object, Object>> {
    private final TraversableHashMap<Object, Object> tableValues = new TraversableHashMap<>();
    private LuaTable globalBinding;
    private static final TableFactory FACTORY_INSTANCE = new LuaTable.Factory();

    public LuaTable() {
    }
    public LuaTable(LuaTable global) {
        this.globalBinding = global;
    }

    public LuaTable getGlobalBinding() {
        return globalBinding;
    }

    public void setGlobalBinding(LuaTable globalBinding) {
        this.globalBinding = globalBinding;
    }

    public static TableFactory factory() {
        return FACTORY_INSTANCE;
    }

    public Object rawget(Object key) {
        key = Conversions.normaliseKey(key);
        if(key == null){
            return null;
        }
        if(this.tableValues.containsKey(key)){
            return this.tableValues.get(key);
        }
        if(globalBinding != null){
            return globalBinding.rawget(key);
        }
        return null;
    }

    public void rawset(Object key, Object value) {
        key = Conversions.normaliseKey(key);
        if (key == null) {
            throw new IllegalArgumentException("table index is nil");
        } else if (key instanceof Double && Double.isNaN((Double)key)) {
            throw new IllegalArgumentException("table index is NaN");
        } else {
            value = Conversions.canonicalRepresentationOf(value);
            if (value == null) {
                this.tableValues.remove(key);
            } else {
                this.tableValues.put(key, value);
            }

            this.updateBasetableModes(key, value);
        }
    }

    public Object initialKey() {
        return this.tableValues.getFirstKey();
    }

    public Object successorKeyOf(Object key) {
        try {
            return this.tableValues.getSuccessorOf(key);
        } catch (NullPointerException | NoSuchElementException var3) {
            throw new IllegalArgumentException("invalid key to 'next'", var3);
        }
    }

    protected void setMode(boolean weakKeys, boolean weakValues) {
    }

    @Override
    public Iterator<Map.Entry<Object, Object>> iterator() {
        return tableValues.entrySet().iterator();
    }

    @Override
    public void forEach(Consumer<? super Entry<Object, Object>> action) {
        tableValues.entrySet().forEach(action);
    }

    @Override
    public Spliterator<Map.Entry<Object, Object>> spliterator() {
        return tableValues.entrySet().spliterator();
    }

    public Stream<Object> stream(){
        return tableValues.values().stream();
    }

    @Override
    public Object put(String name, Object value) {
        this.rawset(name, value);
        return get(name);
    }

    @Override
    public void putAll(Map<? extends String, ?> toMerge) {
        toMerge.forEach(this::put);
    }

    @Override
    public boolean containsKey(Object key) {
        key = Conversions.normaliseKey(key);
        return this.tableValues.containsKey(key);
    }

    @Override
    public Object get(Object key) {
        key = Conversions.normaliseKey(key);
        return this.tableValues.get(key);
    }

    @Override
    public Object remove(Object key) {
        key = Conversions.normaliseKey(key);
        return this.tableValues.remove(key);
    }

    @Override
    public int size() {
        return this.tableValues.size();
    }

    @Override
    public boolean isEmpty() {
        return this.tableValues.isEmpty();
    }

    @Override
    public boolean containsValue(Object value) {
        return this.tableValues.containsValue(value);
    }

    @Override
    public void clear() {
        this.tableValues.clear();
    }

    @Override
    public Set<String> keySet() {
        return tableValues.keySet().stream()
                .map(Object::toString)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Object> values() {
        return tableValues.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return tableValues.entrySet().stream()
                .collect(Collectors.toMap(x -> x.getKey().toString(), Entry::getValue))
                .entrySet();
    }

    public void addAll(LuaTable bindings) {
        bindings.tableValues.forEach(this::rawset);
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",");
        for(var meta : tableValues.entrySet()){
            sj.add(meta.getKey() + ":" + meta.getValue());
        }
        return "{" + sj + "}";
    }

    static class Factory implements TableFactory {
        Factory() {
        }

        public Table newTable() {
            return this.newTable(0, 0);
        }

        public Table newTable(int array, int hash) {
            return new LuaTable();
        }
    }
}
