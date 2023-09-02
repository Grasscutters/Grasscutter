package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PlayerApplyEnterHomeResultNotifyOuterClass;

public class PacketPlayerApplyEnterHomeResultNotify extends BasePacket {
    public PacketPlayerApplyEnterHomeResultNotify(
            int targetUid,
            String nickname,
            boolean agreed,
            PlayerApplyEnterHomeResultNotifyOuterClass.PlayerApplyEnterHomeResultNotify.Reason reason) {
        super(PacketOpcodes.PlayerApplyEnterHomeResultNotify);

        this.setData(
                PlayerApplyEnterHomeResultNotifyOuterClass.PlayerApplyEnterHomeResultNotify.newBuilder()
                        .setTargetUid(targetUid)
                        .setTargetNickname(nickname)
                        .setIsAgreed(agreed)
                        .setReason(reason));
    }
}
