package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.GadgetData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.data.excels.MonsterData;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.EntityType;
import emu.grasscutter.utils.Position;
import emu.grasscutter.game.world.Scene;

import java.util.List;
import java.util.regex.Pattern;

import static emu.grasscutter.command.CommandHelpers.*;
import static emu.grasscutter.config.Configuration.*;
import static emu.grasscutter.utils.Language.translate;

@Command(
    label = "spawn",
    aliases = {"drop"},
    usage = {
        "(<itemId>|<monsterId|gadgetId>) [x<amount>] [lv<level>(monster only)] [state<state>(gadget only)] [blk<blockId>] [grp<groupId>] [cfg<configId>] <x> <y> <z>"},
    permission = "server.spawn",
    permissionTargeted = "server.spawn.others")
public final class SpawnCommand implements CommandHandler {
    private static final Pattern stateRegex = Pattern.compile("state(\\d+)");
    private static final Pattern blockRegex = Pattern.compile("blk(\\d+)");
    private static final Pattern groupRegex = Pattern.compile("grp(\\d+)");
    private static final Pattern configRegex = Pattern.compile("cfg(\\d+)");

    private static class SpawnParameters {
        public int id;
        public int lvl = 1;
        public int amount = 1;
        public int blockId = -1;
        public int groupId = -1;
        public int configId = -1;
        public int state = -1;
        public Position pos = null;
    };

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        SpawnParameters param = new SpawnParameters();

        // Extract any tagged arguments (e.g. "lv90", "x100", "state105")
        for (int i = args.size() - 1; i >= 0; i--) {  // Reverse iteration as we are deleting elements
            String arg = args.get(i).toLowerCase();
            boolean deleteArg = false;
            int argNum;
            // Note that a single argument can actually match all of these, e.g. "lv90r5x100"
            if ((argNum = matchIntOrNeg(lvlRegex, arg)) != -1) {
                param.lvl = argNum;
                deleteArg = true;
            }
            if ((argNum = matchIntOrNeg(amountRegex, arg)) != -1) {
                param.amount = argNum;
                deleteArg = true;
            }
            if ((argNum = matchIntOrNeg(stateRegex, arg)) != -1) {
                param.state = argNum;
                deleteArg = true;
            }
            if ((argNum = matchIntOrNeg(blockRegex, arg)) != -1) {
                param.blockId = argNum;
                deleteArg = true;
            }
            if ((argNum = matchIntOrNeg(groupRegex, arg)) != -1) {
                param.groupId = argNum;
                deleteArg = true;
            }
            if ((argNum = matchIntOrNeg(configRegex, arg)) != -1) {
                param.configId = argNum;
                deleteArg = true;
            }
            if (deleteArg) {
                args.remove(i);
            }
        }

        // At this point, first remaining argument MUST be the id and the rest the pos
        if (args.size() < 1) {
            sendUsageMessage(sender);  // Reachable if someone does `/give lv90` or similar
            throw new IllegalArgumentException();
        }
        switch (args.size()) {
            case 4:
                try {
                    float x, y, z;
                    x = Float.parseFloat(args.get(1));
                    y = Float.parseFloat(args.get(2));
                    z = Float.parseFloat(args.get(3));
                    param.pos = new Position(x,y,z);
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, translate(sender, "commands.execution.argument_error"));
                }  // Fallthrough
            case 1:
                try {
                    param.id = Integer.parseInt(args.get(0));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, translate(sender, "commands.generic.invalid.entityId"));
                }
                break;
            default:
                sendUsageMessage(sender);
                return;
        }

        MonsterData monsterData = GameData.getMonsterDataMap().get(param.id);
        GadgetData gadgetData = GameData.getGadgetDataMap().get(param.id);
        ItemData itemData = GameData.getItemDataMap().get(param.id);
        if (monsterData == null && gadgetData == null && itemData == null) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.generic.invalid.entityId"));
            return;
        }

        Scene scene = targetPlayer.getScene();

        if (scene.getEntities().size() + param.amount > GAME_OPTIONS.sceneEntityLimit) {
            param.amount = Math.max(Math.min(GAME_OPTIONS.sceneEntityLimit - scene.getEntities().size(), param.amount), 0);
            CommandHandler.sendMessage(sender, translate(sender, "commands.spawn.limit_reached", param.amount));
            if (param.amount <= 0) {
                return;
            }
        }

        double maxRadius = Math.sqrt(param.amount * 0.2 / Math.PI);
        if(param.pos==null){
            param.pos = targetPlayer.getPosition();
        }

        for (int i = 0; i < param.amount; i++) {
            Position pos = GetRandomPositionInCircle(param.pos, maxRadius).addY(3);
            GameEntity entity = null;
            if (itemData != null) {
                entity = new EntityItem(scene, null, itemData, pos, 1, true);
            }
            if (gadgetData != null) {
                pos.addY(-3);
                if(gadgetData.getType() == EntityType.Vehicle) {
                    entity = new EntityVehicle(scene, targetPlayer, param.id, 0, pos, targetPlayer.getRotation());
                } else {
                    entity = new EntityGadget(scene, param.id, pos, targetPlayer.getRotation());
                    if(param.state!=-1){
                        ((EntityGadget)entity).setState(param.state);
                    }
                }
            }
            if (monsterData != null) {
                entity = new EntityMonster(scene, monsterData, pos, param.lvl);
            }
            if(param.blockId!=-1){
                entity.setBlockId(param.blockId);
            }
            if(param.groupId!=-1){
                entity.setGroupId(param.groupId);
            }
            if(param.configId!=-1){
                entity.setConfigId(param.configId);
            }

            scene.addEntity(entity);
        }
        CommandHandler.sendMessage(sender, translate(sender, "commands.spawn.success", param.amount, param.id));
    }

    private Position GetRandomPositionInCircle(Position origin, double radius) {
        Position target = origin.clone();
        double angle = Math.random() * 360;
        double r = Math.sqrt(Math.random() * radius * radius);
        target.addX((float) (r * Math.cos(angle))).addZ((float) (r * Math.sin(angle)));
        return target;
    }
}
