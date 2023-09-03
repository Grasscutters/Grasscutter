package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeTransferReqOuterClass;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.HomeTransferReq)
public class HandlerHomeTransferReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = HomeTransferReqOuterClass.HomeTransferReq.parseFrom(payload);
        var player = session.getPlayer();
        var home = player.getCurHomeWorld().getHome();
        var item = home.getHomeSceneItem(player.getSceneId());

        var pos =
                req.getIsTransferToMainHousePoint()
                        ? item.getBornPos()
                        : player
                                .getCurHomeWorld()
                                .getSceneById(player.getSceneId())
                                .getScriptManager()
                                .getConfig()
                                .born_pos;
        if (req.getGuid() != 0) {
            var target = item.getTeleportPointPos(req.getGuid());
            if (target != null) {
                pos = target;
            }
        }

        player.getCurHomeWorld().transferPlayerToScene(player, player.getSceneId(), pos);
        session.send(new BasePacket(PacketOpcodes.HomeTransferRsp));
    }
}
