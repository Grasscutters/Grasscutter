package emu.grasscutter.server.event.player;

import emu.grasscutter.data.excels.avatar.AvatarSkillData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.Cancellable;
import emu.grasscutter.server.event.types.PlayerEvent;
import lombok.Getter;

@Getter
public final class PlayerUseSkillEvent extends PlayerEvent implements Cancellable {
    private final AvatarSkillData skillData;
    private final Avatar avatar;

    public PlayerUseSkillEvent(Player player, AvatarSkillData skillData, Avatar avatar) {
        super(player);

        this.skillData = skillData;
        this.avatar = avatar;
    }

    /**
     * @return {@code true} if the skill is an elemental burst.
     */
    public boolean isElementalBurst() {
        return this.getSkillData().getCostElemVal() <= 0;
    }
}
