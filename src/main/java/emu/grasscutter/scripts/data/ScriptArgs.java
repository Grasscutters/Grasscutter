package emu.grasscutter.scripts.data;

import lombok.ToString;

@ToString
public class ScriptArgs {
    public int param1;
    public int param2;
    public int param3;
    public int source_eid; // Source entity
    public int target_eid;
    public int group_id;
    public String source; // source string, used for timers
    public int type; // lua event type, used by scripts and the ScriptManager

    public ScriptArgs(int groupId, int eventType) {
        this(groupId, eventType, 0, 0);
    }

    public ScriptArgs(int groupId, int eventType, int param1) {
        this(groupId, eventType, param1, 0);
    }

    public ScriptArgs(int groupId, int eventType, int param1, int param2) {
        this.group_id = groupId;
        this.type = eventType;
        this.param1 = param1;
        this.param2 = param2;
    }

    public int getParam1() {
        return param1;
    }

    public ScriptArgs setParam1(int param1) {
        this.param1 = param1;
        return this;
    }

    public int getParam2() {
        return param2;
    }

    public ScriptArgs setParam2(int param2) {
        this.param2 = param2;
        return this;
    }

    public int getParam3() {
        return param3;
    }

    public ScriptArgs setParam3(int param3) {
        this.param3 = param3;
        return this;
    }

    public int getSourceEntityId() {
        return source_eid;
    }

    public ScriptArgs setSourceEntityId(int source_eid) {
        this.source_eid = source_eid;
        return this;
    }

    public int getTargetEntityId() {
        return target_eid;
    }

    public ScriptArgs setTargetEntityId(int target_eid) {
        this.target_eid = target_eid;
        return this;
    }

    public String getEventSource() {
        return source;
    }

    public ScriptArgs setEventSource(String source) {
        this.source = source;
        return this;
    }

    public ScriptArgs setEventSource(int source) {
        this.source = Integer.toString(source);
        return this;
    }

    public int getGroupId() {
        return group_id;
    }

    public ScriptArgs setGroupId(int group_id) {
        this.group_id = group_id;
        return this;
    }
}
