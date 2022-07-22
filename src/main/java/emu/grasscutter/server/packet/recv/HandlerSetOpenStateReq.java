package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.props.OpenState;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SetOpenStateReqOuterClass.SetOpenStateReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketSetOpenStateRsp;

@Opcodes(PacketOpcodes.SetOpenStateReq)
public class HandlerSetOpenStateReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = SetOpenStateReq.parseFrom(payload);
        int openState = req.getKey();
        int value = req.getValue();

        session.getPlayer().getOpenStateManager().setOpenState(OpenState.getTypeByValue(openState), value);
        //Client Automatically Updates its OpenStateMap, no need to send OpenStateUpdateNotify

        session.send(new PacketSetOpenStateRsp(openState,value));
    }

}
