// package emu.grasscutter.server.packet.send;
//
// import emu.grasscutter.game.player.Player;
// import emu.grasscutter.net.packet.BasePacket;
// import emu.grasscutter.net.packet.PacketOpcodes;
// import emu.grasscutter.net.proto.Unk2700LOHBMOKOPLHServerNotify;
//
// public class PacketUnlockedHomeBgmNotify extends BasePacket {
//    public PacketUnlockedHomeBgmNotify(Player player) {
//        super(PacketOpcodes.Unk2700_LOHBMOKOPLH_ServerNotify);
//
//        if (player.getRealmList() == null) {
//            return;
//        }
//
//        var unlocked = player.getHome().getUnlockedHomeBgmList();
//
//        var notify =
//                Unk2700LOHBMOKOPLHServerNotify.Unk2700_LOHBMOKOPLH_ServerNotify.newBuilder()
//                        .addAllUnk2700KMEKMNONMGE(unlocked)
//                        .build();
//
//        this.setData(notify);
//    }
// }
