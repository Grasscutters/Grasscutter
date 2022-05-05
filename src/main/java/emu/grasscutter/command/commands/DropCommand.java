package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.game.entity.EntityItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.Position;

import java.util.List;

@Command(label = "drop", usage = "drop <itemId|itemName> [amount]", description = "Drops an item near you", aliases = {
    "d", "dropitem" }, permission = "server.drop")
public final class DropCommand implements CommandHandler {

  @Override
  public void execute(Player sender, Player targetPlayer, List<String> args) {
    if (targetPlayer == null) {
      CommandHandler.sendMessage(null, Grasscutter.getLanguage().Target_needed);
      return;
    }

    int item = 0;
    int amount = 1;

    switch (args.size()) {
      case 2:
        try {
          amount = Integer.parseInt(args.get(1));
        } catch (NumberFormatException ignored) {
          CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_amount);
          return;
        } // Slightly cheeky here: no break so it falls through to initialize the first
          // argument too
      case 1:
        try {
          item = Integer.parseInt(args.get(0));
        } catch (NumberFormatException ignored) {
          CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_item_id);
          return;
        }
        break;
      default:
        CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Drop_usage);
        return;
    }

    if (amount > Grasscutter.getConfig().getGameServerOptions().CMD_Drop) {
      CommandHandler.sendMessage(sender, "Overmuch :(");
      return;
    }

    ItemData itemData = GameData.getItemDataMap().get(item);
    if (itemData == null) {
      CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_item_id);
      return;
    }
    
    if (itemData.isEquip()) {
      float range = (5f + (.1f * amount));
      for (int i = 0; i < amount; i++) {
        Position pos = targetPlayer.getPos().clone().addX((float) (Math.random() * range) - (range / 2)).addY(3f)
            .addZ((float) (Math.random() * range) - (range / 2));
        EntityItem entity = new EntityItem(targetPlayer.getScene(), targetPlayer, itemData, pos, 1);
        targetPlayer.getScene().addEntity(entity);
      }
    } else {
      EntityItem entity = new EntityItem(targetPlayer.getScene(), targetPlayer, itemData,
          targetPlayer.getPos().clone().addY(3f), amount);
      targetPlayer.getScene().addEntity(entity);
    }
    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Drop_dropped_of
        .replace("{amount}", Integer.toString(amount)).replace("{item}", Integer.toString(item)));
  }
}