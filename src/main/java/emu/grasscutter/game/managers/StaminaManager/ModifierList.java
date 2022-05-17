package emu.grasscutter.game.managers.StaminaManager;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class ModifierList {

    private HashMap<String, HashMap<String, Float>> modifiers; // source -> targetLable -> modifier
    private HashMap<String, Float> modifierCalResults;
    private float base;

    public ModifierList() {
        this.modifiers = new HashMap<String, HashMap<String, Float>>();
        this.modifierCalResults = new HashMap<String, Float>();
        this.base = 1;
    }

    private class HandleTimeLimitedModifier extends TimerTask {
        String source;

        public HandleTimeLimitedModifier(String source) {
            super();
            source = this.source;
        }

        public void run() {
            remove(source);
        }
    }

    public int calculateInt(InnerModifiable<Integer> obj) {
        String lable = obj.getLable();
        if (modifierCalResults.containsKey(lable)) {
            float modifier = modifierCalResults.get(obj.getLable());
            int value = obj.getValue();
            return (int) ((base + modifier) * value);
        }
        return obj.getValue();
    }

    public float calculateFloat(InnerModifiable<Float> obj) {
        String lable = obj.getLable();
        if (modifierCalResults.containsKey(lable)) {
            float modifier = modifierCalResults.get(obj.getLable());
            float value = obj.getValue();
            return (float) ((base + modifier) * value);
        }
        return obj.getValue();
    }

    private void update() {
        for (String key : modifierCalResults.keySet()) {
            modifierCalResults.put(key, 0f);
        }
        for (HashMap<String, Float> target2Modifier : modifiers.values()) {
            for (String key : target2Modifier.keySet()) {
                Float extraValue = target2Modifier.get(key);
                Float value = modifierCalResults.get(key);
                modifierCalResults.put(key, value + extraValue);
            }
        }
    }

    public void add(String source, String[] targets, Float extraValue) {
        HashMap<String, Float> target2Modifier;
        if (modifiers.containsKey(source)) {
            target2Modifier = modifiers.get(source);
        } else {
            target2Modifier = new HashMap<String, Float>();
            modifiers.put(source, target2Modifier);
        }
        for (String target : targets) {
            target2Modifier.put(target, extraValue);
            Float value = 0f;
            if (modifierCalResults.containsKey(target)) {
                value = modifierCalResults.get(target);
            }
            modifierCalResults.put(target, value + extraValue);
        }
    }

    public void add(String source, String[] targets, Float extraValue, long time) {
        add(source, targets, extraValue, time);
        Timer timer = new Timer();
        timer.schedule(new HandleTimeLimitedModifier(source), time);
    }

    public void remove(String source) {
        HashMap<String, Float> target2Modifier = modifiers.get(source);
        modifiers.remove(source);
        for (String key : target2Modifier.keySet()) {
            Float extraValue = target2Modifier.get(key);
            Float value = modifierCalResults.get(key);
            modifierCalResults.put(key, value - extraValue);
        }
    }
}
