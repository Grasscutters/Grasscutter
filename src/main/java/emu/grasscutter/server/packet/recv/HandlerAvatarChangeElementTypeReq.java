package emu.grasscutter.server.packet.recv;

import emu.grasscutter.GameConstants;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.AvatarSkillDepotData;
import emu.grasscutter.data.excels.WorldAreaData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarChangeElementTypeReqOuterClass.AvatarChangeElementTypeReq;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketAbilityChangeNotify;
import emu.grasscutter.server.packet.send.PacketAvatarChangeElementTypeRsp;
import emu.grasscutter.server.packet.send.PacketAvatarFightPropNotify;
import emu.grasscutter.server.packet.send.PacketAvatarSkillDepotChangeNotify;

@Opcodes(PacketOpcodes.AvatarChangeElementTypeReq)
public class HandlerAvatarChangeElementTypeReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        AvatarChangeElementTypeReq req = AvatarChangeElementTypeReq.parseFrom(payload);

        WorldAreaData area = GameData.getWorldAreaDataMap().get(req.getAreaId());

        if (area == null || area.getElementType() == null || area.getElementType().getDepotValue() <= 0) {
            session.send(new PacketAvatarChangeElementTypeRsp(Retcode.RET_SVR_ERROR_VALUE));
            return;
        }

        // Get current avatar, should be one of the main characters
        EntityAvatar mainCharacterEntity = session.getPlayer().getTeamManager().getCurrentAvatarEntity();

        int intialSkillDepotId = 0;
        if (mainCharacterEntity.getAvatar().getAvatarId() == GameConstants.MAIN_CHARACTER_MALE) {
            intialSkillDepotId = 500;
        } else if (mainCharacterEntity.getAvatar().getAvatarId() == GameConstants.MAIN_CHARACTER_FEMALE) {
            intialSkillDepotId = 700;
        } else {
            session.send(new PacketAvatarChangeElementTypeRsp(Retcode.RET_SVR_ERROR_VALUE));
            return;
        }
        intialSkillDepotId += area.getElementType().getDepotValue();

        // Sanity checks for skill depots
        Avatar mainCharacter = mainCharacterEntity.getAvatar();
        AvatarSkillDepotData skillDepot = GameData.getAvatarSkillDepotDataMap().get(intialSkillDepotId);
        if (skillDepot == null || skillDepot.getId() == mainCharacter.getSkillDepotId()) {
            session.send(new PacketAvatarChangeElementTypeRsp(Retcode.RET_SVR_ERROR_VALUE));
            return;
        }

        // Success
        session.send(new PacketAvatarChangeElementTypeRsp());

        // Set skill depot
        mainCharacter.setSkillDepotData(skillDepot);

        // Ability change packet
        session.send(new PacketAvatarSkillDepotChangeNotify(mainCharacter));
        session.send(new PacketAbilityChangeNotify(mainCharacterEntity));
        session.send(new PacketAvatarFightPropNotify(mainCharacter));
    }

}
