package emu.grasscutter.game.managers;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.game.home.FurnitureMakeSlotItem;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.BasePlayerManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ItemUseOp;
import emu.grasscutter.net.proto.ItemParamOuterClass;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class FurnitureManager extends BasePlayerManager {

    public FurnitureManager(Player player) {
        super(player);
    }

    public void onLogin() {
        notifyUnlockFurniture();
        notifyUnlockFurnitureSuite();
    }

    public void notifyUnlockFurniture() {
        player.getSession().send(new PacketUnlockedFurnitureFormulaDataNotify(player.getUnlockedFurniture()));
    }

    public void notifyUnlockFurnitureSuite() {
        player.getSession().send(new PacketUnlockedFurnitureSuiteDataNotify(player.getUnlockedFurnitureSuite()));
    }

    public synchronized boolean unlockFurnitureOrSuite(GameItem useItem) {
        ItemUseOp itemUseOp = useItem.getItemData().getItemUse().get(0).getUseOp();
        
        // Check
        if (itemUseOp != ItemUseOp.ITEM_USE_UNLOCK_FURNITURE_SUITE && itemUseOp != ItemUseOp.ITEM_USE_UNLOCK_FURNITURE_FORMULA) {
            return false;
        }

        int furnitureIdOrSuiteId = Integer.parseInt(useItem.getItemData().getItemUse().get(0).getUseParam()[0]);

        // Remove first
        player.getInventory().removeItem(useItem, 1);

        if (useItem.getItemData().getItemUse().get(0).getUseOp() == ItemUseOp.ITEM_USE_UNLOCK_FURNITURE_FORMULA) {
            player.getUnlockedFurniture().add(furnitureIdOrSuiteId);
            notifyUnlockFurniture();
        }else {
            player.getUnlockedFurnitureSuite().add(furnitureIdOrSuiteId);
            notifyUnlockFurnitureSuite();
        }
        return true;
    }

    public void startMake(int makeId, int avatarId) {
        var makeData = GameData.getFurnitureMakeConfigDataMap().get(makeId);
        if (makeData == null) {
            player.getSession().send(new PacketFurnitureMakeStartRsp(Retcode.RET_FURNITURE_MAKE_CONFIG_ERROR_VALUE, null));
            return;
        }

        // check slot count
        if (player.getHome().getLevelData().getFurnitureMakeSlotCount() <= player.getHome().getFurnitureMakeSlotItemList().size()) {
            player.getSession().send(new PacketFurnitureMakeStartRsp(Retcode.RET_FURNITURE_MAKE_SLOT_FULL_VALUE, null));
            return;
        }

        // pay items first
        if (!player.getInventory().payItems(makeData.getMaterialItems().toArray(new ItemParamData[0]))) {
            player.getSession().send(new PacketFurnitureMakeStartRsp(Retcode.RET_HOME_FURNITURE_COUNT_NOT_ENOUGH_VALUE, null));
            return;
        }

        var furnitureSlot = FurnitureMakeSlotItem.of()
                .avatarId(avatarId)
                .makeId(makeId)
                .beginTime(Utils.getCurrentSeconds())
                .durTime(makeData.getMakeTime())
                .build();

        // add furniture make task
        player.getHome().getFurnitureMakeSlotItemList().add(furnitureSlot);
        player.getSession().send(new PacketFurnitureMakeStartRsp(Retcode.RET_SUCC_VALUE,
                player.getHome().getFurnitureMakeSlotItemList().stream()
                        .map(FurnitureMakeSlotItem::toProto)
                        .toList()
        ));

        player.getHome().save();
    }

    public void queryStatus() {
        if (player.getHome().getFurnitureMakeSlotItemList() == null) {
            player.getHome().setFurnitureMakeSlotItemList(new ArrayList<>());
        }

        player.sendPacket(new PacketFurnitureMakeRsp(player.getHome()));
    }


    public void take(int index, int makeId, boolean isFastFinish) {
        var makeData = GameData.getFurnitureMakeConfigDataMap().get(makeId);
        if (makeData == null) {
            player.getSession().send(new PacketTakeFurnitureMakeRsp(Retcode.RET_FURNITURE_MAKE_CONFIG_ERROR_VALUE, makeId, null, null));
            return;
        }

        var slotItem = player.getHome().getFurnitureMakeSlotItemList().stream()
                .filter(x -> x.getIndex() == index && x.getMakeId() == makeId)
                .findFirst();

        if (slotItem.isEmpty()) {
            player.getSession().send(new PacketTakeFurnitureMakeRsp(Retcode.RET_FURNITURE_MAKE_NO_MAKE_DATA_VALUE, makeId, null, null));
            return;
        }

        // pay the speedup item
        if (isFastFinish && !player.getInventory().payItem(107013,1)) {
            player.getSession().send(new PacketTakeFurnitureMakeRsp(Retcode.RET_FURNITURE_MAKE_UNFINISH_VALUE, makeId, null, null));
            return;
        }

        // check if player can take
//        if (slotItem.get().getBeginTime() + slotItem.get().getDurTime() >= Utils.getCurrentSeconds() && !isFastFinish) {
//            player.getSession().send(new PacketTakeFurnitureMakeRsp(Retcode.RET_FURNITURE_MAKE_UNFINISH_VALUE, makeId, null, null));
//            return;
//        }

        player.getInventory().addItem(makeData.getFurnitureItemID(), makeData.getCount());
        player.getHome().getFurnitureMakeSlotItemList().remove(slotItem.get());

        player.getSession().send(new PacketTakeFurnitureMakeRsp(Retcode.RET_SUCC_VALUE, makeId,
                List.of(ItemParamOuterClass.ItemParam.newBuilder()
                                .setItemId(makeData.getFurnitureItemID())
                                .setCount(makeData.getCount())
                        .build()),
                player.getHome().getFurnitureMakeSlotItemList().stream()
                        .map(FurnitureMakeSlotItem::toProto)
                        .toList()
                ));
        player.getHome().save();
    }
}
