package emu.grasscutter.scripts.data;

public class SceneTrigger {
	public String name;
	public int config_id;
	public int event;
	public String source;
	public String condition;
	public String action;

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SceneTrigger){
			return this.name.equals(((SceneTrigger)obj).name);
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return "SceneTrigger{" +
				"name='" + name + '\'' +
				", config_id=" + config_id +
				", event=" + event +
				", source='" + source + '\'' +
				", condition='" + condition + '\'' +
				", action='" + action + '\'' +
				'}';
	}
}
