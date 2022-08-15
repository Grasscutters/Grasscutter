package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.expedition.ExpeditionInfo;
import emu.grasscutter.game.expedition.ExpeditionRewardData;
import emu.grasscutter.game.expedition.ExpeditionRewardDataList;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarExpeditionGetRewardReqOuterClass.AvatarExpeditionGetRewardReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketAvatarExpeditionGetRewardRsp;
import emu.grasscutter.server.packet.send.PacketItemAddHintNotify;
import emu.grasscutter.utils.Utils;

import java.util.LinkedList;
import java.util.List;

@Opcodes(PacketOpcodes.AvatarExpeditionGetRewardReq)
public class HandlerAvatarExpeditionGetRewardReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        AvatarExpeditionGetRewardReq req = AvatarExpeditionGetRewardReq.parseFrom(payload);
        var player = session.getPlayer();

        ExpeditionInfo expInfo = player.getExpeditionInfo(req.getAvatarGuid());

        List<GameItem> items = new LinkedList<>();

        if (session.getServer().getExpeditionSystem().getExpeditionRewardDataList().containsKey(expInfo.getExpId())) {
            for (ExpeditionRewardDataList RewardDataList : session.getServer().getExpeditionSystem().getExpeditionRewardDataList().get(expInfo.getExpId())) {
                if (RewardDataList.getHourTime() == expInfo.getHourTime()) {
                    if (!RewardDataList.getExpeditionRewardData().isEmpty()) {
                        for (ExpeditionRewardData RewardData :RewardDataList.getExpeditionRewardData()) {
                            int num = RewardData.getMinCount();
                            if (RewardData.getMinCount() != RewardData.getMaxCount()) {
                                num = Utils.randomRange(RewardData.getMinCount(), RewardData.getMaxCount());
                            }
                            items.add(new GameItem(RewardData.getItemId(), num));
                        }
                    }
                }
            }
        }

        player.getInventory().addItems(items);
        player.sendPacket(new PacketItemAddHintNotify(items, ActionReason.ExpeditionReward));

        player.removeExpeditionInfo(req.getAvatarGuid());
        player.save();
        session.send(new PacketAvatarExpeditionGetRewardRsp(player.getExpeditionInfo(), items));
    }
}

