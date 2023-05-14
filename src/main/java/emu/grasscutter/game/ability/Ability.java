package emu.grasscutter.game.ability;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.AbilityData;
import emu.grasscutter.data.binout.TalentData;
import emu.grasscutter.data.excels.ProudSkillData;
import emu.grasscutter.game.ability.talents.TalentAction;
import emu.grasscutter.game.ability.talents.TalentActionHandler;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.AbilityStringOuterClass.AbilityString;
import emu.grasscutter.utils.Utils;
import io.netty.util.concurrent.FastThreadLocalThread;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import lombok.Getter;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class Ability {
    private static final HashMap<TalentData.Type, TalentActionHandler> talentHandlers = new HashMap<>();
    public static final ExecutorService eventExecutor;

    static {
        eventExecutor = new ThreadPoolExecutor(4, 4,
            60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1000),
            FastThreadLocalThread::new, new ThreadPoolExecutor.AbortPolicy());

        Ability.registerTalentHandlers();
    }

    @Getter private AbilityData data;
    @Getter private GameEntity owner;
    @Getter private Player playerOwner;

    @Getter private AbilityManager manager;
    @Getter private Map<String, AbilityModifierController> modifiers = new HashMap<>();
    @Getter private Object2FloatMap<String> abilitySpecials = new Object2FloatOpenHashMap<>();

    @Getter private static Map<String, Object2FloatMap<String>> abilitySpecialsModified = new HashMap<String, Object2FloatMap<String>>();

    @Getter private int hash;

    public Ability(AbilityData data, GameEntity owner, Player playerOwner) {
        this.data = data;
        this.owner = owner;
        this.manager = owner.getScene().getWorld().getHost().getAbilityManager();

        if (this.data.abilitySpecials != null) {
            for (var entry : this.data.abilitySpecials.entrySet())
                abilitySpecials.put(entry.getKey(), entry.getValue().floatValue());
        }

        //if(abilitySpecialsModified.containsKey(this.data.abilityName)) {//Modify talent data
        //    abilitySpecials.putAll(abilitySpecialsModified.get(this.data.abilityName));
        //}

        this.playerOwner = playerOwner;

        hash = Utils.abilityHash(data.abilityName);

        data.initialize();
    }

    public static void executeTalent(List<TalentData> dataList, ProudSkillData skillData) {
        for (var data : dataList) {
            executeTalent(data, skillData);
        }
    }

    public static void registerTalentHandlers() {
        var reflections = new Reflections("emu.grasscutter.game.ability.talents");
        var handlerClasses = reflections.getSubTypesOf(TalentActionHandler.class);

        for (var obj : handlerClasses) {
            try {
                if (obj.isAnnotationPresent(TalentAction.class)) {
                    var talentAction = obj.getAnnotation(TalentAction.class).value();
                    talentHandlers.put(talentAction, obj.getDeclaredConstructor().newInstance());
                } else {
                    return;
                }
            } catch (Exception e) {
                Grasscutter.getLogger().error("Unable to register handler.", e);
            }
        }
    }

    public static void executeTalent(TalentData data, ProudSkillData skillData) {
        var handler = talentHandlers.get(data.type);

        if (handler == null) {
            Grasscutter.getLogger().debug("Could not execute talent action {}", data.type);
            return;
        }

        eventExecutor.submit(() -> {
            if (!handler.execute(data, skillData)) {
                Grasscutter.getLogger().debug("exec ability talent failed {}", data.type);
            }
        });
    }

    public static String getAbilityName(AbilityString abString) {
        if(abString.hasStr())
            return abString.getStr();
        if(abString.hasHash())
            return GameData.getAbilityHashes().get(abString.getHash());

        return null;
    }
}
