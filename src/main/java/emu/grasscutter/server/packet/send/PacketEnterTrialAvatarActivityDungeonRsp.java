package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.EnterTrialAvatarActivityDungeonRspOuterClass.EnterTrialAvatarActivityDungeonRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass;

public class PacketEnterTrialAvatarActivityDungeonRsp extends BasePacket {

    public PacketEnterTrialAvatarActivityDungeonRsp(
            int activityId, int trialAvatarIndexId, boolean success) {
        this(
                activityId,
                trialAvatarIndexId,
                success
                        ? RetcodeOuterClass.Retcode.RET_SUCC_VALUE
                        : RetcodeOuterClass.Retcode.RET_FAIL_VALUE);
    }

    public PacketEnterTrialAvatarActivityDungeonRsp(
            int activityId, int trialAvatarIndexId, int retcodeVal) {
        super(PacketOpcodes.EnterTrialAvatarActivityDungeonRsp);
        this.setData(
                EnterTrialAvatarActivityDungeonRsp.newBuilder()
                        .setActivityId(activityId)
                        .setTrialAvatarIndexId(trialAvatarIndexId)
                        .setRetcode(retcodeVal));
    }
}
