package emu.grasscutter.game.managers.blossom;

import emu.grasscutter.utils.Utils;

public enum BlossomType {
    GOLDEN_GADGET_ID(70360056), BLUE_GADGET_ID(70360057);

    private final int gadgetId;
    BlossomType(int gadgetId) {
        this.gadgetId=gadgetId;
    }
    public int getGadgetId(){
        return this.gadgetId;
    }
    public static BlossomType valueOf(int i){
        if(i == GOLDEN_GADGET_ID.gadgetId){
            return GOLDEN_GADGET_ID;
        }else if(i == BLUE_GADGET_ID.gadgetId){
            return BLUE_GADGET_ID;
        }
        return null;
    }
    public static BlossomType random(){
        int rand = Utils.randomRange(0,1);
        if(rand == 0){
            return GOLDEN_GADGET_ID;
        }else{
            return BLUE_GADGET_ID;
        }
    }
}
