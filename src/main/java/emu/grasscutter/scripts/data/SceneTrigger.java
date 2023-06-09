package emu.grasscutter.scripts.data;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
// todo find way to deserialize from lua with final fields, maybe with the help of Builder?
public final class SceneTrigger {
    private String name;
    private int config_id;
    private int event;
    private int trigger_count = 1;
    private String source;
    private String condition;
    private String action;
    private String tag;

    public transient SceneGroup currentGroup;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SceneTrigger sceneTrigger) {
            return this.name.equals(sceneTrigger.name);
        } else return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return (currentGroup.id + name).hashCode();
    }

    @Override
    public String toString() {
        return "SceneTrigger{"
                + "name='"
                + name
                + '\''
                + ", config_id="
                + config_id
                + ", event="
                + event
                + ", source='"
                + source
                + '\''
                + ", condition='"
                + condition
                + '\''
                + ", action='"
                + action
                + '\''
                + ", trigger_count='"
                + trigger_count
                + '\''
                + '}';
    }
}
