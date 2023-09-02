package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.world.Position;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ScenePlayerSoundNotifyOuterClass.ScenePlayerSoundNotify;
import emu.grasscutter.net.proto.ScenePlayerSoundNotifyOuterClass.ScenePlayerSoundNotify.PlaySoundType;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
import java.util.Objects;

public class PacketScenePlayerSoundNotify extends BasePacket {

    public PacketScenePlayerSoundNotify(Position playPosition, String soundName, int playType) {
        super(PacketOpcodes.ScenePlayerSoundNotify, true);

        ScenePlayerSoundNotify.Builder proto = ScenePlayerSoundNotify.newBuilder();
        if (!Objects.equals(playPosition, null)) {
            proto.setPlayPos(
                    Vector.newBuilder()
                            .setX(playPosition.getX())
                            .setY(playPosition.getY())
                            .setZ(playPosition.getZ())
                            .build());
        }
        if (!Objects.equals(soundName, null)) {
            proto.setSoundName(soundName);
        }
        proto.setPlayType(PlaySoundType.forNumber(playType));

        this.setData(proto.build());
    }
}
