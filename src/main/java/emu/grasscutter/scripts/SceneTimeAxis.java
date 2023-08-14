package emu.grasscutter.scripts;

import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.ScriptArgs;
import java.util.*;
import lombok.*;

@Getter
@RequiredArgsConstructor
public final class SceneTimeAxis {
    private final Timer timer = new Timer();

    private final SceneScriptManager handle;
    private final int groupId;

    private final String identifier;
    private final int delay;
    private final boolean loop;

    /** Schedules the task to run. */
    public void start() {
        if (this.loop) {
            this.timer.scheduleAtFixedRate(new Task(), this.delay, this.delay);
        } else {
            this.timer.schedule(new Task(), this.delay);
        }
    }

    /** Terminates a repeating task. */
    public void stop() {
        this.timer.cancel();
    }

    final class Task extends TimerTask {
        @Override
        public void run() {
            // Invoke script event.
            SceneTimeAxis.this.handle.callEvent(
                    new ScriptArgs(SceneTimeAxis.this.groupId, EventType.EVENT_TIME_AXIS_PASS)
                            .setEventSource(SceneTimeAxis.this.identifier));
        }
    }
}
