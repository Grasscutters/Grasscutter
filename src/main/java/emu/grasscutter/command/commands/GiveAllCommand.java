package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.AvatarData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.player.Player;

import java.util.*;

@Command(label = "giveall", usage = "giveall [player] [amount]",
        description = "Gives all items", aliases = {"givea"}, permission = "player.giveall", threading = true)
public final class GiveAllCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        int target, amount = 99999;

        switch (args.size()) {
            case 0: // *no args*
                if (sender == null) {
                    CommandHandler.sendMessage(null, Grasscutter.getLanguage().Run_this_command_in_game);
                    return;
                }
                target = sender.getUid();
                break;

            case 1: // [player]
                try {
                    target = Integer.parseInt(args.get(0));
                    if (Grasscutter.getGameServer().getPlayerByUid(target) == null) {
                        CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_playerId);
                        return;
                    }
                }catch (NumberFormatException ignored){
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_playerId);
                    return;
                }
                break;

            case 2: // [player] [amount]
                try {
                    target = Integer.parseInt(args.get(0));
                    if (Grasscutter.getGameServer().getPlayerByUid(target) == null) {
                        target = sender.getUid();
                        amount = Integer.parseInt(args.get(0));
                    } else {
                        amount = Integer.parseInt(args.get(1));
                    }
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().GiveAll_invalid_amount_or_playerId);
                    return;
                }
                break;

            default: // invalid
                CommandHandler.sendMessage(null, Grasscutter.getLanguage().GiveAll_usage);
                return;
        }

        Player targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Player_not_found);
            return;
        }

        this.giveAllItems(targetPlayer, amount);
        CommandHandler.sendMessage(sender, Grasscutter.getLanguage().GiveAll_done);
    }

    public void giveAllItems(Player player, int amount) {
        CommandHandler.sendMessage(player, Grasscutter.getLanguage().GiveAll_item);

        for (AvatarData avatarData: GameData.getAvatarDataMap().values()) {
            //Exclude test avatar
            if (isTestAvatar(avatarData.getId())) continue;

            Avatar avatar = new Avatar(avatarData);
            avatar.setLevel(90);
            avatar.setPromoteLevel(6);
            for(int i = 1;i <= 6;++i){
                avatar.getTalentIdList().add((avatar.getAvatarId()-10000000)*10+i);
            }
            // This will handle stats and talents
            avatar.recalcStats();
            player.addAvatar(avatar);
        }

        //some test items
        List<GameItem> itemList = new ArrayList<>();
        for (ItemData itemdata: GameData.getItemDataMap().values()) {
            //Exclude test item
            if (isTestItem(itemdata.getId())) continue;

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
            }
            else {
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
            }
            else {
                player.getInventory().addItems(itemList.subList(i * number + offset, (i + 1) * number + offset));
            }
        }
    }

    public boolean isTestAvatar(int avatarId) {
        return avatarId < 10000002 || avatarId >= 11000000;
    }

    public boolean isTestItem(int itemId) {
        for (Range range: testItemRanges) {
            if (range.check(itemId)) {
                return true;
            }
        }

        return testItemsList.contains(itemId);
    }

    static class Range {
        private final int min, max;

        public Range(int min, int max) {
            if(min > max){
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

    private static final Range[] testItemRanges = new Range[] {
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
    private static final Integer[] testItemsIds = new Integer[] {
            210, 211, 314, 315, 317, 1005, 1007, 1105, 1107, 1201, 1202,10366,
            101212, 11411, 11506, 11507, 11508, 12505, 12506, 12508, 12509, 13503,
            13506, 14411, 14503, 14505, 14508, 15411, 15504, 15505, 15506, 15508,
            20001, 10002, 10003, 10004, 10005, 10006, 10008,100231,100232,100431,
            101689,105001,105004, 106000,106001,108000,110000
    };

    private static final Collection<Integer> testItemsList = Arrays.asList(testItemsIds);

}

