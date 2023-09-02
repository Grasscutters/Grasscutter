package emu.grasscutter.game.player;

import static emu.grasscutter.config.Configuration.GAME_OPTIONS;

import dev.morphia.annotations.Entity;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.net.proto.AvatarTeamOuterClass.AvatarTeam;
import java.util.*;

@Entity
public final class TeamInfo {
    private String name;
    private List<Integer> avatars;

    public TeamInfo() {
        this.name = "";
        this.avatars = new ArrayList<>(GAME_OPTIONS.avatarLimits.singlePlayerTeam);
    }

    public TeamInfo(List<Integer> avatars) {
        this.name = "";
        this.avatars = avatars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getAvatars() {
        return avatars;
    }

    public int size() {
        return avatars.size();
    }

    public boolean contains(Avatar avatar) {
        return getAvatars().contains(avatar.getAvatarId());
    }

    public boolean addAvatar(Avatar avatar) {
        if (contains(avatar)) {
            return false;
        }

        getAvatars().add(avatar.getAvatarId());

        return true;
    }

    public boolean removeAvatar(int slot) {
        if (size() <= 1) {
            return false;
        }

        getAvatars().remove(slot);

        return true;
    }

    public void copyFrom(TeamInfo team) {
        copyFrom(team, GAME_OPTIONS.avatarLimits.singlePlayerTeam);
    }

    public void copyFrom(TeamInfo team, int maxTeamSize) {
        // Clone avatar ids from team to copy from
        List<Integer> avatarIds = new ArrayList<>(team.getAvatars());

        // Clear current avatar list first
        this.getAvatars().clear();

        // Copy from team
        int len = Math.min(avatarIds.size(), maxTeamSize);
        for (int i = 0; i < len; i++) {
            int id = avatarIds.get(i);
            this.getAvatars().add(id);
        }
    }

    public AvatarTeam toProto(Player player) {
        AvatarTeam.Builder avatarTeam = AvatarTeam.newBuilder().setTeamName(this.getName());

        for (int i = 0; i < this.getAvatars().size(); i++) {
            Avatar avatar = player.getAvatars().getAvatarById(this.getAvatars().get(i));
            if (avatar == null) continue;

            avatarTeam.addAvatarGuidList(avatar.getGuid());
        }

        return avatarTeam.build();
    }
}
