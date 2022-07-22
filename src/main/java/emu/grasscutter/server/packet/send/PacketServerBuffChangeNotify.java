package emu.grasscutter.server.packet.send;

import java.util.Collection;

import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.PlayerBuffManager.PlayerBuff;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ServerBuffChangeNotifyOuterClass.ServerBuffChangeNotify;
import emu.grasscutter.net.proto.ServerBuffChangeNotifyOuterClass.ServerBuffChangeNotify.ServerBuffChangeType;

public class PacketServerBuffChangeNotify extends BasePacket {
	
	public PacketServerBuffChangeNotify(Player player, ServerBuffChangeType changeType, PlayerBuff buff) {
		super(PacketOpcodes.ServerBuffChangeNotify);

		var proto = ServerBuffChangeNotify.newBuilder();
		
		for (EntityAvatar entity : player.getTeamManager().getActiveTeam()) {
		    proto.addAvatarGuidList(entity.getAvatar().getGuid());
		}
		
		proto.setServerBuffChangeType(changeType);
		proto.addServerBuffList(buff.toProto());
		
		this.setData(proto);
	}
	
	public PacketServerBuffChangeNotify(Player player, ServerBuffChangeType changeType, Collection<PlayerBuff> buffs) {
        super(PacketOpcodes.ServerBuffChangeNotify);

        var proto = ServerBuffChangeNotify.newBuilder();
        
        for (EntityAvatar entity : player.getTeamManager().getActiveTeam()) {
            proto.addAvatarGuidList(entity.getAvatar().getGuid());
        }
        
        proto.setServerBuffChangeType(changeType);
        proto.addAllServerBuffList(buffs.stream().map(PlayerBuff::toProto).toList());
        
        this.setData(proto);
    }
}
