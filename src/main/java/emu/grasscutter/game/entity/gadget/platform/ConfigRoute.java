package emu.grasscutter.game.entity.gadget.platform;

import emu.grasscutter.net.proto.MovingPlatformTypeOuterClass;
import emu.grasscutter.net.proto.PlatformInfoOuterClass;
import emu.grasscutter.scripts.data.SceneGadget;
import emu.grasscutter.utils.Position;
import lombok.Getter;
import lombok.Setter;

public class ConfigRoute extends BaseRoute {

    @Getter @Setter private int routeId;

    public ConfigRoute(SceneGadget gadget) {
        super(gadget);
        this.routeId = gadget.route_id;
    }

    public ConfigRoute(Position startRot, boolean startRoute, boolean isActive, int routeId) {
        super(startRot, startRoute, isActive);
        this.routeId = routeId;
    }

    @Override
    public PlatformInfoOuterClass.PlatformInfo.Builder toProto() {
        return super.toProto()
            .setRouteId(routeId)
            .setMovingPlatformType(MovingPlatformTypeOuterClass.MovingPlatformType.MOVING_PLATFORM_TYPE_USE_CONFIG);
    }
}
