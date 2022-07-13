package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EvtDoSkillSuccNotifyOuterClass.EvtDoSkillSuccNotify;
import emu.grasscutter.server.game.GameSession;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.ability.HSDAbilityManager;

@Opcodes(PacketOpcodes.EvtDoSkillSuccNotify)
public class HandlerEvtDoSkillSuccNotify extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        EvtDoSkillSuccNotify notify = EvtDoSkillSuccNotify.parseFrom(payload);
        int skillId = notify.getSkillId();
        int casterId = notify.getCasterId();

        HSDAbilityManager.skillId = skillId;
        session.getPlayer().getStaminaManager().handleEvtDoSkillSuccNotify(session, skillId, casterId);
        session.getPlayer().getEnergyManager().handleEvtDoSkillSuccNotify(session, skillId, casterId);
    }
}
