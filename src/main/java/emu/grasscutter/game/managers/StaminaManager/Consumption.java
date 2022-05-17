package emu.grasscutter.game.managers.StaminaManager;


public class Consumption extends MotionStateData implements InnerModifiable<Integer> {
    Consumption(Consumption motionStatusData) {
        this.value = motionStatusData.value;
        this.persistent = motionStatusData.persistent;
        this.state = motionStatusData.state;
        this.stateType = motionStatusData.stateType;
    }

    Consumption(MotionStateData motionStatusData) {
        this.value = motionStatusData.value;
        this.persistent = motionStatusData.persistent;
        this.state = motionStatusData.state;
        this.stateType = motionStatusData.stateType;
    }
    Consumption(){
        this.value = 0;
        this.persistent = true;
        this.state = "None";
        this.stateType = MotionStateType.None;
    }

    

    public Integer getValue() {
        return this.value;
    }

    public String getLable() {
        return this.stateType.name();
    }

    protected boolean isRecovering() {
        return value <= 0;
    }

}
