package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.CombineReqOuterClass;
import emu.grasscutter.net.proto.ItemParamOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketCombineRsp;

import java.util.List;
import java.util.stream.Collectors;

@Opcodes(PacketOpcodes.CombineReq)
public class HandlerCombineReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {

        CombineReqOuterClass.CombineReq req = CombineReqOuterClass.CombineReq.parseFrom(payload);

        var result = session.getServer().getCombineManger()
            .combineItem(session.getPlayer(), req.getCombineId(), req.getCombineCount());

        if (result == null) {
            return;
        }

        session.send(new PacketCombineRsp(req,
            this.toItemParamList(result.getMaterial()),
            this.toItemParamList(result.getResult()),
            this.toItemParamList(result.getExtra()),
            this.toItemParamList(result.getBack()),
            this.toItemParamList(result.getBack())));
    }

    private List<ItemParamOuterClass.ItemParam> toItemParamList(List<ItemParamData> list) {
        return list.stream()
            .map(item -> ItemParamOuterClass.ItemParam.newBuilder()
                .setItemId(item.getId())
                .setCount(item.getCount())
                .build())
            .collect(Collectors.toList());
    }
}

