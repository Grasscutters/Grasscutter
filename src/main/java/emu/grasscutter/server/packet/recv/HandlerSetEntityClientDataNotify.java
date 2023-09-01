package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SetEntityClientDataNotifyOuterClass.SetEntityClientDataNotify;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.SetEntityClientDataNotify)
public class HandlerSetEntityClientDataNotify extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        // Skip if there is no one to broadcast it too
        if (session.getPlayer().getScene().getPlayerCount() <= 1) {
            return;
        }

        // Make sure packet is a valid proto before replaying it to the other players
        SetEntityClientDataNotify notif = SetEntityClientDataNotify.parseFrom(payload);

        BasePacket packet = new BasePacket(PacketOpcodes.SetEntityClientDataNotify, true);
        packet.setData(notif);

        session.getPlayer().getScene().broadcastPacket(packet);
    }
}
