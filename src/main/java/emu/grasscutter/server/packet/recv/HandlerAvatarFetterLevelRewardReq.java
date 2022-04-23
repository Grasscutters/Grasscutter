package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GenshinData;
import emu.grasscutter.data.def.RewardData;
import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarFetterLevelRewardReqOuterClass.AvatarFetterLevelRewardReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketAvatarFetterLevelRewardRsp;
import emu.grasscutter.server.packet.send.PacketItemAddHintNotify;
import emu.grasscutter.server.packet.send.PacketUnlockNameCardNotify;
import emu.grasscutter.net.packet.PacketHandler;

@Opcodes(PacketOpcodes.AvatarFetterLevelRewardReq)
public class HandlerAvatarFetterLevelRewardReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		AvatarFetterLevelRewardReq req = AvatarFetterLevelRewardReq.parseFrom(payload);
        if (req.getFetterLevel() < 10) {
            // You don't have a full level of fetter level, why do you want to get a divorce certificate?
            session.send(new PacketAvatarFetterLevelRewardRsp(req.getAvatarGuid(), req.getFetterLevel()));
        } else {
            long avatarGuid = req.getAvatarGuid();

            int rewardId = session
                .getPlayer()
                .getAvatars()
                .getAvatarByGuid(avatarGuid)
                .getNameCardRewardId();

            RewardData card = GenshinData.getRewardDataMap().get(rewardId);
            int cardId = card.getRewardItemList().get(0).getItemId();

            if (session.getPlayer().getNameCardList().contains(cardId)) {
                // Already got divorce certificate.
                session.send(new PacketAvatarFetterLevelRewardRsp(req.getAvatarGuid(), req.getFetterLevel()));
                return;
            }

            GenshinItem item = new GenshinItem(cardId);
            session.getPlayer().sendPacket(new PacketItemAddHintNotify(item, ActionReason.FetterLevelReward));
            session.send(new PacketUnlockNameCardNotify(cardId));
            session.send(new PacketAvatarFetterLevelRewardRsp(avatarGuid, req.getFetterLevel(), rewardId));
        }
	}
}
