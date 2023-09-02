package emu.grasscutter.game.entity.gadget.platform;

import emu.grasscutter.game.world.Position;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.scripts.data.SceneGadget;
import java.util.*;
import lombok.*;

public class ConfigRoute extends BaseRoute {

    @Getter @Setter private int routeId;
    @Getter @Setter private int startIndex;
    @Getter @Setter private List<Integer> scheduledIndexes;

    public ConfigRoute(SceneGadget gadget) {
        super(gadget);
        this.routeId = gadget.route_id;
        this.startIndex = 0;
        this.scheduledIndexes = new ArrayList<>();
    }

    public ConfigRoute(Position startRot, boolean startRoute, boolean isActive, int routeId) {
        super(startRot, startRoute, isActive);
        this.routeId = routeId;
        this.startIndex = 0;
        this.scheduledIndexes = new ArrayList<>();
    }

    @Override
    public PlatformInfoOuterClass.PlatformInfo.Builder toProto() {
        return super.toProto()
                .setRouteId(this.routeId)
                .setStartIndex(this.startIndex)
                .setMovingPlatformType(
                        MovingPlatformTypeOuterClass.MovingPlatformType.MOVING_PLATFORM_TYPE_USE_CONFIG);
    }
}
