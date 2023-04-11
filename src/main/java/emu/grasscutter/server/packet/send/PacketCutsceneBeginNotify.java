package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.CutSceneBeginNotifyOuterClass.CutSceneBeginNotify;

public class PacketCutsceneBeginNotify extends BasePacket {

    public PacketCutsceneBeginNotify(int cutsceneId) {
        super(PacketOpcodes.CutSceneBeginNotify);

        setData(CutSceneBeginNotify.newBuilder().setCutsceneId(cutsceneId));
    }
}
