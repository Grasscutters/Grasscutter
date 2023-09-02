package emu.grasscutter.game.entity.gadget.platform;

import emu.grasscutter.game.world.Position;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.net.proto.MathQuaternionOuterClass.MathQuaternion;

/** TODO mostly hardcoded for EntitySolarIsotomaElevatorPlatform, should be more generic */
public class AbilityRoute extends BaseRoute {

    private final Position basePosition;

    public AbilityRoute(
            Position startRot, boolean startRoute, boolean isActive, Position basePosition) {
        super(startRot, startRoute, isActive);
        this.basePosition = basePosition;
    }

    @Override
    public PlatformInfoOuterClass.PlatformInfo.Builder toProto() {
        return super.toProto()
                .setStartRot(MathQuaternion.newBuilder().setW(1.0F))
                .setPosOffset(basePosition.toProto())
                .setRotOffset(MathQuaternion.newBuilder().setW(1.0F))
                .setMovingPlatformType(
                        MovingPlatformTypeOuterClass.MovingPlatformType.MOVING_PLATFORM_TYPE_ABILITY);
    }
}
