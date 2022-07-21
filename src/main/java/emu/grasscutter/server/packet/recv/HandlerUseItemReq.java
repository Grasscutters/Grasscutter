package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.UseItemReqOuterClass.UseItemReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketUseItemRsp;

@Opcodes(PacketOpcodes.UseItemReq)
public class HandlerUseItemReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        UseItemReq req = UseItemReq.parseFrom(payload);

        GameItem useItem = session.getServer().getInventorySystem().useItem(session.getPlayer(), req.getTargetGuid(), req.getGuid(), req.getCount(), req.getOptionIdx());
        if (useItem != null) {
            session.send(new PacketUseItemRsp(req.getTargetGuid(), useItem));
        } else {
            session.send(new PacketUseItemRsp());
        }
    }

}
