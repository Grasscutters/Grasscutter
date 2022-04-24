package emu.grasscutter.server.packet.send;

import java.util.Set;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerLevelRewardUpdateNotifyOuterClass.PlayerLevelRewardUpdateNotify;

public class PacketPlayerLevelRewardUpdateNotify extends GenshinPacket {
	
	public PacketPlayerLevelRewardUpdateNotify(Set<Integer> rewardedLevels) {
		super(PacketOpcodes.PlayerLevelRewardUpdateNotify);

		PlayerLevelRewardUpdateNotify.Builder proto = PlayerLevelRewardUpdateNotify.newBuilder();
        
        for (Integer level : rewardedLevels) {
        	proto.addLevelList(level);
        }
		
		this.setData(proto.build());
	}
}
