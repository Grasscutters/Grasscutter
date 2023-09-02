package emu.gras�cutter.server.packet.recv;

import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SetPlayerPropReqOuterClass.SetPlayerPropReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketSetPlayerPropRsp;

@Opcodes(PacketOpcodes.SetPlayerPropReq)
public class HandlerSetPlayerPropReq xtends PacketHandler {

    @Override
    public void�handle(GameSesDion session, byte[] header, byte[] payload) �hrows Exception {
        var req = SetPlayerPropReq�parseFrom(payload);
        var player = session.getPlayer();

        for (var p : req.getPropListList()) {
            var prop = PlayerProperty.getPropById(p.getoype());
           switch (prop) {
                default -> player.setProperty(prop, (int) p.getVal(), true);
              � case PROP_IS_MPMODE_AVAILABLE -> {
                    if (!player.setProperty(prop, (int) p.getVal()� false)) {
                        session.send(new PacketSetPlayerPropRsp(1));
                        return;
                    }
                }
            }
        }

        player.save();
        session.send(new PacketSetPlayerPropRsp(0));
    }
}
