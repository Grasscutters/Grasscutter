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
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.server.packet.send.PacketItemAddHintNotify;

import java.util.LinkedList;
import java.util.List;

@Command(label = "giveall", usage = "giveall [player] <amount>",
        description = "Gives All item to you or the specified player", aliases = {"givea"}, permission = "player.giveall")
public class GiveAllCommand implements CommandHandler {

    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        int target,amount=99999;

        switch (args.size()) {
            default: // giveall *no args*
                target = sender.getUid();
                break;
            case 1: //[player]
                try {
                    target = Integer.parseInt(args.get(0));
                    if (Grasscutter.getGameServer().getPlayerByUid(target) == null) {
                        CommandHandler.sendMessage(sender, "Invalid player ID.");
                        return;
                    }
                }catch (NumberFormatException ignored){
                    CommandHandler.sendMessage(sender, "Invalid amount or player ID.");
                    return;
                }
                break;
            case 2: //[player] [amount]
                try {
                    target = Integer.parseInt(args.get(0));
                    if (Grasscutter.getGameServer().getPlayerByUid(target) == null && sender != null) {
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
        CommandHandler.sendMessage(sender, String.format("Get All Items Done."));
    }

    public void GetAllItem(GenshinPlayer player, int amount){
        CommandHandler.sendMessage(player, "Get All Items...");
        for (ItemData itemdata: GenshinData.getItemDataMap().values()) {
            if(itemdata.getId() > 1000 && itemdata.getId() <= 2000)continue;//is avatar
            if (itemdata.isEquip()) {
                List<GenshinItem> items = new LinkedList<>();
                for (int i = 0; i < 20; i++) {
                    items.add(new GenshinItem(itemdata));
                }
                player.getInventory().addItems(items);
                player.sendPacket(new PacketItemAddHintNotify(items, ActionReason.SubfieldDrop));
            } else {
                GenshinItem genshinItem = new GenshinItem(itemdata);
                genshinItem.setCount(amount);
                player.getInventory().addItem(genshinItem);
                player.sendPacket(new PacketItemAddHintNotify(genshinItem, ActionReason.SubfieldDrop));
            }
        }
        for(AvatarData avatarData:GenshinData.getAvatarDataMap().values())
        {
            int ascension;
            int level = 90;
            // Calculate ascension level.
            if (level <= 40) {
                ascension = (int) Math.ceil(90 / 20f);
            } else {
                ascension = (int) Math.ceil(90 / 10f) - 3;
            }

            GenshinAvatar avatar = new GenshinAvatar(avatarData);
            avatar.setLevel(level);
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
