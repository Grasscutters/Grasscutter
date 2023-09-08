package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.OtherPlayerEnterHomeNotifyOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.*;

@Opcodes(PacketOpcodes.HomeSceneInitFinishReq)
public class HandlerHomeSceneInitFinishReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var curHomeWorld = session.getPlayer().getCurHomeWorld();

        if (!session.getPlayer().isHasSentInitPacketInHome()) {
            session.getPlayer().setHasSentInitPacketInHome(true);

            if (curHomeWorld.getHost().isOnline()
                    && !curHomeWorld.getHost().equals(session.getPlayer())) {
                curHomeWorld
                        .getHost()
                        .sendPacket(
                                new PacketOtherPlayerEnterOrLeaveHomeNotify(
                                        session.getPlayer(),
                                        OtherPlayerEnterHomeNotifyOuterClass.OtherPlayerEnterHomeNotify.Reason.ENTER));
            }
        }

        curHomeWorld.ifHost(
                session.getPlayer(),
                player -> {
                    player.sendPacket(new PacketHomeAvatarRewardEventNotify(player));
                    player.sendPacket(new PacketHomeAvatarSummonAllEventNotify(player));
                });
        session.send(new PacketHomeMarkPointNotify(session.getPlayer()));

        session.send(new PacketHomeSceneInitFinishRsp());
    }
}
