package emu.grasscutter.game.managers.collection;


import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.utils.Position;

public class CollectionData {
    Gadget gadget;
    MotionInfo motionInfo;
    Prop[] fightPropList;
    static class GatherGadget{
        int itemId;
    }
    static class Gadget{
        int gadgetId;
        int authorityPeerId;
        int configId;
        int groupId;
        boolean isEnableInteract;
        GatherGadget gatherGadget;
    }
    static class MotionInfo{
        Position pos;
        Position rot;
    }
    static class Prop{
        int propType;
        float propValue;
    }
}
