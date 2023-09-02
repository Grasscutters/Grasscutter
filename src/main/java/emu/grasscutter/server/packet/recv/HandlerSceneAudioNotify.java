package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SceneAudioNotifyOuterClass.SceneAudioNotify;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketSceneAudioNotify;
import java.util.List;

@Opcodes(PacketOpcodes.SceneAudioNotify)
public class HandlerSceneAudioNotify extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        SceneAudioNotify notify = SceneAudioNotify.parseFrom(payload);

        int sourceUid = notify.getSourceUid();
        List<Float> param2 = notify.getParam2List();
        List<String> param3 = notify.getParam3List();
        int type = notify.getType();
        List<Integer> param1 = notify.getParam1List();

        session
                .getPlayer()
                .getScene()
                .broadcastPacket(new PacketSceneAudioNotify(sourceUid, param2, param3, type, param1));
    }
}
