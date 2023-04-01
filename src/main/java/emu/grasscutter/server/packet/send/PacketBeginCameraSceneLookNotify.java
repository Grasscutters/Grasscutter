package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.BeginCameraSceneLookNotifyOuterClass.BeginCameraSceneLookNotify;
import emu.grasscutter.utils.Position;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

import java.util.ArrayList;
import java.util.Collection;

public class PacketBeginCameraSceneLookNotify extends BasePacket {

	public PacketBeginCameraSceneLookNotify(CameraSceneLookNotify parameters) {
		super(PacketOpcodes.BeginCameraSceneLookNotify);
        val builder = BeginCameraSceneLookNotify.newBuilder()
            .setLookPos(parameters.lookPos.toProto())
            .setFollowPos(parameters.followPos.toProto())
            .setDuration(parameters.duration)
            .setIsAllowInput(parameters.isAllowInput)
            .setIsSetFollowPos(parameters.setFollowPos)
            .setIsSetScreenXy(parameters.isScreenXY)
            .setIsRecoverKeepCurrent(parameters.recoverKeepCurrent)
            .setIsChangePlayMode(parameters.isChangePlayMode)
            .setScreenY(parameters.screenY)
            .setScreenX(parameters.screenX)
            .setIsForce(parameters.isForce)
            .setIsForce(parameters.isForceWalk)
            .setEntityId(parameters.entityId)
            .addAllOtherParams(parameters.otherParams);
		this.setData(builder);
	}

    // TODO check default values
    // todo find missing field usages:
    //  enum Unk2700_HIAKNNCKHJB (Unk2700_LNCHDDOOECD)
    //  Unk3000_MNLLCJMPMNH (uint32)
    //  Unk2700_DHAHEKOGHBJ (float)
    //  Unk3000_IEFIKMHCKDH (uint32)
    //  Unk3000_OGCLMFFADBD (float)

    @Data @NoArgsConstructor
    public static class CameraSceneLookNotify{
        Position lookPos = new Position();
        Position followPos = new Position();
        float duration = 0.0f;
        boolean isAllowInput = true;
        boolean setFollowPos = false;
        boolean isScreenXY = false;
        boolean recoverKeepCurrent = true;
        boolean isForceWalk = false;
        boolean isForce = false;
        boolean isChangePlayMode = false;
        float screenY = 0.0f;
        float screenX = 0.0f;
        int entityId = 0;
        Collection<String> otherParams = new ArrayList<>(0);
    }
}
