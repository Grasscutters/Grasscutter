package emu.grasscutter.game.avatar;

import dev.morphia.annotations.Entity;

@Entity
public class AvatarProfileData {
    private int avatarId;
    private int level;

    public AvatarProfileData(Avatar avatar) {
        this.update(avatar);
    }

    public int getAvatarId() {
        return this.avatarId;
    }

    public int getLevel() {
        return this.level;
    }

    public void update(Avatar avatar) {
        this.avatarId = avatar.getAvatarId();
        this.level = avatar.getLevel();
    }
}
