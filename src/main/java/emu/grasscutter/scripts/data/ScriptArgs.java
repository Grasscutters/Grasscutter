package emu.grasscutter.scripts.data;

public class ScriptArgs {
    public int param1;
    public int param2;
    public int param3;
    public int source_eid; // Source entity

    public ScriptArgs() {

    }

    public ScriptArgs(int param1) {
        this.param1 = param1;
    }

    public ScriptArgs(int param1, int param2) {
        this.param1 = param1;
        this.param2 = param2;
    }

    public int getParam1() {
        return this.param1;
    }

    public ScriptArgs setParam1(int param1) {
        this.param1 = param1;
        return this;
    }

    public int getParam2() {
        return this.param2;
    }

    public ScriptArgs setParam2(int param2) {
        this.param2 = param2;
        return this;
    }

    public int getParam3() {
        return this.param3;
    }

    public ScriptArgs setParam3(int param3) {
        this.param3 = param3;
        return this;
    }

    public int getSourceEntityId() {
        return this.source_eid;
    }

    public ScriptArgs setSourceEntityId(int source_eid) {
        this.source_eid = source_eid;
        return this;
    }
}
