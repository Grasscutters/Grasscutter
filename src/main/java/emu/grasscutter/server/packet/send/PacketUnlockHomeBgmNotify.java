// package emu.grasscutter.server.packet.send;
//
// import emu.grasscutter.net.packet.BasePacket;
// import emu.grasscutter.net.packet.PacketOpcodes;
// import emu.grasscutter.net.proto.Unk2700MEBFPBDNPGOServerNotify;
//
// public class PacketUnlockHomeBgmNotify extends BasePacket {
//    public PacketUnlockHomeBgmNotify(int homeBgmId) {
//        super(PacketOpcodes.Unk2700_MEBFPBDNPGO_ServerNotify);
//
//        var notify =
//                Unk2700MEBFPBDNPGOServerNotify.Unk2700_MEBFPBDNPGO_ServerNotify.newBuilder()
//                        .addUnk2700ELJPLMIHNIP(homeBgmId)
//                        .build();
//
//        this.setData(notify);
//    }
// }
