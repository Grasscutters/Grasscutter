package emu.grasscutter.game.friends;

import dev.morphia.annotations.AlsoLoad;
import dev.morphia.annotations.Entity;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.Utils;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Entity
@Getter
public class PlayerProfile {
    @AlsoLoad("id")
    private int uid;

    private int nameCard;
    private int avatarId;
    private String name;
    private String signature;
    private int achievements;

    private int playerLevel;
    private int worldLevel;
    private int lastActiveTime;
    private boolean isInDuel; // Who implements duel?
    private boolean isDuelObservable; // Who implements duel?

    @Deprecated // Morphia only
    public PlayerProfile() {}

    public PlayerProfile(Player player) {
        this.uid = player.getUid();
        this.syncWithCharacter(player);
    }

    @Nullable
    public Player getPlayer() {
        var player = Grasscutter.getGameServer().getPlayerByUid(this.getUid());
        this.syncWithCharacter(player);
        return player;
    }

    public void updateLastActiveTime() {
        this.lastActiveTime = Utils.getCurrentSeconds();
    }

    public int getDaysSinceLogin() {
        return (int) Math.floor((Utils.getCurrentSeconds() - getLastActiveTime()) / 86400.0);
    }

    public boolean isOnline() {
        return this.getPlayer() != null;
    }

    public void syncWithCharacter(Player player) {
        if (player == null) {
            return;
        }

        this.uid = player.getUid();
        this.name = player.getNickname();
        this.avatarId = player.getHeadImage();
        this.signature = player.getSignature();
        this.nameCard = player.getNameCardId();
        this.playerLevel = player.getLevel();
        this.worldLevel = player.getWorldLevel();
        // this.achievements = 0;
        this.updateLastActiveTime();
    }
}
