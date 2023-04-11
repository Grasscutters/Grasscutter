// package emu.grasscutter.server.packet.send;
//
// import emu.grasscutter.net.packet.BasePacket;
// import emu.grasscutter.net.packet.PacketOpcodes;
// import emu.grasscutter.net.proto.RemoveCustomTeamRspOuterClass.RemoveCustomTeamRsp;
// import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
//
// public class PacketRemoveCustomTeamRsp extends BasePacket {
//    public PacketRemoveCustomTeamRsp(Retcode retcode, int id) {
//        super(PacketOpcodes.RemoveCustomTeamRsp);
//
//        RemoveCustomTeamRsp proto =
//
// RemoveCustomTeamRsp.newBuilder().setRetcode(retcode.getNumber()).setId(id).build();
//
//        this.setData(proto);
//    }
//
//    public PacketRemoveCustomTeamRsp(int id) {
//        this(Retcode.RET_SUCC, id);
//    }
// }
