package emu.grasscutter.command.commands;

import static emu.grasscutter.command.CommandHelpers.*;
import static emu.grasscutter.config.Configuration.GAME_OPTIONS;
import static emu.grasscutter.utils.lang.Language.translate;

import emu.grasscutter.command.*;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.*;
import emu.grasscutter.data.excels.monster.MonsterData;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.*;
import emu.grasscutter.game.world.*;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;
import lombok.Setter;

@Command(
        label = "spawn",
        aliases = {"drop", "s"},
        usage = {
            "<itemId> [x<amount>] [blk<blockId>] [grp<groupId>] [cfg<configId>] [<x> <y> <z>] [<rotX> <rotY> <rotZ>]",
            "<gadgetId> [x<amount>] [state<state>] [maxhp<maxhp>] [hp<hp>(0 for infinite)] [atk<atk>] [def<def>] [blk<blockId>] [grp<groupId>] [cfg<configId>] [<x> <y> <z>] [<rotX> <rotY> <rotZ>]",
            "<monsterId> [x<amount>] [lv<level>] [ai<aiId>] [maxhp<maxhp>] [hp<hp>(0 for infinite)] [atk<atk>] [def<def>] [blk<blockId>] [grp<groupId>] [cfg<configId>] [<x> <y> <z>] [<rotX> <rotY> <rotZ>]"
        },
        permission = "server.spawn",
        permissionTargeted = "server.spawn.others")
public final class SpawnCommand implements CommandHandler {
    private static final Map<Pattern, BiConsumer<SpawnParameters, Integer>> intCommandHandlers =
            Map.ofEntries(
                    Map.entry(lvlRegex, SpawnParameters::setLvl),
                    Map.entry(amountRegex, SpawnParameters::setAmount),
                    Map.entry(stateRegex, SpawnParameters::setState),
                    Map.entry(blockRegex, SpawnParameters::setBlockId),
                    Map.entry(groupRegex, SpawnParameters::setGroupId),
                    Map.entry(configRegex, SpawnParameters::setConfigId),
                    Map.entry(maxHPRegex, SpawnParameters::setMaxHP),
                    Map.entry(hpRegex, SpawnParameters::setHp),
                    Map.entry(defRegex, SpawnParameters::setDef),
                    Map.entry(atkRegex, SpawnParameters::setAtk),
                    Map.entry(aiRegex, SpawnParameters::setAi));

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        SpawnParameters param = new SpawnParameters();

        parseIntParameters(args, param, intCommandHandlers);

        // At this point, first remaining argument MUST be the id and the rest the pos
        if (args.size() < 1) {
            sendUsageMessage(sender); // Reachable if someone does `/give lv90` or similar
            throw new IllegalArgumentException();
        }

        Position pos = new Position(targetPlayer.getPosition());
        Position rot = new Position(targetPlayer.getRotation());

        switch (args.size()) {
            case 7:
                try {
                    rot.setX(CommandHelpers.parseRelative(args.get(4), rot.getX()));
                    rot.setY(CommandHelpers.parseRelative(args.get(5), rot.getY()));
                    rot.setZ(CommandHelpers.parseRelative(args.get(6), rot.getZ()));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(
                            sender, translate(sender, "commands.execution.argument_error"));
                } // Fallthrough
            case 4:
                try {
                    pos = CommandHelpers.parsePosition(args.get(1), args.get(2), args.get(3), pos, rot);
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(
                            sender, translate(sender, "commands.execution.argument_error"));
                } // Fallthrough
            case 1:
                try {
                    param.id = Integer.parseInt(args.get(0));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(
                            sender, translate(sender, "commands.generic.invalid.entityId"));
                }
                break;
            default:
                sendUsageMessage(sender);
                return;
        }
        param.pos = pos;
        param.rot = rot;

