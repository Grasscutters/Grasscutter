package emu.grasscutter.game.home;

import emu.grasscutter.data.excels.scene.SceneData;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.server.packet.send.PacketSceneTimeNotify;

public class HomeScene extends Scene {
    public HomeScene(HomeWorld world, SceneData sceneData) {
        super(world, sceneData);
        this.setDontDestroyWhenEmpty(true);
    }

    @Override
    public boolean isPaused() {
        return false;
    }

    @Override
    public HomeWorld getWorld() {
        return (HomeWorld) super.getWorld();
    }

    public GameHome getHome() {
        return this.getWorld().getHome();
    }

    public HomeSceneItem getSceneItem() {
        return this.getHome().getHomeSceneItem(this.getId());
    }

    @Override
    public void setPaused(boolean paused) {}

    @Override
    public void onTick() {
        this.getEntities()
                .values()
                .forEach(gameEntity -> gameEntity.onTick(this.getSceneTimeSeconds()));

        this.finishLoading();
        this.checkPlayerRespawn();
        if (this.tickCount++ % 10 == 0) this.broadcastPacket(new PacketSceneTimeNotify(this));
    }

    @Override
    public void checkNpcGroup() {}

    @Override
    public void checkSpawns() {}

    @Override
    public void addItemEntity(int itemId, int amount, GameEntity bornForm) {}

    @Override
    public void loadNpcForPlayerEnter(Player player) {}
}
