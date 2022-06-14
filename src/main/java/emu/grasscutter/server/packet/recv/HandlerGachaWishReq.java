package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.gacha.GachaBanner;
import emu.grasscutter.game.gacha.PlayerGachaBannerInfo;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GachaWishReqOuterClass.GachaWishReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGachaWishRsp;

@Opcodes(PacketOpcodes.GachaWishReq)
public class HandlerGachaWishReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        GachaWishReq req = GachaWishReq.parseFrom(payload);

        GachaBanner banner = session.getServer().getGachaManager().getGachaBanners().get(req.getGachaScheduleId());
        PlayerGachaBannerInfo gachaInfo = session.getPlayer().getGachaInfo().getBannerInfo(banner);

        gachaInfo.setFailedChosenItemPulls(0);
        gachaInfo.setWishItemId(req.getItemId());

        session.send(new PacketGachaWishRsp(req.getGachaType(), req.getGachaScheduleId(), req.getItemId(), 0, banner.getWishMaxProgress()));
    }

}
