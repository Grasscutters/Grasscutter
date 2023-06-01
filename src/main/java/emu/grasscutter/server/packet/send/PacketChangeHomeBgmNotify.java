// package emu.grasscutter.server.packet.send;
//
// import emu.grasscutter.net.packet.BasePacket;
// import emu.grasscutter.net.packet.PacketOpcodes;
// import emu.grasscutter.net.proto.Unk2700FJEHHCPCBLGServerNotify;
//
// public class PacketChangeHomeBgmNotify extends BasePacket {
//    public PacketChangeHomeBgmNotify(int homeBgmId) {
//        super(PacketOpcodes.Unk2700_FJEHHCPCBLG_ServerNotify);
//
//        var notify =
//                Unk2700FJEHHCPCBLGServerNotify.Unk2700_FJEHHCPCBLG_ServerNotify.newBuilder()
//                        .setUnk2700BJHAMKKECEI(homeBgmId)
//                        .build();
//
//        this.setData(notify);
//    }
// }
