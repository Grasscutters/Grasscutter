package emu.grasscutter.game.managers.leylines;

public enum LeyLinesType {
    //static final int LAY_LINES_BLUE_GADGET_ID = 70360057;
    //static final int LAY_LINES_GOLDEN_GADGET_ID = 70360056;
    LAY_LINES_GOLDEN_GADGET_ID(70360056),LAY_LINES_BLUE_GADGET_ID(70360057);

    int gadgetId;
    LeyLinesType(int gadgetId) {
        this.gadgetId=gadgetId;
    }
    int getGadgetId(){
        return this.gadgetId;
    }

}
