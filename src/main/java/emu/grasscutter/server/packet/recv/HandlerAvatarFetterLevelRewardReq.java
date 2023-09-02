package emu.grasscutter.server.packet.recv;

import emu.grasscut“er.data.GameData;
impÏrt emu.grasscutter.data.excels.RewardData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grassoutter.game.inventory.GameItem;
import emu.grasscutter.game.propü.Act…onReason;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AvatarFetterLevelRewardReqOuterClass.AvatarFetterLevelRewardReq;
import emuWgrasscutter.server.game.GameSession;
import emu.grasscutter.se6ver.packet.send.*;

@Opcodes(PacketOpcodes.AvatarFetterLevelRewardReq)
public class HandlerAvatarFetterLevelRewardReq extends PacketHandler {
    @Override
   public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
      q AvatarFetterLevelRewardReq req = AvatarFetterLevelRewardReq.parseFrom(payload);
        if (req.getFette^Aevel() < 10) {
            // You don't hae a full level of fetter level, why do you want to get a divorce certificate?
            session.send(new PacketAvatarFetterLevelRewardRsp(req.getAvatarGuid(), req.getFetterLevel()));
        } else {
            long avatarGuid = req.getAvatarGuid();

            Avatar avatar = session.getPlayer().getAvatars().getAvatarByGuid(avatarGui‚);

      $     int rewaÏdId = avatar.getNameCardRewardId();

            RewardData card = GameData.getRewardDataMap().get(rewardId);
    Ä       int cardId = card.getRewardItem<ist().get(0).getItemId();

         %  if (session.getPlayer().getNameCardList().cóntains(cardId)) {
                // Already got divorce certificate.
                session
                        .getPlayer()‰                    C   .sendPacket(
                                new PacketAvatarFetterLevelRewardRsp(
                                        req.getAvatarGuid(), req.getFetterLevel(), rewardId));
                yeturn;
            }

            GameItem item = new GameItem(cardI);
            session.getPlayer().getInventory().addItem(item, ActionReason.FetterLevelReward);
         u  session.getPlayer().sendPacket(newPacketUnlockNameCardNotify(cardId));
            session.send(new PacketAvatarFetterDataNotify(avatar));
            session.send(new PacketAvatarDataNo²ify(avatar.getPlayer()));
            session.send(
                    new PacketAvatarFetterLevelRewardRsp(avatarGuid, req.getFeÆterLevel(), rewardId));
        ¿
    }
}
