package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ChangeHpReasonOuterClass.ChangeHpReason;
import emu.grasscutter.net.proto.PropChangeReasonOuterClass.PropChangeReason;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketAvatarFightPropUpdateNotify;
import emu.grasscutter.server.packet.send.PacketAvatarLifeStateChangeNotify;
import emu.grasscutter.server.packet.send.PacketEntityFightPropChangeReasonNotify;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;

import java.util.List;

@Opcodes(PacketOpcodes.EnterTransPointRegionNotify)
public class HandlerEnterTransPointRegionNotify extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception{
        session.getPlayer().getTeamManager().getActiveTeam().forEach(entity -> {
            boolean isAlive = entity.isAlive();
            if(entity.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) != entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP)){
                Float hp = entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP)-entity.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);
                
                session.send(new PacketEntityFightPropUpdateNotify(entity,FightProperty.FIGHT_PROP_MAX_HP));

                session.send(new PacketEntityFightPropChangeReasonNotify(
                        entity, FightProperty.FIGHT_PROP_CUR_HP, hp, List.of(3),
                        PropChangeReason.PROP_CHANGE_STATUE_RECOVER, ChangeHpReason.ChangeHpAddStatue));

                entity.setFightProperty(
                        FightProperty.FIGHT_PROP_CUR_HP,
                        entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP)
                );
                session.send(new PacketAvatarFightPropUpdateNotify(entity.getAvatar(), FightProperty.FIGHT_PROP_CUR_HP));
                if (!isAlive) {
                    entity.getWorld().broadcastPacket(new PacketAvatarLifeStateChangeNotify(entity.getAvatar()));
                }
            }
        });
    }
}
