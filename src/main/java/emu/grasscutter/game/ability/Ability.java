package emu.grasscutter.game.ability;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.AbilityData;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.AbilityStringOuterClass.AbilityString;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.objects.*;
import java.util.*;
import lombok.Getter;

public class Ability {
    @Getter private AbilityData data;
    @Getter private GameEntity owner;
    @Getter private Player playerOwner;

    @Getter private AbilityManager manager;

    @Getter private Map<String, AbilityModifierController> modifiers = new HashMap<>();
    @Getter private Object2FloatMap<String> abilitySpecials = new Object2FloatOpenHashMap<>();

    @Getter
    private static Map<String, Object2FloatMap<String>> abilitySpecialsModified = new HashMap<>();

    @Getter private int hash;

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
