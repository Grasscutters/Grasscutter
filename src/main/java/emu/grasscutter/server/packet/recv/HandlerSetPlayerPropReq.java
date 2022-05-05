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

import java.util.ArrayList;
import java.util.List;

@Opcodes(PacketOpcodes.SetPlayerPropReq)
public class HandlerSetPlayerPropReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        // Auto template
        SetPlayerPropReq req = SetPlayerPropReq.parseFrom(payload);
        Player player = session.getPlayer();
        List<PropValue> propList = req.getPropListList();
        for (int i = 0; i < propList.size(); i++) {
            PlayerProperty prop = PlayerProperty.getPropById(propList.get(i).getType());
            if (prop == PlayerProperty.PROP_IS_MP_MODE_AVAILABLE) {
                if (!player.setProperty(prop, (int)propList.get(i).getVal())) {
                    session.send(new PacketSetPlayerPropRsp(1));
                    return;
                }
            }
        }
        player.save();
        session.send(new PacketSetPlayerPropRsp(0));
    }



}
