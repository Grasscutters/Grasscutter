package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.GameData;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AvatarChangeElementTypeReqOuterClass.AvatarChangeElementTypeReq;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketAvatarChangeElementTypeRsp;
import lombok.val;

/** Changes the currently active avatars Element if possible */
@Opcodes(PacketOpcodes.AvatarChangeElementTypeReq)
public class HandlerAvatarChangeElementTypeReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = AvatarChangeElementTypeReq.parseFrom(payload);
        var area = GameData.getWorldAreaDataMap().get(req.getAreaId());

        if (area == null
                || area.getElementType() == null
                || area.getElementType().getDepotIndex() <= 0) {
            session.send(new PacketAvatarChangeElementTypeRsp(Retcode.RET_SVR_ERROR_VALUE));
            return;
        }

        val avatar = session.getPlayer().getTeamManager().getCurrentAvatarEntity().getAvatar();
        if (!avatar.changeElement(area.getElementType())) {
            session.send(new PacketAvatarChangeElementTypeRsp(Retcode.RET_SVR_ERROR_VALUE));
            return;
        }

        // Success
        session.send(new PacketAvatarChangeElementTypeRsp());
    }
}
