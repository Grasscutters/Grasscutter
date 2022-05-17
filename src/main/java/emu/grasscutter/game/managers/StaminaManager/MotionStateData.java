package emu.grasscutter.game.managers.StaminaManager;

public class MotionStateData {
    protected String state;
    protected MotionStateType stateType;
    protected int value;
    protected boolean persistent;

    MotionStateData() {
        this.state = "None";
        this.stateType = MotionStateType.None;
        this.value = 0;
        this.persistent = true;
    }

}
