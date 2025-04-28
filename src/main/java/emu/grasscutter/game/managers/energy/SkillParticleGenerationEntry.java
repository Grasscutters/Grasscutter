package emu.grasscutter.game.managers.energy;

import java.util.List;
import lombok.Getter;

@Getter
public class SkillParticleGenerationEntry {
    private int avatarId;
    private List<SkillParticleGenerationInfo> amountList;
}
