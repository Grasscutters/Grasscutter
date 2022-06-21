package emu.grasscutter.game.managers;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.game.home.FurnitureMakeSlotItem;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.ItemParamOuterClass;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class FurnitureManager {
    private final Player player;

    public FurnitureManager(Player player) {
        this.player = player;
    }

    public void onLogin() {
        this.notifyUnlockFurniture();
        this.notifyUnlockFurnitureSuite();
    }

    public void notifyUnlockFurniture() {
        this.player.getSession().send(new PacketUnlockedFurnitureFormulaDataNotify(this.player.getUnlockedFurniture()));
    }

    public void notifyUnlockFurnitureSuite() {
        this.player.getSession().send(new PacketUnlockedFurnitureSuiteDataNotify(this.player.getUnlockedFurnitureSuite()));
    }

    public synchronized boolean unlockFurnitureOrSuite(GameItem useItem) {
        // Check
        if (!List.of("ITEM_USE_UNLOCK_FURNITURE_FORMULA", "ITEM_USE_UNLOCK_FURNITURE_SUITE")
            .contains(useItem.getItemData().getItemUse().get(0).getUseOp())) {
            return false;
        }

        int furnitureIdOrSuiteId = Integer.parseInt(useItem.getItemData().getItemUse().get(0).getUseParam().get(0));

        // Remove first
        this.player.getInventory().removeItem(useItem, 1);

        if ("ITEM_USE_UNLOCK_FURNITURE_FORMULA".equals(useItem.getItemData().getItemUse().get(0).getUseOp())) {
            this.player.getUnlockedFurniture().add(furnitureIdOrSuiteId);
            this.notifyUnlockFurniture();
        } else {
            this.player.getUnlockedFurnitureSuite().add(furnitureIdOrSuiteId);
            this.notifyUnlockFurnitureSuite();
        }
        return true;
    }

    public void startMake(int makeId, int avatarId) {
        var makeData = GameData.getFurnitureMakeConfigDataMap().get(makeId);
        if (makeData == null) {
            this.player.getSession().send(new PacketFurnitureMakeStartRsp(Retcode.RET_FURNITURE_MAKE_CONFIG_ERROR_VALUE, null));
            return;
        }

        // check slot count
        if (this.player.getHome().getLevelData().getFurnitureMakeSlotCount() <= this.player.getHome().getFurnitureMakeSlotItemList().size()) {
            this.player.getSession().send(new PacketFurnitureMakeStartRsp(Retcode.RET_FURNITURE_MAKE_SLOT_FULL_VALUE, null));
            return;
        }

        // pay items first
        if (!this.player.getInventory().payItems(makeData.getMaterialItems().toArray(new ItemParamData[0]))) {
            this.player.getSession().send(new PacketFurnitureMakeStartRsp(Retcode.RET_HOME_FURNITURE_COUNT_NOT_ENOUGH_VALUE, null));
            return;
        }

        var furnitureSlot = FurnitureMakeSlotItem.of()
            .avatarId(avatarId)
            .makeId(makeId)
            .beginTime(Utils.getCurrentSeconds())
            .durTime(makeData.getMakeTime())
            .build();

        // add furniture make task
        this.player.getHome().getFurnitureMakeSlotItemList().add(furnitureSlot);
        this.player.getSession().send(new PacketFurnitureMakeStartRsp(Retcode.RET_SUCC_VALUE,
            this.player.getHome().getFurnitureMakeSlotItemList().stream()
                .map(FurnitureMakeSlotItem::toProto)
                .toList()
        ));

        this.player.getHome().save();
    }

    public void queryStatus() {
        if (this.player.getHome().getFurnitureMakeSlotItemList() == null) {
            this.player.getHome().setFurnitureMakeSlotItemList(new ArrayList<>());
        }

        this.player.sendPacket(new PacketFurnitureMakeRsp(this.player.getHome()));
    }


    public void take(int index, int makeId, boolean isFastFinish) {
        var makeData = GameData.getFurnitureMakeConfigDataMap().get(makeId);
        if (makeData == null) {
            this.player.getSession().send(new PacketTakeFurnitureMakeRsp(Retcode.RET_FURNITURE_MAKE_CONFIG_ERROR_VALUE, makeId, null, null));
            return;
        }

        var slotItem = this.player.getHome().getFurnitureMakeSlotItemList().stream()
            .filter(x -> x.getIndex() == index && x.getMakeId() == makeId)
            .findFirst();

        if (slotItem.isEmpty()) {
            this.player.getSession().send(new PacketTakeFurnitureMakeRsp(Retcode.RET_FURNITURE_MAKE_NO_MAKE_DATA_VALUE, makeId, null, null));
            return;
        }

        // pay the speedup item
        if (isFastFinish && !this.player.getInventory().payItem(107013, 1)) {
            this.player.getSession().send(new PacketTakeFurnitureMakeRsp(Retcode.RET_FURNITURE_MAKE_UNFINISH_VALUE, makeId, null, null));
            return;
        }

        // check if player can take
//        if(slotItem.get().getBeginTime() + slotItem.get().getDurTime() >= Utils.getCurrentSeconds() && !isFastFinish){
//            player.getSession().send(new PacketTakeFurnitureMakeRsp(Retcode.RET_FURNITURE_MAKE_UNFINISH_VALUE, makeId, null, null));
//            return;
//        }

        this.player.getInventory().addItem(makeData.getFurnitureItemID(), makeData.getCount());
        this.player.getHome().getFurnitureMakeSlotItemList().remove(slotItem.get());

        this.player.getSession().send(new PacketTakeFurnitureMakeRsp(Retcode.RET_SUCC_VALUE, makeId,
            List.of(ItemParamOuterClass.ItemParam.newBuilder()
                .setItemId(makeData.getFurnitureItemID())
                .setCount(makeData.getCount())
                .build()),
            this.player.getHome().getFurnitureMakeSlotItemList().stream()
                .map(FurnitureMakeSlotItem::toProto)
                .toList()
        ));
        this.player.getHome().save();
    }
}
