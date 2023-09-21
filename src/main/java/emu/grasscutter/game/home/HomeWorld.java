package emu.grasscutter.game.home;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.entity.EntityTeam;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.game.world.World;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.proto.ChatInfoOuterClass;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.packet.send.PacketDelTeamEntityNotify;
import emu.grasscutter.server.packet.send.PacketPlayerChatNotify;
import emu.grasscutter.server.packet.send.PacketPlayerGameTimeNotify;
import java.util.List;
import java.util.function.Consumer;
import lombok.Getter;

@Getter
public class HomeWorld extends World {
    private final GameHome home;
    private HomeModuleManager moduleManager;

    public HomeWorld(GameServer server, Player owner) {
        super(server, owner);

        this.home = owner.isOnline() ? owner.getHome() : GameHome.getByUid(owner.getUid());
        this.refreshModuleManager();
    }

    @Override
    public boolean onTick() {
        if (this.moduleManager == null) {
            return false;
        }
        this.moduleManager.tick();

        if (this.getTickCount() % 10 == 0) {
            this.getPlayers().forEach(p -> p.sendPacket(new PacketPlayerGameTimeNotify(p)));
        }

        if (this.isInHome(this.getHost()) && this.getTickCount() % 60 == 0) {
            this.getHost().updatePlayerGameTime(this.getCurrentWorldTime());
        }

        this.tickCount++;
        return false;
    }

    public void refreshModuleManager() {
        if (this.moduleManager != null) {
            this.moduleManager.onRemovedModule();
        }

        this.moduleManager = new HomeModuleManager(this);
        this.moduleManager.onSetModule();
    }

    public int getActiveOutdoorSceneId() {
        return this.getHost().getCurrentRealmId() + 2000;
    }

    public int getActiveIndoorSceneId() {
        return this.isRealmIdValid()
                ? this.getSceneById(this.getActiveOutdoorSceneId()).getSceneItem().getRoomSceneId()
                : -1;
    }

    public boolean isRealmIdValid() {
        return this.getHost().getCurrentRealmId() > 0;
    }

    @Override
    public synchronized void addPlayer(Player player) {
        // Check if player already in
        if (this.getPlayers().contains(player)) {
            return;
        }

        // Remove player from prev world
        if (player.getWorld() != null) {
            player.getWorld().removePlayer(player);
        }

        // Register
        player.setWorld(this);
        this.getPlayers().add(player);

        // Set player variables
        if (this.getHost().equals(player)) {
            player.setPeerId(1);
            this.getGuests().forEach(player1 -> player1.setPeerId(player1.getPeerId() + 1));
        } else {
            player.setPeerId(this.getNextPeerId());
        }

        player.getTeamManager().setEntity(new EntityTeam(player));

        // Copy main team to multiplayer team
        if (this.isMultiplayer()) {
            player
                    .getTeamManager()
                    .getMpTeam()
                    .copyFrom(
                            player.getTeamManager().getCurrentSinglePlayerTeamInfo(),
                            player.getTeamManager().getMaxTeamSize());
            player.getTeamManager().setCurrentCharacterIndex(0);

            if (!player.equals(this.getHost())) {
                this.broadcastPacket(
                        new PacketPlayerChatNotify(
                                player,
                                0,
                                ChatInfoOuterClass.ChatInfo.SystemHint.newBuilder()
                                        .setType(
                                                ChatInfoOuterClass.ChatInfo.SystemHintType.SYSTEM_HINT_TYPE_CHAT_ENTER_WORLD
                                                        .getNumber())
                                        .build()));
            }
        }

        // Add to scene
        var scene = this.getSceneById(player.getSceneId());
        scene.addPlayer(player);

        // Info packet for other players
        if (this.getPlayers().size() > 1) {
            this.updatePlayerInfos(player);
        }
    }

    @Override
    public synchronized void removePlayer(Player player) {
        // Remove team entities
        this.broadcastPacket(
                new PacketDelTeamEntityNotify(
                        player.getSceneId(),
                        this.getPlayers().stream()
                                .map(
                                        p ->
                                                p.getTeamManager().getEntity() == null
                                                        ? 0
                                                        : p.getTeamManager().getEntity().getId())
                                .toList()));

        // Deregister
        this.getPlayers().remove(player);
        player.setWorld(null);

        // Remove from scene
        Scene scene = this.getSceneById(player.getSceneId());
        scene.removePlayer(player);

        // Info packet for other players
        if (this.getPlayers().size() > 0) {
            this.updatePlayerInfos(player);
        }

        this.broadcastPacket(
                new PacketPlayerChatNotify(
                        player,
                        0,
                        ChatInfoOuterClass.ChatInfo.SystemHint.newBuilder()
                                .setType(
                                        ChatInfoOuterClass.ChatInfo.SystemHintType.SYSTEM_HINT_TYPE_CHAT_LEAVE_WORLD
                                                .getNumber())
                                .build()));
    }

    @Override
    public HomeScene getSceneById(int sceneId) {
        var scene = this.getScenes().get(sceneId);
        if (scene instanceof HomeScene homeScene) {
            return homeScene;
        }

        var sceneData = GameData.getSceneDataMap().get(sceneId);
        if (sceneData != null) {
            scene = new HomeScene(this, sceneData);
            this.registerScene(scene);
            return (HomeScene) scene;
        }

        return null;
    }

    @Override
    public int getNextPeerId() {
        return this.getPlayers().size() + 1;
    }

    @Override
    public synchronized void setHost(Player host) {
        super.setHost(host);
    }

    @Override
    public final boolean isMultiplayer() {
        return true;
    }

    @Override
    public final boolean isPaused() {
        return false;
    }

    @Override
    public final boolean isTimeLocked() {
        return false;
    }

    public int getOwnerUid() {
        return this.getHost().getUid();
    }

    public List<Player> getGuests() {
        return this.getPlayers().stream().filter(player -> !player.equals(this.getHost())).toList();
    }

    public boolean isInHome(Player player) {
        return this.getPlayers().contains(player);
    }

    public void ifHost(Player hostOrGuest, Consumer<Player> ifHost) {
        if (this.getHost().equals(hostOrGuest)) {
            ifHost.accept(hostOrGuest);
        }
    }

    public void sendPacketToHostIfOnline(BasePacket basePacket) {
        if (this.getHost().isOnline()) {
            this.getHost().sendPacket(basePacket);
        }
    }
}
