// package emu.grasscutter.server.packet.send;
//
// import emu.grasscutter.net.packet.BasePacket;
// import emu.grasscutter.net.packet.PacketOpcodes;
// import emu.grasscutter.net.proto.HomeUnknown1NotifyOuterClass;
//
// public class PacketHomeUnknown1Notify extends BasePacket {
//
//    public PacketHomeUnknown1Notify(boolean isEnterEditMode) {
//        super(PacketOpcodes.Unk2700_JDMPECKFGIG_ServerNotify);
//
//        var proto = HomeUnknown1NotifyOuterClass.HomeUnknown1Notify.newBuilder();
//
//        proto.setIsEnterEditMode(isEnterEditMode);
//
//        this.setData(proto);
//    }
// }
