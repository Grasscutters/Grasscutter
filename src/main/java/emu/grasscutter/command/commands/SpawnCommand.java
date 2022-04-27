package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.MonsterData;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.Position;

import java.util.List;

@Command(label = "spawn", usage = "spawn <entityId|entityName> [level] [amount]",
        description = "Spawns an entity near you", permission = "server.spawn")
public final class SpawnCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, "Run this command in-game.");
            return;
        }

        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, "Usage: spawn <entityId|entityName> [amount]");
            return;
        }

        try {
            int entity = Integer.parseInt(args.get(0));
            int level = args.size() > 1 ? Integer.parseInt(args.get(1)) : 1;
            int amount = args.size() > 2 ? Integer.parseInt(args.get(2)) : 1;

            MonsterData entityData = GameData.getMonsterDataMap().get(entity);
            if (entityData == null) {
                CommandHandler.sendMessage(sender, "Invalid entity id.");
                return;
            }

            float range = (5f + (.1f * amount));
            for (int i = 0; i < amount; i++) {
                Position pos = sender.getPos().clone().addX((float) (Math.random() * range) - (range / 2)).addY(3f).addZ((float) (Math.random() * range) - (range / 2));
                EntityMonster monster = new EntityMonster(sender.getScene(), entityData, pos, level);
                sender.getScene().addEntity(monster);
            }
            CommandHandler.sendMessage(sender, String.format("Spawned %s of %s.", amount, entity));
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, "Invalid item or player ID.");
        }
    }
}
