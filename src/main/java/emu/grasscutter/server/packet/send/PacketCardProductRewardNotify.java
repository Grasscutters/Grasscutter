package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.CardProductRewardNotifyOuterClass.CardProductRewardNotify;

public class PacketCardProductRewardNotify extends GenshinPacket {
	
	public PacketCardProductRewardNotify(int remainDays) {
		super(PacketOpcodes.CardProductRewardNotify);
		
		CardProductRewardNotify proto = CardProductRewardNotify.newBuilder()
            .setHcoin(90)
            .setRemainDays(remainDays)
            .setProductId("ys_chn_blessofmoon_tier5")
            .build();
        
		this.setData(proto);
	}
}
