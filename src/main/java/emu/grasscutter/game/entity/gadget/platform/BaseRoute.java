package emu.grasscutter.game.entity.gadget.platform;

import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.MathQuaternionOuterClass.MathQuaternion;
import emu.grasscutter.net.proto.PlatformInfoOuterClass.PlatformInfo;
import emu.grasscutter.scripts.data.SceneGadget;
import emu.grasscutter.utils.Position;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

public abstract class BaseRoute {
    @Getter @Setter private boolean isStarted;
    @Getter @Setter private boolean isActive;
    @Getter @Setter private Position startRot;
    @Getter @Setter private int startSceneTime;
    @Getter @Setter private int stopSceneTime;

    BaseRoute(Position startRot, boolean isStarted, boolean isActive) {
        this.startRot = startRot;
        this.isStarted = isStarted;
        this.isActive = isActive;
    }

    BaseRoute(SceneGadget gadget) {
        this.startRot = gadget.rot;
        this.isStarted = gadget.start_route;
        this.isActive = gadget.start_route;
    }

    public static BaseRoute fromSceneGadget(SceneGadget sceneGadget) {
        if (sceneGadget.route_id != 0) {
            return new ConfigRoute(sceneGadget);
        } else if (sceneGadget.is_use_point_array) {
            return new PointArrayRoute(sceneGadget);
        }
        return null;
    }

    public boolean startRoute(Scene scene) {
        if (this.isStarted) {
            return false;
        }
        this.isStarted = true;
        this.isActive = true;
        this.startSceneTime = scene.getSceneTime()+300;

        return true;
    }

    public boolean stopRoute(Scene scene) {
        if (!this.isStarted) {
            return false;
        }
        this.isStarted = false;
        this.isActive = false;
        this.startSceneTime = scene.getSceneTime();
        this.stopSceneTime = scene.getSceneTime();

        return true;
    }

    private MathQuaternion.Builder rotAsMathQuaternion() {
        val result = MathQuaternion.newBuilder();
        if (startRot != null) {
            result.setX(startRot.getX())
                .setY(startRot.getY())
                .setZ(startRot.getZ());
        }
        return result;
    }

    public PlatformInfo.Builder toProto() {
        val result = PlatformInfo.newBuilder()
            .setIsStarted(isStarted)
            .setIsActive(isActive)
            .setStartRot(rotAsMathQuaternion())
            .setStartSceneTime(startSceneTime);
        if (!isStarted) {
            result.setStopSceneTime(stopSceneTime);
        }
        return result;
    }
}
