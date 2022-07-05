package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EvtEntityRenderersChangedNotifyOuterClass;
import emu.grasscutter.net.proto.ForwardTypeOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketEvtEntityRenderersChangedNotify;
import emu.grasscutter.server.packet.send.PacketWorldPlayerLocationNotify;

@Opcodes(PacketOpcodes.EvtEntityRenderersChangedNotify)
public class HandlerEvtEntityRenderersChangedNotify extends PacketHandler {

	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = EvtEntityRenderersChangedNotifyOuterClass.EvtEntityRenderersChangedNotify.parseFrom(payload);

        switch (req.getForwardType()) {
            case FORWARD_TYPE_TO_ALL ->
                session.getPlayer().getScene().broadcastPacket(new PacketEvtEntityRenderersChangedNotify(req));
            case FORWARD_TYPE_TO_ALL_EXCEPT_CUR ->
                session.getPlayer().getScene().broadcastPacketToOthers(session.getPlayer(), new PacketEvtEntityRenderersChangedNotify(req));
            case FORWARD_TYPE_TO_HOST ->
                session.getPlayer().getScene().getWorld().getHost().sendPacket(new PacketEvtEntityRenderersChangedNotify(req));
        }

	}

}
