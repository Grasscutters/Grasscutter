// package emu.grasscutter.server.packet.send;
//
// import emu.grasscutter.net.packet.BasePacket;
// import emu.grasscutter.net.packet.PacketOpcodes;
// import emu.grasscutter.net.proto.MusicBeatmapOuterClass;
// import emu.grasscutter.net.proto.MusicBriefInfoOuterClass;
// import emu.grasscutter.net.proto.MusicGameGetBeatmapReqOuterClass;
// import emu.grasscutter.net.proto.MusicGameGetBeatmapRspOuterClass;
//
// public class PacketMusicGameGetBeatmapRsp extends BasePacket {
//
//    public PacketMusicGameGetBeatmapRsp(MusicBriefInfoOuterClass.MusicBriefInfo briefInfo,
// MusicBeatmapOuterClass.MusicBeatmap musicRecord,
// MusicGameGetBeatmapReqOuterClass.MusicGameGetBeatmapReq req) {
//        super(PacketOpcodes.MusicGameGetBeatmapRsp);
//
//        var proto = MusicGameGetBeatmapRspOuterClass.MusicGameGetBeatmapRsp.newBuilder();
//
//        proto.setMusicBriefInfo(briefInfo)
//            .setMusicShareId(briefInfo.getMusicShareId())
//            .setMusicRecord(musicRecord)
//            .setUnknownEnum1(req.getUnknownEnum1())
//            .setReqType(req.getReqType())
//        ;
//
//
//        this.setData(proto);
//    }
// }
