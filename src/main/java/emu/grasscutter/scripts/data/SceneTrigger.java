package emu.grasscutter.scripts.data;

import lombok.Setter;

@Setter
public class SceneTrigger {
    public String name;
    public int config_id;
    public int event;
    public String source;
    public String condition;
    public String action;

    public transient SceneGroup currentGroup;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SceneTrigger sceneTrigger) {
            return this.name.equals(sceneTrigger.name);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        return "SceneTrigger{" +
            "name='" + this.name + '\'' +
            ", config_id=" + this.config_id +
            ", event=" + this.event +
            ", source='" + this.source + '\'' +
            ", condition='" + this.condition + '\'' +
            ", action='" + this.action + '\'' +
            '}';
    }
}
