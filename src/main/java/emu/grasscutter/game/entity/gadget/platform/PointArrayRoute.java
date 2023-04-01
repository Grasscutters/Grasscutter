package emu.grasscutter.game.entity.gadget.platform;

import emu.grasscutter.net.proto.MovingPlatformTypeOuterClass;
import emu.grasscutter.net.proto.PlatformInfoOuterClass;
import emu.grasscutter.scripts.data.SceneGadget;
import emu.grasscutter.utils.Position;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO implement point array routes, read from missing resources
 */
public class PointArrayRoute extends BaseRoute {

    @Getter @Setter int currentPoint;
    @Getter @Setter int pointArrayId;

    public PointArrayRoute(SceneGadget gadget) {
        super(gadget);
    }

    public PointArrayRoute(Position startRot, boolean startRoute, boolean isActive, int pointArrayId) {
        super(startRot, startRoute, isActive);
        this.pointArrayId = pointArrayId;
    }

    @Override
    public PlatformInfoOuterClass.PlatformInfo.Builder toProto() {
        return super.toProto()
            .setMovingPlatformType(MovingPlatformTypeOuterClass.MovingPlatformType.MOVING_PLATFORM_TYPE_ROUTE);
    }
}
