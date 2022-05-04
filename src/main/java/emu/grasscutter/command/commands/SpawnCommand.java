package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.AvatarData;
import emu.grasscutter.data.def.GadgetData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.data.def.MonsterData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.EntityType;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.utils.Position;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Random;

@Command(label = "spawn", usage = "spawn <entityId> [amount] [level(monster only)]",
        description = "Spawns an entity near you", permission = "server.spawn")
public final class SpawnCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, Grasscutter.getLanguage().Run_this_command_in_game);
            return;
        }

        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Spawn_usage);
            return;
        }

        try {
            int id = Integer.parseInt(args.get(0));
            int amount = args.size() > 1 ? Integer.parseInt(args.get(1)) : 1;
            int level = args.size() > 2 ? Integer.parseInt(args.get(2)) : 1;

            MonsterData monsterData = GameData.getMonsterDataMap().get(id);
            GadgetData gadgetData = GameData.getGadgetDataMap().get(id);
            ItemData itemData = GameData.getItemDataMap().get(id);
            if (monsterData == null && gadgetData == null && itemData == null) {
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_entity_id);
                return;
            }

            double maxRadius = Math.sqrt(amount * 0.2 / Math.PI);
            for (int i = 0; i < amount; i++) {
                Position pos = GetRandomPositionInCircle(sender.getPos(), maxRadius).addY(3);
                GameEntity entity = null;
                if (itemData != null) {
                    entity = new EntityItem(sender.getScene(), null, itemData, pos, 1, true);
                }
                if (gadgetData != null) {
                    entity = new EntityVehicle(sender.getScene(), sender.getSession().getPlayer(), gadgetData.getId(), 0, pos, sender.getRotation());
                    int gadgetId = gadgetData.getId();
                    switch (gadgetId) {
                        // TODO: Not hardcode this. Waverider (skiff)
                        case 45001001, 45001002 -> {
                            entity.addFightProperty(FightProperty.FIGHT_PROP_BASE_HP, 10000);
                            entity.addFightProperty(FightProperty.FIGHT_PROP_BASE_ATTACK, 100);
                            entity.addFightProperty(FightProperty.FIGHT_PROP_CUR_ATTACK, 100);
                            entity.addFightProperty(FightProperty.FIGHT_PROP_CUR_HP, 10000);
                            entity.addFightProperty(FightProperty.FIGHT_PROP_CUR_DEFENSE, 0);
                            entity.addFightProperty(FightProperty.FIGHT_PROP_CUR_SPEED, 0);
                            entity.addFightProperty(FightProperty.FIGHT_PROP_CHARGE_EFFICIENCY, 0);
                            entity.addFightProperty(FightProperty.FIGHT_PROP_MAX_HP, 10000);
                        }
                        default -> {}
                    }
                }
                if (monsterData != null) {
                    entity = new EntityMonster(sender.getScene(), monsterData, pos, level);
                }

                sender.getScene().addEntity(entity);
            }
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Spawn_message.replace("{amount}", Integer.toString(amount)).replace("{id}", Integer.toString(id)));
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_entity_id);
        }
    }

    private Position GetRandomPositionInCircle(Position origin, double radius){
        Position target = origin.clone();
        double angle = Math.random() * 360;
        double r = Math.sqrt(Math.random() * radius * radius);
        target.addX((float) (r * Math.cos(angle))).addZ((float) (r * Math.sin(angle)));
        return target;
    }
}
