package emu.grasscutter.game.trigger;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.trigger.enums.Trigger;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class TriggerManager {
    private final Player player;

    private Collection<ITriggerListener> listeners;
    public TriggerManager(Player player) {
        this.player = player;
        this.listeners = new HashSet<>();
    }

    public void addAchievementTriggerListener(ITriggerListener listener) {
        listeners.add(listener);
    }

    public void removeAchievementTriggerListener(ITriggerListener listener) {
        if (listeners == null)
            return;
        listeners.remove(listener);
    }

    public void triggerEvent(Trigger trigger, int id){
        TriggerEvent event = new TriggerEvent(this, trigger, id);
        notifyListeners(event);
    }

    public void triggerEvent(Trigger trigger, float amount){
        TriggerEvent event = new TriggerEvent(this, trigger, amount);
        notifyListeners(event);
    }

    private void notifyListeners(TriggerEvent event) {
        Iterator<ITriggerListener> iter = listeners.iterator();
        while (iter.hasNext()) {
            ITriggerListener listener = (ITriggerListener) iter.next();
            boolean remove = listener.triggerEvent(event);
            if(remove){
                iter.remove();
            }
        }
    }
}
