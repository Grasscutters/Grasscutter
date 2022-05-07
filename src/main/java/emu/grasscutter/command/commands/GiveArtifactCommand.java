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
import java.util.Collections;
import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "giveart", usage = "giveart <artifactId> <mainPropId> [<appendPropId>[,<times>]]... [level]", description = "Gives the player a specified artifact", aliases = {"gart"}, permission = "player.giveart")
public final class GiveArtifactCommand implements CommandHandler {
	@Override
	public void execute(Player sender, Player targetPlayer, List<String> args) {
		if (targetPlayer == null) {
			CommandHandler.sendMessage(sender, translate("commands.execution.need_target"));
			return;
		}

		if (args.size() < 2) {
			CommandHandler.sendMessage(sender, translate("commands.giveArtifact.usage"));
			return;
		}

		int itemId;
		try {
			itemId = Integer.parseInt(args.remove(0));
		} catch (NumberFormatException ignored) {
			CommandHandler.sendMessage(sender, translate("commands.giveArtifact.id_error"));
			return;
		}
		ItemData itemData = GameData.getItemDataMap().get(itemId);
		if (itemData.getItemType() != ItemType.ITEM_RELIQUARY) {
			CommandHandler.sendMessage(sender, translate("commands.giveArtifact.id_error"));
			return;
		}

		int mainPropId;
		try {
			mainPropId = Integer.parseInt(args.remove(0));
		} catch (NumberFormatException ignored) {
			CommandHandler.sendMessage(sender, translate("commands.generic.execution.argument_error"));
			return;
		}

		int level = 1;
		try {
			int last = Integer.parseInt(args.get(args.size()-1));
			if (last > 0 && last < 22) {  // Luckily appendPropIds aren't in the range of [1,21] 
				level = last;
				args.remove(args.size()-1);
			}
		} catch (NumberFormatException ignored) {  // Could be a stat,times string so no need to panic
		}

		List<Integer> appendPropIdList = new ArrayList<>();
		try {
			args.forEach(it -> {
				String[] arr;
				int n = 1;
				if ((arr = it.split(",")).length == 2) {
					it = arr[0];
					n = Integer.parseInt(arr[1]);
					if (n > 200) {
						n = 200;
					}
				}
				appendPropIdList.addAll(Collections.nCopies(n, Integer.parseInt(it)));
			});
		} catch (Exception ignored) {
			CommandHandler.sendMessage(sender, translate("commands.execution.argument_error"));
			return;
		}

		GameItem item = new GameItem(itemData);
		item.setLevel(level);
		item.setMainPropId(mainPropId);
		item.getAppendPropIdList().clear();
		item.getAppendPropIdList().addAll(appendPropIdList);
		targetPlayer.getInventory().addItem(item, ActionReason.SubfieldDrop);

		CommandHandler.sendMessage(sender, translate("commands.giveArtifact.success", Integer.toString(itemId), Integer.toString(targetPlayer.getUid())));
	}
}

