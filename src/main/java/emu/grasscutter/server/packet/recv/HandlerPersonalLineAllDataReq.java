package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketCoopDataNotify;
import emu.grasscutter.server.packet.send.PacketPersonalLineAllDataRsp;

@Opcodes(PacketOpcodes.PersonalLineAllDataReq)
public class HandlerPersonalLineAllDataReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        session.send(
                new PacketPersonalLineAllDataRsp(
                        session.getPlayer().getQuestManager().getMainQuests().values()));
        // TODO: this should maybe be at player login?
        session.send(new PacketCoopDataNotify());
    }
}
