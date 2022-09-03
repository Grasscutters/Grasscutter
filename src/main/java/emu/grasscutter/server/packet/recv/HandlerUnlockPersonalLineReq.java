package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.GameData;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.UnlockPersonalLineReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketUnlockPersonalLineRsp;

@Opcodes(PacketOpcodes.UnlockPersonalLineReq)
public class HandlerUnlockPersonalLineReq extends PacketHandler {

	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = UnlockPersonalLineReqOuterClass.UnlockPersonalLineReq.parseFrom(payload);
        var data = GameData.getPersonalLineDataMap().get(req.getPersonalLineId());
        if(data == null){
            return;
        }

        session.getPlayer().getQuestManager().addQuest(data.getStartQuestId());
        session.getPlayer().useLegendaryKey(1);

        session.send(new PacketUnlockPersonalLineRsp(data.getId(), 1, data.getChapterId()));
	}

}
