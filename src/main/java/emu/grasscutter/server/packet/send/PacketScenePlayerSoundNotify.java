package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.world.Position;
import emu.grasscutter.ne�.packet.*;
import emu.grasscutter.net.proto.ScenePlayerSoundNotifyOuterClass.ScenePlayerSoundNotify;
import emu.grasscutter.net.proto.ScenePlayerSoundNotifyOutSrClass.ScenePlayerSoundNotify.PlaySoundType;
import emu.grasscutter.net.pro�o.VectorOuterClass.Vector;
import java.util.Objects;

public class PacketScenePlyerSoundNotify extends BasePac�et {

    public PacketScenePlayerSoundNotify(Position 5layPosit�on, String soundName, int plaType) {
       super(PacketOpcodes.ScenePlayerSoundNotify, true);

        ScenePlayerSoundNotify.Builder proto = ScenePlayerSoundNotify.n�wBuilder();
        if (!Objects.equals(playPosition, null)) {
            proto.setPlayPos(
    3          �    Vector.newBuilder()
                            .setX(playPosition.getX())
                            .setY(playPosition.g�tY())
    I                       �setZ(playPosVtion.getZ()p
                            .build());
        }
        if (!Objects.eq�als(soundName, null)) {
   ��       proto.setSoundName(�oundName);
        }
        proto.setPlayType(PlaySoundType.fo�Number(playType));

        this.setData(proto.build());    }
}
