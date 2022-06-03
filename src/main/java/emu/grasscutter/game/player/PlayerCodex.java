package emu.grasscutter.game.player;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Transient;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.CodexAnimalData;
import emu.grasscutter.data.excels.CodexReliquaryData;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.inventory.MaterialType;
import emu.grasscutter.server.packet.send.PacketCodexDataUpdateNotify;

import java.util.*;

@Entity
public class PlayerCodex {
    @Transient private Player player;

    //itemId is not codexId!
    private Set<Integer> unlockedWeapon;
    private Map<Integer, Integer> unlockedAnimal;
    private Set<Integer> unlockedMaterial;
    private Set<Integer> unlockedBook;
    private Set<Integer> unlockedTip;
    private Set<Integer> unlockedView;
    private Set<Integer> unlockedReliquary;
    private Set<Integer> unlockedReliquarySuitCodex;

    public PlayerCodex(){
        this.unlockedWeapon = new HashSet<>();
        this.unlockedAnimal = new HashMap<>();
        this.unlockedMaterial = new HashSet<>();
        this.unlockedBook = new HashSet<>();
        this.unlockedTip = new HashSet<>();
        this.unlockedView = new HashSet<>();
        this.unlockedReliquary = new HashSet<>();
        this.unlockedReliquarySuitCodex = new HashSet<>();
    }

    public PlayerCodex(Player player){
        this();
        this.player = player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void checkAddedItem(GameItem item){
        ItemType type = item.getItemData().getItemType();
        if (type == ItemType.ITEM_WEAPON){
            if(!getUnlockedWeapon().contains(item.getItemId())){
                getUnlockedWeapon().add(item.getItemId());
                var codexItem = GameData.getCodexWeaponDataIdMap().get(item.getItemId());
                if(codexItem != null){
                    player.save();
                    this.player.sendPacket(new PacketCodexDataUpdateNotify(2, codexItem.getId()));
                }
            }
        }
        else if(type == ItemType.ITEM_MATERIAL){
            if( item.getItemData().getMaterialType() == MaterialType.MATERIAL_FOOD ||
                item.getItemData().getMaterialType() == MaterialType.MATERIAL_WIDGET||
                item.getItemData().getMaterialType() == MaterialType.MATERIAL_EXCHANGE||
                item.getItemData().getMaterialType() == MaterialType.MATERIAL_AVATAR_MATERIAL||
                item.getItemData().getMaterialType() == MaterialType.MATERIAL_NOTICE_ADD_HP){
                if (!getUnlockedMaterial().contains(item.getItemId())) {
                    var codexMaterial = GameData.getCodexMaterialDataIdMap().get(item.getItemId());
                    if (codexMaterial != null) {
                        getUnlockedMaterial().add(item.getItemId());
                        player.save();
                        this.player.sendPacket(new PacketCodexDataUpdateNotify(4, codexMaterial.getId()));
                    }
                }
            }
        }
        else if(type == ItemType.ITEM_RELIQUARY) {
            if(!getUnlockedReliquary().contains(item.getItemId())){
                getUnlockedReliquary().add(item.getItemId());
                checkUnlockedSuits(item);
            }
        }
    }

    public void checkAnimal(GameEntity target, CodexAnimalData.CodexAnimalUnlockCondition condition){
        if(target instanceof EntityMonster){
            var monsterId = ((EntityMonster)target).getMonsterData().getId();
            var codexAnimal = GameData.getCodexAnimalDataMap().get(monsterId);

            if(!getUnlockedAnimal().containsKey(monsterId)) {
                if (codexAnimal != null) {
                    if(codexAnimal.getUnlockCondition() == condition || codexAnimal.getUnlockCondition() == null){
                        getUnlockedAnimal().put(monsterId, 1);
                    }
                }
            }else{
                getUnlockedAnimal().put(monsterId, getUnlockedAnimal().get(monsterId) + 1);
            }
            player.save();
            this.player.sendPacket(new PacketCodexDataUpdateNotify(3, monsterId));
        }
    }

    public void checkUnlockedSuits(GameItem item){
        int reliquaryId = item.getItemId();
        Optional<CodexReliquaryData> excelReliquarySuitList = GameData.getcodexReliquaryArrayList().stream().filter(
                x -> x.getCupId() == reliquaryId
                        || x.getLeatherId() == reliquaryId
                        || x.getCapId() == reliquaryId
                        || x.getFlowerId() == reliquaryId
                        || x.getSandId() == reliquaryId
        ).findFirst();
        if(excelReliquarySuitList.isPresent()) {
            var excelReliquarySuit = excelReliquarySuitList.get();
            if(!getUnlockedReliquarySuitCodex().contains(excelReliquarySuit.getId())){
                if(
                        getUnlockedReliquary().contains(excelReliquarySuit.getCupId()) &&
                        getUnlockedReliquary().contains(excelReliquarySuit.getLeatherId()) &&
                        getUnlockedReliquary().contains(excelReliquarySuit.getCapId()) &&
                        getUnlockedReliquary().contains(excelReliquarySuit.getFlowerId()) &&
                        getUnlockedReliquary().contains(excelReliquarySuit.getSandId())
                ){
                    getUnlockedReliquarySuitCodex().add(excelReliquarySuit.getId());
                    player.save();
                    this.player.sendPacket(new PacketCodexDataUpdateNotify(8, excelReliquarySuit.getId()));
                }
            }
        }
    }

    public Set<Integer> getUnlockedWeapon() {
        return unlockedWeapon;
    }

    public Map<Integer, Integer> getUnlockedAnimal() {
        return unlockedAnimal;
    }

    public Set<Integer> getUnlockedMaterial() {
        return unlockedMaterial;
    }

    public Set<Integer> getUnlockedBook() {
        return unlockedBook;
    }

    public Set<Integer> getUnlockedTip() {
        return unlockedTip;
    }

    public Set<Integer> getUnlockedView() {
        return unlockedView;
    }

    public Set<Integer> getUnlockedReliquary() {
        return unlockedReliquary;
    }

    public Set<Integer> getUnlockedReliquarySuitCodex() {
        return unlockedReliquarySuitCodex;
    }

}