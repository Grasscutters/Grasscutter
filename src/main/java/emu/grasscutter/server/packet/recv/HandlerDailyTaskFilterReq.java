package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DailyTaskFilterCityReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketDailyTaskFilterRsp;

@Opcodes(PacketOpcodes.DailyTaskFilterCityReq)
public class HandlerDailyTaskFilterReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = DailyTaskFilterCityReqOuterClass.DailyTaskFilterCityReq.parseFrom(payload);

        var world = session.getPlayer().getWorld();
        if (world.getHost().getUid() != session.getPlayer().getUid()) {
            return;
        }

        session.getPlayer().getDailyTaskManager().filterCityId(req.getCityId());

        session.send(new PacketDailyTaskFilterRsp(req.getCityId()));
    }
}
