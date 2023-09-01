package emu.grasscutter.server.packet.recv;

import com.github.davidmoten.guavamini.Lists;
import emu.grasscutter.game.home.HomeFurnitureItem;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.HomeTransferReqOuterClass;
import emu.grasscutter.server.game.GameSession;

import java.util.List;

@Opcodes(PacketOpcodes.HomeTransferReq)
public class HandlerHomeTransferReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = HomeTransferReqOuterClass.HomeTransferReq.parseFrom(payload);
        var player = session.getPlayer();
        var home = player.getCurHomeWorld().getHome();
        var item = home.getHomeSceneItem(player.getSceneId());

        if (req.getIsTransferToSafePoint()) {
            player.getCurHomeWorld().transferPlayerToScene(player, player.getSceneId(), item.getBornPos());
        } else {
            for (var homeBlockItem : item.getBlockItems().values()) {
                List<HomeFurnitureItem> items = Lists.newArrayList();
                items.addAll(homeBlockItem.getDeployFurnitureList());
                items.addAll(homeBlockItem.getPersistentFurnitureList());
                items.stream()
                    .filter(homeFurnitureItem -> homeFurnitureItem.getGuid() == req.getGuid())
                    .findFirst()
                    .ifPresent(homeFurnitureItem -> {
                        player.getCurHomeWorld().transferPlayerToScene(player, player.getSceneId(), homeFurnitureItem.getSpawnPos());
                    });
            }
        }

        session.send(new BasePacket(PacketOpcodes.HomeTransferRsp));
    }
}
