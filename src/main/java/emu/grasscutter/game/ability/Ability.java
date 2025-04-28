package emu.grasscutter.game.ability;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.AbilityData;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.AbilityStringOuterClass.AbilityString;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.objects.*;
import java.util.*;
import lombok.Getter;

public class Ability {
    @Getter private final AbilityData data;
    @Getter private final GameEntity owner;
    @Getter private final Player playerOwner;

    @Getter private final AbilityManager manager;

    @Getter private final Map<String, AbilityModifierController> modifiers = new HashMap<>();
    @Getter private final Object2FloatMap<String> abilitySpecials = new Object2FloatOpenHashMap<>();

    @Getter
    private static final Map<String, Object2FloatMap<String>> abilitySpecialsModified =
            new HashMap<>();

    @Getter private final int hash;
    @Getter private final Set<Integer> avatarSkillStartIds;

    public Ability(AbilityData data, GameEntity owner, Player playerOwner) {
        this.data = data;
        this.owner = owner;
        this.manager = owner.getWorld().getHost().getAbilityManager();

        if (this.data.abilitySpecials != null) {
            for (var entry : this.data.abilitySpecials.entrySet())
                abilitySpecials.put(entry.getKey(), entry.getValue().floatValue());
        }

        // if(abilitySpecialsModified.containsKey(this.data.abilityName)) {//Modify talent data
        //    abilitySpecials.putAll(abilitySpecialsModified.get(this.data.abilityName));
        // }

        this.playerOwner = playerOwner;

        hash = Utils.abilityHash(data.abilityName);

        data.initialize();

        //
        // Collect skill IDs referenced by AvatarSkillStart modifier actions
        // in onAbilityStart and in every modifier's onAdded action set.
        // These skill IDs will be used by AbilityManager to determine whether
        // an elemental burst has fired correctly.
        //
        avatarSkillStartIds = new HashSet<>();
        if (data.onAbilityStart != null) {
            avatarSkillStartIds.addAll(
                    Arrays.stream(data.onAbilityStart)
                            .filter(action -> action.type == AbilityModifierAction.Type.AvatarSkillStart)
                            .map(action -> action.skillID)
                            .toList());
        }
        avatarSkillStartIds.addAll(
                data.modifiers.values().stream()
                        .map(
                                m ->
                                        (List<AbilityModifierAction>)
                                                (m.onAdded == null ? Collections.emptyList() : Arrays.asList(m.onAdded)))
                        .flatMap(List::stream)
                        .filter(action -> action.type == AbilityModifierAction.Type.AvatarSkillStart)
                        .map(action -> action.skillID)
                        .toList());

        if (data.onAdded != null) {
            processOnAddedAbilityModifiers();
        }
    }

    public void processOnAddedAbilityModifiers() {
        for (AbilityModifierAction modifierAction : data.onAdded) {
            if (modifierAction.type == null) continue;

            if (modifierAction.type == AbilityModifierAction.Type.ApplyModifier) {
                if (modifierAction.modifierName == null) continue;
                else if (!data.modifiers.containsKey(modifierAction.modifierName)) continue;

                var modifierData = data.modifiers.get(modifierAction.modifierName);
                owner.onAddAbilityModifier(modifierData);
            }
        }
    }

    public static String getAbilityName(AbilityString abString) {
        if (abString.hasStr()) return abString.getStr();
        if (abString.hasHash()) return GameData.getAbilityHashes().get(abString.getHash());

        return null;
    }

    @Override
    public String toString() {
        return "Ability Name: %s; Entity Owner: %s; Player Owner: %s"
                .formatted(data.abilityName, owner, playerOwner);
    }
}
