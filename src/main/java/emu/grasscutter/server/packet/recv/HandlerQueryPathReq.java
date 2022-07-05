package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.proto.QueryPathReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketQueryPathRsp;

@Opcodes(PacketOpcodes.QueryPathReq)
public class HandlerQueryPathReq extends PacketHandler {

	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = QueryPathReqOuterClass.QueryPathReq.parseFrom(payload);

        /**
         * It is not the actual work
         */
        session.send(new PacketQueryPathRsp(req));
	}

}