        MonsterData monsterData = GameData.getMonsterDataMap().get(param.id);
        GadgetData gadgetData = GameData.getGadgetDataMap().get(param.id);
        ItemData itemData = GameData.getItemDataMap().get(param.id);
        if (monsterData == null && gadgetData == null && itemData == null) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.generic.invalid.entityId"));
            return;
        }

        param.scene = targetPlayer.getScene();

        if (param.scene.getEntities().size() + param.amount > GAME_OPTIONS.sceneEntityLimit) {
            param.amount =
                    Math.max(
                            Math.min(
                                    GAME_OPTIONS.sceneEntityLimit - param.scene.getEntities().size(), param.amount),
                            0);
            CommandHandler.sendMessage(
                    sender, translate(sender, "commands.spawn.limit_reached", param.amount));
            if (param.amount <= 0) {
                return;
            }
        }

        double maxRadius = Math.sqrt(param.amount * 0.2 / Math.PI);
        for (int i = 0; i < param.amount; i++) {
            pos = GetRandomPositionInCircle(param.pos, maxRadius).addY(3);
            GameEntity entity = null;
            if (itemData != null) {
                entity = createItem(itemData, param, pos);
            }
            if (gadgetData != null) {
                pos.addY(-3);
                entity = createGadget(gadgetData, param, pos, targetPlayer);
            }
            if (monsterData != null) {
                entity = createMonster(monsterData, param, pos);
            }
            applyCommonParameters(entity, param);

            param.scene.addEntity(entity);
        }
        CommandHandler.sendMessage(
                sender, translate(sender, "commands.spawn.success", param.amount, param.id));
    }

    private EntityItem createItem(ItemData itemData, SpawnParameters param, Position pos) {
        return new EntityItem(param.scene, null, itemData, pos, param.rot, 1, true);
    }

    private EntityMonster createMonster(
            MonsterData monsterData, SpawnParameters param, Position pos) {
        var entity = new EntityMonster(param.scene, monsterData, pos, param.rot, param.lvl);
        if (param.ai != -1) {
            entity.setAiId(param.ai);
        }
        return entity;
    }

    private EntityBaseGadget createGadget(
            GadgetData gadgetData, SpawnParameters param, Position pos, Player targetPlayer) {
        EntityBaseGadget entity;
        if (gadgetData.getType() == EntityType.Vehicle) {
            entity = new EntityVehicle(param.scene, targetPlayer, param.id, 0, pos, param.rot);
        } else {
            entity = new EntityGadget(param.scene, param.id, pos, param.rot);
            if (param.state != -1) {
                ((EntityGadget) entity).setState(param.state);
            }
        }
        return entity;
    }

    private void applyCommonParameters(GameEntity entity, SpawnParameters param) {
        if (param.blockId != -1) {
            entity.setBlockId(param.blockId);
        }
        if (param.groupId != -1) {
            entity.setGroupId(param.groupId);
        }
        if (param.configId != -1) {
            entity.setConfigId(param.configId);
        }
        if (param.maxHP != -1) {
            entity.setFightProperty(FightProperty.FIGHT_PROP_MAX_HP, param.maxHP);
            entity.setFightProperty(FightProperty.FIGHT_PROP_BASE_HP, param.maxHP);
        }
        if (param.hp != -1) {
            entity.setFightProperty(
                    FightProperty.FIGHT_PROP_CUR_HP, param.hp == 0 ? Float.MAX_VALUE : param.hp);
        }
        if (param.atk != -1) {
            entity.setFightProperty(FightProperty.FIGHT_PROP_ATTACK, param.atk);
            entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_ATTACK, param.atk);
        }
        if (param.def != -1) {
            entity.setFightProperty(FightProperty.FIGHT_PROP_DEFENSE, param.def);
            entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_DEFENSE, param.def);
        }
    }

    private Position GetRandomPositionInCircle(Position origin, double radius) {
        Position target = origin.clone();
        double angle = Math.random() * 360;
        double r = Math.sqrt(Math.random() * radius * radius);
        target.addX((float) (r * Math.cos(angle))).addZ((float) (r * Math.sin(angle)));
        return target;
    }

    private static class SpawnParameters {
        @Setter public int id;
        @Setter public int lvl = 1;
        @Setter public int amount = 1;
        @Setter public int blockId = -1;
        @Setter public int groupId = -1;
        @Setter public int configId = -1;
        @Setter public int state = -1;
        @Setter public int hp = -1;
        @Setter public int maxHP = -1;
        @Setter public int atk = -1;
        @Setter public int def = -1;
        @Setter public int ai = -1;
        @Setter public Position pos = null;
        @Setter public Position rot = null;
        public Scene scene = null;
    }
}
