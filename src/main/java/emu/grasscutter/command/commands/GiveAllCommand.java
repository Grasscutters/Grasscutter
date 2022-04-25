package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GenshinData;
import emu.grasscutter.data.def.AvatarData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.game.inventory.ItemType;

import java.util.*;

@Command(label = "giveall", usage = "giveall [player] <amount>",
        description = "Gives all items", aliases = {"givea"}, permission = "player.giveall",threading = true)
public class GiveAllCommand implements CommandHandler {
    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        int target,amount=99999;

        if(sender == null){
            CommandHandler.sendMessage(null, "Run this command in-game");
            return;
        }
        switch (args.size()) {
            default: // *no args*
                target = sender.getUid();
                break;
            case 1: // [player]
                try {
                    target = Integer.parseInt(args.get(0));
                    if (Grasscutter.getGameServer().getPlayerByUid(target) == null) {
                        CommandHandler.sendMessage(sender, "Invalid player ID.");
                        return;
                    }
                }catch (NumberFormatException ignored){
                    CommandHandler.sendMessage(sender, "Invalid player ID.");
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
                    CommandHandler.sendMessage(sender, "Invalid amount or player ID.");
                    return;
                }
                break;
        }

        GenshinPlayer targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, "Player not found.");
            return;
        }

        this.GetAllItem(targetPlayer,amount);
        CommandHandler.sendMessage(sender, "Done! or Getting all items done");
    }

    public void GetAllItem(GenshinPlayer player, int amount){
        CommandHandler.sendMessage(player, "Getting all item....");

        Collection<GenshinItem> genshinItemList =new LinkedList<>();
        for (ItemData itemdata: GenshinData.getItemDataMap().values()) {
            if(itemdata.getId() > 1000 && itemdata.getId() <= 1099)continue;//is avatar
            if (itemdata.isEquip()) {
                for (int i = 0; i < 20; i++) {
                    GenshinItem genshinItem = new GenshinItem(itemdata);
                    if(itemdata.getItemType() == ItemType.ITEM_WEAPON){
                        genshinItem.setLevel(90);
                        genshinItem.setPromoteLevel(6);
                    }
                    genshinItemList.add(genshinItem);
                }
            } else {
                GenshinItem genshinItem = new GenshinItem(itemdata);
                genshinItem.setCount(amount);
                genshinItemList.add(genshinItem);
            }
        }
        player.getInventory().addItems(genshinItemList);

        for(AvatarData avatarData:GenshinData.getAvatarDataMap().values()) {
            if(avatarData.getId() == 10000001 || avatarData.getId() >= 10000099)continue;
            // Calculate ascension level.
            int ascension = (int) Math.ceil(90 / 10f) - 3;
            GenshinAvatar avatar = new GenshinAvatar(avatarData);
            avatar.setLevel(90);
            avatar.setPromoteLevel(ascension);
            for (int i = 1;i<=6;i++){
                avatar.getTalentIdList().add((avatar.getAvatarId()-10000000)*10+i);//(10000058-10000000)*10+i
            }
            // This will handle stats and talents
            avatar.recalcStats();
            player.addAvatar(avatar);
        }
    }

}
