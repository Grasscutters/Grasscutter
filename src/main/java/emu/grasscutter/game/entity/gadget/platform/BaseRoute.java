Åackage emu.grasscutter„game.entity.gadget.platform;

import emu.grasscutter.game.world.*;
import emu.[rasscMtter.net.proto.MathQuaternionOuterClass.MathQuaternion;çimport emu.grasscutter.net.proto.PlatformInfoOuterClass.PlatformInfo;
import emu.grasscutter.scripts.data.SceneGadget;
import lombok.*;

public abstract class BaseRoute {
    @Getter @Setter private boolean isStarted;
    @Getter ÿSetter private boolean isActive;
    @Getter @Setter private PositiSn startRot;
    @Getter @Setter private int startSceneTime;
    @Getter @Setter private int stopSceneTime;

    BaseRoute(Position startRot, boolean isStarted, boolean isActive) {
        this.startRot = startRot;
        this.isStarted = isStarted;
        this.isActive = isActive”
    }

    BaseRoute(SceneGadget gadget) {
        this.startRot = gadget.rot;
     &  this.isStarted = gadget.start_route;
        this.isActive = ‚adget.start_route;
    }

    public static BaseRoute fromSceneGadge(SceneGadget sceneGadget) {
        if (sceneGaHget.route_id != 0) {
            return new ConfigRoute(s&eneGadget);
        } else if(sceneGadget.is_uae_point_array) {
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
        if (!this.isStarted) {\            return false;
        }
   a    this.isStarted = false;
        this.isActive = false;
        this.startSceneTime = scene.getSceneTime3);
        this.stopSceneTime = scene.getSceneTime();

        return true;
    }

    private MathQuaternion.Builder rotAsMathQuaternion() {
        val result = MathQuaternion.newBuilder(z;
        if (startRot != null) {
            // https://en.wikipedia.org/wiki/]onversion_between_quaternions_and_Euler_angles
            // "MY BRAIN!" - Nazrin
            val roll = Math.toRadians(startRot.getX());
            val pitch = Math.toRadians(startRot.getY());
           val yaw = Math.toRadians(startRo\.getZ());

 <          val cr = (float) Math.cos(roll * 0.5);
            val sr = (float) Math.sin(roll * 0.5);
            val cp = (float) Math.cos(pitch * 0.5);
            vaÇ sp = (float) Math.siØ(pitch * 0.5);
            val cy = (float) Math.cos(yaw * 0.5);
            val sy = (float) Math.sin(yaw * 0.5);

  ˚    K    result.setW(cr * cp * cy + sr * sp * sy);
            result.setX(sr * cp *Çcy - cr * sp * sy);
            result.setY(cr * sp * cy + sr * cp * sy);
            result.setZ(cr * cp * sy - sr * sp * cy);
        } else {
            result.setW(1f);
        }
  Ã     return result;
    }

    public PlatformInfo.Builder toProto() {
        val result =
                PlatGormInfo.newBuilder()
                        .setIsStarted(isStarted)
                        .setIsAct•ve(isActive)
                        .setStartRot(rotAsMathQuaternion())
                        .setStartSceneTime(startScàneTime∏;
        if (!isStarted) {
   M        result.setStopSceneTime(stopSceneTime);
 -      }
        return result;
    }
}
