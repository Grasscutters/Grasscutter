package emu.grasscutter.data.binout.routes;

import emu.grasscutter.net.proto.RouteOuterClass;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Route {
    private int localId;
    private String name;
    private RouteType type = RouteType.Unknown;
    private RoutePoint[] points;
    private float arriveRange; // optional
    private RotType rotType; // optional
    private RotAngleType rotAngleType; // optional

    public RouteOuterClass.Route toProto() {
        val builder = RouteOuterClass.Route.newBuilder().setRouteType(type.getValue());
        if (points != null) {
            for (var routePoint : points) {
                builder.addRoutePoints(routePoint.toProto().setArriveRange(arriveRange));
            }
        }
        return builder.build();
    }
}
