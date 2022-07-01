package emu.grasscutter.game.managers.leylines;

import com.google.gson.annotations.Until;

import emu.grasscutter.utils.Utils;

public enum LeyLinesType {
    LEY_LINES_GOLDEN_GADGET_ID(70360056), LEY_LINES_BLUE_GADGET_ID(70360057);

    private final int gadgetId;
    LeyLinesType(int gadgetId) {
        this.gadgetId=gadgetId;
    }
    public int getGadgetId(){
        return this.gadgetId;
    }
    public static LeyLinesType valueOf(int i){
        if(i == LEY_LINES_GOLDEN_GADGET_ID.gadgetId){
            return LEY_LINES_GOLDEN_GADGET_ID;
        }else if(i == LEY_LINES_BLUE_GADGET_ID.gadgetId){
            return LEY_LINES_BLUE_GADGET_ID;
        }
        return null;
    }
    public static LeyLinesType random(){
        int rand = Utils.randomRange(0,1);
        if(rand == 0){
            return LEY_LINES_GOLDEN_GADGET_ID;
        }else{
            return LEY_LINES_BLUE_GADGET_ID;
        }
    }
}
