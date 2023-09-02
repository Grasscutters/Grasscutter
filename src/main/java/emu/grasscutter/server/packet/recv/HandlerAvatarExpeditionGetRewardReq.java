package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.expedition.*;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AvatarExpeditionGetRewardReqOuterClass.AvatarExpeditionGetRewardReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.*;
import java.util.*;

@Opcodes(PacketOpcodes.AvatarExpeditionGetRewardReq)
public class HandlerAvatarExpeditionGetRewardReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        AvatarExpeditionGetRewardReq req = AvatarExpeditionGetRewardReq.parseFrom(payload);
        var player = session.getPlayer();

        ExpeditionInfo expInfo = player.getExpeditionInfo(req.getAvatarGuid());
        List<GameItem> items = new ArrayList<>();
        List<ExpeditionRewardDataList> expeditionRewardDataLists =
                session
                        .getServer()
                        .getExpeditionSystem()
                        .getExpeditionRewardDataList()
                        .get(expInfo.getExpId());

        if (expeditionRewardDataLists != null) {
            expeditionRewardDataLists.stream()
                    .filter(r -> r.getHourTime() == expInfo.getHourTime())
                    .map(ExpeditionRewardDataList::getRewards)
                    .forEach(items::addAll);
        }

        player.getInventory().addItems(items);
        player.sendPacket(new PacketItemAddHintNotify(items, ActionReason.ExpeditionReward));

        player.removeExpeditionInfo(req.getAvatarGuid());
        player.save();
        session.send(new PacketAvatarExpeditionGetRewardRsp(player.getExpeditionInfo(), items));
    }
}
