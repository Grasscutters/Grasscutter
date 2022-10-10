package emu.grasscutter.game.entity;

import emu.grasscutter.game.entity.platform.EntityAlbedoElevatorPlatform;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.EvtCreateGadgetNotifyOuterClass;

public class EntityAlbedoSolarIsotomaClientGadget extends EntityClientGadget {
    public static final int GADGET_ID = 41038001;
    public static final int ELEVATOR_GADGET_ID = 41038002;

    public EntityAlbedoSolarIsotomaClientGadget(Scene scene, Player player, EvtCreateGadgetNotifyOuterClass.EvtCreateGadgetNotify notify) {
        super(scene, player, notify);
    }

    @Override
    public void onCreate() {
        //Create albedo elevator and send to all.
        var elevator = new EntityAlbedoElevatorPlatform(this, getScene(), getOwner(), ELEVATOR_GADGET_ID, getPosition(), getRotation());
        getScene().addEntity(elevator);
    }
}
