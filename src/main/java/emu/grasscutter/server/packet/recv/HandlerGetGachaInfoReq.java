package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.gacha.GachaBanner;
import emu.grasscutter.game.gacha.PlayerGachaBannerInfo;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGachaWishRsp;
import emu.grasscutter.server.packet.send.PacketGetGachaInfoRsp;

@Opcodes(PacketOpcodes.GetGachaInfoReq)
public class HandlerGetGachaInfoReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        session.send(new PacketGetGachaInfoRsp(session.getServer().getGachaSystem(), session.getPlayer()));
    }

}
