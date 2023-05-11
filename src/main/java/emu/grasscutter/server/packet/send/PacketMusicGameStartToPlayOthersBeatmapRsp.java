// package emu.grasscutter.server.packet.send;
//
// import emu.grasscutter.net.packet.BasePacket;
// import emu.grasscutter.net.packet.PacketOpcodes;
// import emu.grasscutter.net.proto.MusicGameStartToPlayOthersBeatmapRspOuterClass;
// import emu.grasscutter.net.proto.MusicGameUnknown1EnumOuterClass;
//
// public class PacketMusicGameStartToPlayOthersBeatmapRsp extends BasePacket {
//
//	public
// PacketMusicGameStartToPlayOthersBeatmapRsp(MusicGameUnknown1EnumOuterClass.MusicGameUnknown1Enum
// unknownEnum1) {
//		super(PacketOpcodes.MusicGameStartToPlayOthersBeatmapRsp);
//
//        var proto =
// MusicGameStartToPlayOthersBeatmapRspOuterClass.MusicGameStartToPlayOthersBeatmapRsp.newBuilder();
//
//        proto.setUnknownEnum1(unknownEnum1);
//
//        this.setData(proto);
//	}
// }
