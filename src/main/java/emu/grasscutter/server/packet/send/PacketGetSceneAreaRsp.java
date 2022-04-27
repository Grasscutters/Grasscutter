package emu.grasscutter.server.packet.send;

import java.util.Arrays;
import java.util.stream.Collectors;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.CityInfoOuterClass.CityInfo;
import emu.grasscutter.net.proto.GetSceneAreaRspOuterClass.GetSceneAreaRsp;

public class PacketGetSceneAreaRsp extends BasePacket {
	
	public PacketGetSceneAreaRsp(int sceneId) {
		super(PacketOpcodes.GetSceneAreaRsp);
		
		this.buildHeader(0);
		
		GetSceneAreaRsp p = GetSceneAreaRsp.newBuilder()
				.setSceneId(sceneId)
				.addAllAreaIdList(Arrays.stream(new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,17,18,19,100,101,102,103,200,210,300}).boxed().collect(Collectors.toList()))
				.addCityInfoList(CityInfo.newBuilder().setCityId(1).setLevel(1).build())
				.addCityInfoList(CityInfo.newBuilder().setCityId(2).setLevel(1).build())
				.addCityInfoList(CityInfo.newBuilder().setCityId(3).setLevel(1).build())
				.build();
		
		this.setData(p);
	}
}
