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

@Command(label = "giveart", usage = "giveart [player] <artifactId> <mainPropId> [<appendPropId>[,<times>]]... [level]", description = "Gives the player a specified artifact", aliases = {"gart"}, permission = "player.giveart")
public final class GiveArtifactCommand implements CommandHandler {
	@Override
	public void execute(Player sender, List<String> args) {
		int size = args.size(), target, itemId, mainPropId, level = 1;
		ArrayList<Integer> appendPropIdList = new ArrayList<>();
		String msg = Grasscutter.getLanguage().GiveArtifact_usage;

		if (sender == null && size < 2) {
			CommandHandler.sendMessage(null, msg);
			return;
		}

		if (size >= 2) {
			try {
				try {
					int last = Integer.parseInt(args.get(size - 1));
					if (last >= 1 && last <= 21) {
						level = last;
						size--;
					}
				} catch (NumberFormatException ignored) {
				}
				target = Integer.parseInt(args.get(0));
				int fromIdx;
				if (Grasscutter.getGameServer().getPlayerByUid(target) == null && sender != null) {
					target = sender.getUid();
					itemId = Integer.parseInt(args.get(0));
					mainPropId = Integer.parseInt(args.get(1));
					fromIdx = 2;
				} else {
					target = Integer.parseInt(args.get(0));
					itemId = Integer.parseInt(args.get(1));
					mainPropId = Integer.parseInt(args.get(2));
					fromIdx = 3;
				}
				args.subList(fromIdx, size).forEach(it -> {
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
				CommandHandler.sendMessage(sender, msg);
				return;
			}
		} else {
			CommandHandler.sendMessage(sender, msg);
			return;
		}

		Player targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);
		if (targetPlayer == null) {
			CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Player_not_found);
			return;
		}

		ItemData itemData = GameData.getItemDataMap().get(itemId);

		if (itemData.getItemType() != ItemType.ITEM_RELIQUARY) {
			CommandHandler.sendMessage(sender, Grasscutter.getLanguage().GiveArtifact_invalid_artifact_id);
			return;
		}

		GameItem item = new GameItem(itemData);
		item.setLevel(level);
		item.setMainPropId(mainPropId);
		item.getAppendPropIdList().clear();
		item.getAppendPropIdList().addAll(appendPropIdList);
		targetPlayer.getInventory().addItem(item, ActionReason.SubfieldDrop);

		CommandHandler.sendMessage(sender, Grasscutter.getLanguage().GiveArtifact_given.replace("{itemId}", Integer.toString(itemId)).replace("target", Integer.toString(target)));
	}
}

