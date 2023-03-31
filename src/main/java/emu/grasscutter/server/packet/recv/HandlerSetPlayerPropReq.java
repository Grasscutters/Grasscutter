package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PropValueOuterClass.PropValue;
import emu.grasscutter.net.proto.SetPlayerPropReqOuterClass.SetPlayerPropReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketSetPlayerPropRsp;

@Opcodes(PacketOpcodes.SetPlayerPropReq)
public class HandlerSetPlayerPropReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        // Auto template
        SetPlayerPropReq req = SetPlayerPropReq.parseFrom(payload);
        Player player = session.getPlayer();
        for (PropValue p : req.getPropListList()) {
            PlayerProperty prop = PlayerProperty.getPropById(p.getType());
            if (prop == PlayerProperty.PROP_IS_MP_MODE_AVAILABLE) {
                if (!player.setProperty(prop, (int) p.getVal(), false)) {
                    session.send(new PacketSetPlayerPropRsp(1));
                    return;
                }
            }
        }
        player.save();
        session.send(new PacketSetPlayerPropRsp(0));
    }


}
