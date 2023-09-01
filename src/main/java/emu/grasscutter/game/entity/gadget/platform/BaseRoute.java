package emu.grasscutter.game.entity.gadget.platform;

import emu.grasscutter.game.world.*;
import emu.grasscutter.net.proto.MathQuaternionOuterClass.MathQuaternion;
import emu.grasscutter.net.proto.PlatformInfoOuterClass.PlatformInfo;
import emu.grasscutter.scripts.data.SceneGadget;
import lombok.*;

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
        this.startSceneTime = scene.getSceneTime() + 300;

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
            // https://en.wikipedia.org/wiki/Conversion_between_quaternions_and_Euler_angles
            // "MY BRAIN!" - Nazrin
            val roll = Math.toRadians(startRot.getX());
            val pitch = Math.toRadians(startRot.getY());
            val yaw = Math.toRadians(startRot.getZ());

            val cr = (float) Math.cos(roll * 0.5);
            val sr = (float) Math.sin(roll * 0.5);
            val cp = (float) Math.cos(pitch * 0.5);
            val sp = (float) Math.sin(pitch * 0.5);
            val cy = (float) Math.cos(yaw * 0.5);
            val sy = (float) Math.sin(yaw * 0.5);

            result.setW(cr * cp * cy + sr * sp * sy);
            result.setX(sr * cp * cy - cr * sp * sy);
            result.setY(cr * sp * cy + sr * cp * sy);
            result.setZ(cr * cp * sy - sr * sp * cy);
        } else {
            result.setW(1f);
        }
        return result;
    }

    public PlatformInfo.Builder toProto() {
        val result =
                PlatformInfo.newBuilder()
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
