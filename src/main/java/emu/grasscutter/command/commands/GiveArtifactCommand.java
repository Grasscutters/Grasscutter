package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Command(label = "giveart", usage = "giveart|givea [player] <artifactId> <mainPropId> [<appendPropId>...] [level]",
		description = "Gives the player a specified reliquary", aliases = {"givea"}, permission = "player.giveart")
public final class GiveArtifactCommand implements CommandHandler {
	@Override
	public void execute(Player sender, List<String> args) {
		int target, itemId, mainPropId, level;
		ArrayList<Integer> appendPropIdList = new ArrayList<>();
		String msg = "Usage: giveart|givea [player] <artifactId> <mainPropId> [<appendPropId>...] [level]";

		if (sender == null && args.size() < 2) {
			CommandHandler.sendMessage(null, msg);
			return;
		}

		int size = args.size();
		if (size >= 2) {
			try {
				level = Integer.parseInt(args.get(size - 1));
				if (level <= 21) size -= 1;
				else level = 1;
				target = Integer.parseInt(args.get(0));
				if (Grasscutter.getGameServer().getPlayerByUid(target) == null && sender != null) {
					target = sender.getUid();
					itemId = Integer.parseInt(args.get(0));
					mainPropId = Integer.parseInt(args.get(1));
					args.subList(2, size).stream().flatMapToInt(num -> IntStream.of(Integer.parseInt(num))).forEach(appendPropIdList::add);
				} else {
					target = Integer.parseInt(args.get(0));
					itemId = Integer.parseInt(args.get(1));
					mainPropId = Integer.parseInt(args.get(2));
					args.subList(3, size).stream().flatMapToInt(num -> IntStream.of(Integer.parseInt(num))).forEach(appendPropIdList::add);
				}
			} catch (NumberFormatException | IndexOutOfBoundsException ignored) {
				CommandHandler.sendMessage(sender, msg);
				return;
			}
		} else {
			CommandHandler.sendMessage(sender, msg);
			return;
		}

		Player targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);
		if (targetPlayer == null) {
			CommandHandler.sendMessage(sender, "Player not found.");
			return;
		}

		ItemData itemData = GameData.getItemDataMap().get(itemId);

		if (itemData.getItemType() != ItemType.ITEM_RELIQUARY) {
			CommandHandler.sendMessage(sender, "Invalid artifact ID.");
			return;
		}

		GameItem item = new GameItem(itemData);
		item.setLevel(level);
		item.setMainPropId(mainPropId);
		item.getAppendPropIdList().addAll(appendPropIdList);
		targetPlayer.getInventory().addItem(item, ActionReason.SubfieldDrop);

		CommandHandler.sendMessage(sender, String.format("Given %s to %s.", itemId, target));
	}
}

