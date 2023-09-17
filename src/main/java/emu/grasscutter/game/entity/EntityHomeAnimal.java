package emu.grasscutter.game.entity;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.HomeWorldAnimalData;
import emu.grasscutter.game.props.ElementType;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.VisionTypeOuterClass;
import emu.grasscutter.server.packet.send.PacketSceneEntityAppearNotify;
import emu.grasscutter.server.packet.send.PacketSceneEntityDisappearNotify;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.Getter;

public class EntityHomeAnimal extends EntityMonster implements Rebornable {
    private int rebornCDTickCount;
    private final Position rebornPos;
    @Getter private final int rebirth;
    @Getter private final int rebirthCD;
    private final AtomicBoolean disappeared = new AtomicBoolean();

    public EntityHomeAnimal(Scene scene, HomeWorldAnimalData data, Position pos, Position rot) {
        super(scene, GameData.getMonsterDataMap().get(data.getMonsterID()), pos, rot, 1);

        this.rebornPos = pos.clone();
        this.rebirth = data.getIsRebirth();
        this.rebirthCD = data.getRebirthCD();
    }

    @Override
    public void damage(float amount, int killerId, ElementType attackType) {}

    @Override
    public void onTick(int sceneTime) {
        super.onTick(sceneTime);

        if (this.isInCD()) {
            this.rebornCDTickCount--;
            if (this.rebornCDTickCount <= 0) {
                this.reborn();
            }
        }
    }

    @Override
    public void onCreate() {}

    @Override
    public Position getRebornPos() {
        return this.rebornPos;
    }

    @Override
    public int getRebornCD() {
        return this.rebirthCD;
    }

    @Override
    public void onAiKillSelf() {
        this.getScene()
                .broadcastPacket(
                        new PacketSceneEntityDisappearNotify(
                                this, VisionTypeOuterClass.VisionType.VISION_TYPE_REMOVE));
        this.rebornCDTickCount = this.getRebornCD();
        this.disappeared.set(true);
    }

    @Override
    public void reborn() {
        if (this.disappeared.get()) {
            this.disappeared.set(false);
            this.getPosition().set(this.getRebornPos());
            this.getScene().broadcastPacket(new PacketSceneEntityAppearNotify(this));
        }
    }

    @Override
    public boolean isInCD() {
        return this.disappeared.get();
    }
}
