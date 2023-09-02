package emu.grasscutter.command.commands;

import static emu.grasscutter.command.CommandHelpers.*;
import static emu.grasscutter.utils.lang.Language.translate;

import emu.grasscutter.command.*;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.*;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.server.event.entity.EntityDamageEvent;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;
import lombok.Setter;

@Command(
        label = "entity",
        usage = {
            "<configId gadget> [state<state>] [maxhp<maxhp>] [hp<hp>(0 for infinite)] [atk<atk>] [def<def>]",
            "<configId monster> [ai<aiId>] [maxhp<maxhp>] [hp<hp>(0 for infinite)] [atk<atk>] [def<def>]"
        },
        permission = "server.entity")
public final class EntityCommand implements CommandHandler {
    private static final Map<Pattern, BiConsumer<EntityParameters, Integer>> intCommandHandlers =
            Map.ofEntries(
                    Map.entry(stateRegex, EntityParameters::setState),
                    Map.entry(maxHPRegex, EntityParameters::setMaxHP),
                    Map.entry(hpRegex, EntityParameters::setHp),
                    Map.entry(defRegex, EntityParameters::setDef),
                    Map.entry(atkRegex, EntityParameters::setAtk),
                    Map.entry(aiRegex, EntityParameters::setAi));

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        EntityParameters param = new EntityParameters();

        parseIntParameters(args, param, intCommandHandlers);

        // At this point, first remaining argument MUST be the id and the rest the pos
        if (args.size() != 1) {
            sendUsageMessage(sender); // Reachable if someone does `/give lv90` or similar
            throw new IllegalArgumentException();
        }

        try {
            param.configId = Integer.parseInt(args.get(0));
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.generic.invalid.cfgId"));
        }

        param.scene = targetPlayer.getScene();
        var entity = param.scene.getEntityByConfigId(param.configId);

        if (entity == null) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.entity.not_found_error"));
            return;
        }
        applyFightProps(entity, param);
        applyGadgetParams(entity, param);
        applyMonsterParams(entity, param);

        CommandHandler.sendMessage(sender, translate(sender, "commands.status.success"));
    }

    private void applyGadgetParams(GameEntity entity, EntityParameters param) {
        if (!(entity instanceof EntityGadget)) {
            return;
        }
        if (param.state != -1) {
            ((EntityGadget) entity).updateState(param.state);
        }
    }

    private void applyMonsterParams(GameEntity entity, EntityParameters param) {
        if (!(entity instanceof EntityMonster)) {
            return;
        }

        if (param.ai != -1) {
            ((EntityMonster) entity).setAiId(param.ai);
            // TODO notify
        }
    }

    private void applyFightProps(GameEntity entity, EntityParameters param) {
        var changedFields = new ArrayList<FightProperty>();
        if (param.maxHP != -1) {
            setFightProperty(entity, FightProperty.FIGHT_PROP_MAX_HP, param.maxHP, changedFields);
        }
        if (param.hp != -1) {
            float targetHp = param.hp == 0 ? Float.MAX_VALUE : param.hp;
            float oldHp = entity.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);
            setFightProperty(entity, FightProperty.FIGHT_PROP_CUR_HP, targetHp, changedFields);
            EntityDamageEvent event =
                    new EntityDamageEvent(entity, oldHp - targetHp, ElementType.None, null);
            callHPEvents(entity, event);
        }
        if (param.atk != -1) {
            setFightProperty(entity, FightProperty.FIGHT_PROP_ATTACK, param.atk, changedFields);
            setFightProperty(entity, FightProperty.FIGHT_PROP_CUR_ATTACK, param.atk, changedFields);
        }
        if (param.def != -1) {
            setFightProperty(entity, FightProperty.FIGHT_PROP_DEFENSE, param.def, changedFields);
            setFightProperty(entity, FightProperty.FIGHT_PROP_CUR_DEFENSE, param.def, changedFields);
        }
        if (!changedFields.isEmpty()) {
            entity
                    .getScene()
                    .broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, changedFields));
        }
    }

    private void callHPEvents(GameEntity entity, EntityDamageEvent event) {
        entity.runLuaCallbacks(event);
    }

    private void setFightProperty(
            GameEntity entity, FightProperty property, float value, List<FightProperty> modifiedProps) {
        entity.setFightProperty(property, value);
        modifiedProps.add(property);
    }

    private static class EntityParameters {
        @Setter public int configId = -1;
        @Setter public int state = -1;
        @Setter public int hp = -1;
        @Setter public int maxHP = -1;
        @Setter public int atk = -1;
        @Setter public int def = -1;
        @Setter public int ai = -1;
        public Scene scene = null;
    }
}
