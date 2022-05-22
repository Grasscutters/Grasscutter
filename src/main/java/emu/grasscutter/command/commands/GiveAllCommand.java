package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.AvatarData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.inventory.MaterialType;
import emu.grasscutter.game.player.Player;

import java.util.*;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "giveall", usage = "giveall", aliases = {"givea"}, permission = "player.giveall", permissionTargeted = "player.giveall.others",threading = true, description = "commands.giveAll.description")
public final class GiveAllCommand implements CommandHandler {

  @Override
  public void execute(Player sender, Player targetPlayer, List<String> args) {

    if (targetPlayer == null) {
      CommandHandler.sendMessage(sender, translate(sender, "commands.execution.need_target"));
      return;
    }

    // Check Username
    Account account = Grasscutter.getGameServer().getAccountByName(targetPlayer.getAccount().getUsername());
    if (account == null) {
      CommandHandler.sendMessage(sender, translate(sender, "commands.permission.account_error"));
      return;
    }
    
    account.addPermission("-player.giveall");
    this.giveAllItems(targetPlayer);
    CommandHandler.sendMessage(sender, translate("commands.giveAll.success", targetPlayer.getNickname()));
  }

  public void giveAllItems(Player player) {

    CommandHandler.sendMessage(player, translate("commands.giveAll.started"));

    // some test items
    List<GameItem> itemList = new ArrayList<>();
    
    for (AvatarData avatarData : GameData.getAvatarDataMap().values()) {

      // Exclude test avatar
      if (isTestAvatar(avatarData.getId()))
       continue;

      Avatar avatar = new Avatar(avatarData);
      avatar.setLevel(90);
      avatar.setPromoteLevel(6);
      for (int i = 1; i <= 6; ++i) {
        avatar.getTalentIdList().add((avatar.getAvatarId() - 10000000) * 10 + i);
      }
      // This will handle stats and talents
      avatar.recalcStats();
      player.addAvatar(avatar);

    }    
    
    for (ItemData itemdata : GameData.getItemDataMap().values()) {

      int limitwp=3;
      int limitaft=1;
      int tmp=1000;

      // Exclude test item
      if (isTestItem(itemdata.getId()))
        continue;

      if (itemdata.isEquip()) {

        if (itemdata.getItemType() == ItemType.ITEM_WEAPON) {
          for (int i = 0; i < limitwp; ++i) {
            GameItem item = new GameItem(itemdata);
            item.setLevel(90);
            item.setPromoteLevel(6);
            item.setRefinement(4);
            itemList.add(item);
          }
        } else {
          GameItem item = new GameItem(itemdata);
          // Artifact
          if(itemdata.getItemType() == ItemType.ITEM_RELIQUARY){
            if(itemdata.getRankLevel() == 5){
              item.setLevel(itemdata.getMaxLevel());
              //item.setLocked(true);
              //item.setTotalExp(totalExp);
            }else{
              continue;
            }
            tmp=limitaft;
          }else{
           // Set Mx
           if(itemdata.getStackLimit() != 0){
            tmp=itemdata.getStackLimit();
           }
          }
          item.setCount(tmp);
          itemList.add(item);
        }

      } else {        
        
        if (itemdata.getMaterialType() == MaterialType.MATERIAL_QUEST)
         continue;
         
        if (itemdata.getMaterialType() == MaterialType.MATERIAL_FURNITURE_SUITE_FORMULA || itemdata.getMaterialType() == MaterialType.MATERIAL_FURNITURE_FORMULA)
         continue; 

        // CODEX_WIDGET aka Gadget 
        if(itemdata.getItemType() == ItemType.ITEM_MATERIAL){
          if(itemdata.getMaterialType() == MaterialType.MATERIAL_WIDGET){
            tmp=itemdata.getStackLimit();
          }
        }

        GameItem item = new GameItem(itemdata);
        item.setCount(tmp);
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
      210, 211, 314, 315, 317, 1005, 1007, 1105, 1107, 1201, 1202, 10366,
      101212, 11411, 11506, 11507, 11508, 12505, 12506, 12508, 12509, 13503,
      13506, 14411, 14503, 14505, 14508, 15411, 15504, 15505, 15506, 15508,
      20001, 10002, 10003, 10004, 10005, 10006, 10008, 100231, 100232, 100431,
      101689, 105001, 105004, 106000, 106001, 108000, 110000
  };

  private static final Collection<Integer> testItemsList = Arrays.asList(testItemsIds);

}
