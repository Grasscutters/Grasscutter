package emu.grasscutter.game.managers.energy;

import java.util.List;

public class SkillParticleGenerationEntry {
    private int avatarId;
    private List<SkillParticleGenerationInfo> amountList;

    public int getAvatarId() {
        return this.avatarId;
    }

    public List<SkillParticleGenerationInfo> getAmountList() {
        return this.amountList;
    }
}
