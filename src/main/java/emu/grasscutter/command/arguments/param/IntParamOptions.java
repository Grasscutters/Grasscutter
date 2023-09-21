package emu.grasscutter.command.arguments.param;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

import static emu.grasscutter.command.CommandHelpers.*;

public class IntParamOptions {
    public static final Map<Pattern, BiConsumer<DefaultIntParams, Integer>> OPTIONS = Map.ofEntries(
        Map.entry(lvlRegex, DefaultIntParams::setLvl),
        Map.entry(amountRegex, DefaultIntParams::setAmount),
        Map.entry(refineRegex, DefaultIntParams::setRefine),
        Map.entry(rankRegex, DefaultIntParams::setRank),
        Map.entry(constellationRegex, DefaultIntParams::setConstellation),
        Map.entry(skillLevelRegex, DefaultIntParams::setSkillLevel),
        Map.entry(stateRegex, DefaultIntParams::setState),
        Map.entry(blockRegex, DefaultIntParams::setBlock),
        Map.entry(groupRegex, DefaultIntParams::setGroup),
        Map.entry(configRegex, DefaultIntParams::setConfig),
        Map.entry(hpRegex, DefaultIntParams::setHp),
        Map.entry(maxHPRegex, DefaultIntParams::setMaxHP),
        Map.entry(atkRegex, DefaultIntParams::setAtk),
        Map.entry(defRegex, DefaultIntParams::setDef),
        Map.entry(aiRegex, DefaultIntParams::setAi),
        Map.entry(sceneRegex, DefaultIntParams::setScene),
        Map.entry(suiteRegex, DefaultIntParams::setSuite)
    );

    public static boolean anyMatch(String input) {
        return OPTIONS.keySet().stream().anyMatch(pattern -> pattern.matcher(input).find());
    }
}
