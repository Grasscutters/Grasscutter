package emu.grasscutter.command.commands;

import emu.grasscutter.GameConstants;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.AvatarData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "giveall", usage = "giveall [amount]", aliases = {"givea"}, permission = "player.giveall", permissionTargeted = "player.giveall.others", threading = true, description = "commands.giveAll.description")
public final class GiveAllCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        int amount = 99999;

        switch (args.size()) {
            case 0:
                break;
            case 1: // [amount]
                try {
                    amount = Integer.parseInt(args.get(0));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, translate(sender, "commands.generic.invalid.amount"));
                    return;
                }
                break;
            default: // invalid
                CommandHandler.sendMessage(sender, translate(sender, "commands.giveAll.usage"));
                return;
        }

        this.giveAllItems(targetPlayer, amount);
        CommandHandler.sendMessage(sender, translate(targetPlayer, "commands.giveAll.success", targetPlayer.getNickname()));
    }

    public void giveAllItems(Player player, int amount) {
        CommandHandler.sendMessage(player, translate(player, "commands.giveAll.started"));

        for (AvatarData avatarData : GameData.getAvatarDataMap().values()) {
            //Exclude test avatar
            if (this.isTestAvatar(avatarData.getId())) continue;

            Avatar avatar = new Avatar(avatarData);
            avatar.setLevel(90);
            avatar.setPromoteLevel(6);

            // Add constellations.
            int talentBase = switch (avatar.getAvatarId()) {
                case 10000005 -> 70;
                case 10000006 -> 40;
                default -> (avatar.getAvatarId() - 10000000) * 10;
            };

            for (int i = 1; i <= 6; ++i) {
                avatar.getTalentIdList().add(talentBase + i);
            }

            // Handle skill depot for traveller.
            if (avatar.getAvatarId() == GameConstants.MAIN_CHARACTER_MALE) {
                avatar.setSkillDepotData(GameData.getAvatarSkillDepotDataMap().get(504));
            } else if (avatar.getAvatarId() == GameConstants.MAIN_CHARACTER_FEMALE) {
                avatar.setSkillDepotData(GameData.getAvatarSkillDepotDataMap().get(704));
            }

            // This will handle stats and talents
            avatar.recalcStats();
            // Don't try to add each avatar to the current team
            player.addAvatar(avatar, false);
        }

        //some test items
        List<GameItem> itemList = new ArrayList<>();
        for (ItemData itemdata : GameData.getItemDataMap().values()) {
            //Exclude test item
            if (this.isTestItem(itemdata.getId())) continue;

            if (itemdata.isEquip()) {
                if (itemdata.getItemType() == ItemType.ITEM_WEAPON) {
                    for (int i = 0; i < 5; ++i) {
                        GameItem item = new GameItem(itemdata);
                        item.setLevel(90);
                        item.setPromoteLevel(6);
                        item.setRefinement(4);
                        itemList.add(item);
                    }
                }
            } else {
                GameItem item = new GameItem(itemdata);
                item.setCount(amount);
                itemList.add(item);
            }
        }
        int packetNum = 10;
        int itemLength = itemList.size();
        int number = itemLength / packetNum;
        int remainder = itemLength % packetNum;
        int offset = 0;
        for (int i = 0; i < packetNum; ++i) {
            if (remainder > 0) {
                player.getInventory().addItems(itemList.subList(i * number + offset, (i + 1) * number + offset + 1));
                --remainder;
                ++offset;
            } else {
                player.getInventory().addItems(itemList.subList(i * number + offset, (i + 1) * number + offset));
            }
        }
    }

    public boolean isTestAvatar(int avatarId) {
        return avatarId < 10000002 || avatarId >= 11000000;
    }

    public boolean isTestItem(int itemId) {
        for (Range range : testItemRanges) {
            if (range.check(itemId)) {
                return true;
            }
        }

        return testItemsList.contains(itemId);
    }

    static class Range {
        private final int min, max;

        public Range(int min, int max) {
            if (min > max) {
                min ^= max;
                max ^= min;
                min ^= max;
            }

            this.min = min;
            this.max = max;
        }

        public boolean check(int value) {
            return value >= this.min && value <= this.max;
        }
    }

    private static final Range[] testItemRanges = new Range[]{
        new Range(106, 139),
        new Range(1000, 1099),
        new Range(2001, 3022),
        new Range(23300, 23340),
        new Range(23383, 23385),
        new Range(78310, 78554),
        new Range(99310, 99554),
        new Range(100001, 100187),
        new Range(100210, 100214),
        new Range(100303, 100398),
        new Range(100414, 100425),
        new Range(100454, 103008),
        new Range(109000, 109492),
        new Range(115001, 118004),
        new Range(141001, 141072),
        new Range(220050, 221016),
    };
    private static final Integer[] testItemsIds = new Integer[]{
        210, 211, 314, 315, 317, 1005, 1007, 1105, 1107, 1201, 1202, 10366,
        101212, 11411, 11506, 11507, 11508, 12505, 12506, 12508, 12509, 13503,
        13506, 14411, 14503, 14505, 14508, 15504, 15505, 15506,
        20001, 10002, 10003, 10004, 10005, 10006, 10008, 100231, 100232, 100431,
        101689, 105001, 105004, 106000, 106001, 108000, 110000
    };

    private static final Collection<Integer> testItemsList = Arrays.asList(testItemsIds);

}

