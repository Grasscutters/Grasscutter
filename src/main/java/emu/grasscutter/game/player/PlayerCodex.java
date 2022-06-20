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
    @Transient
    private Player player;

    //itemId is not codexId!
    private final Set<Integer> unlockedWeapon;
    private final Map<Integer, Integer> unlockedAnimal;
    private final Set<Integer> unlockedMaterial;
    private final Set<Integer> unlockedBook;
    private final Set<Integer> unlockedTip;
    private final Set<Integer> unlockedView;
    private final Set<Integer> unlockedReliquary;
    private final Set<Integer> unlockedReliquarySuitCodex;

    public PlayerCodex() {
        this.unlockedWeapon = new HashSet<>();
        this.unlockedAnimal = new HashMap<>();
        this.unlockedMaterial = new HashSet<>();
        this.unlockedBook = new HashSet<>();
        this.unlockedTip = new HashSet<>();
        this.unlockedView = new HashSet<>();
        this.unlockedReliquary = new HashSet<>();
        this.unlockedReliquarySuitCodex = new HashSet<>();
    }

    public PlayerCodex(Player player) {
        this();
        this.player = player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void checkAddedItem(GameItem item) {
        ItemType type = item.getItemData().getItemType();
        if (type == ItemType.ITEM_WEAPON) {
            if (!this.getUnlockedWeapon().contains(item.getItemId())) {
                this.getUnlockedWeapon().add(item.getItemId());
                var codexItem = GameData.getCodexWeaponDataIdMap().get(item.getItemId());
                if (codexItem != null) {
                    this.player.save();
                    this.player.sendPacket(new PacketCodexDataUpdateNotify(2, codexItem.getId()));
                }
            }
        } else if (type == ItemType.ITEM_MATERIAL) {
            if (item.getItemData().getMaterialType() == MaterialType.MATERIAL_FOOD ||
                item.getItemData().getMaterialType() == MaterialType.MATERIAL_WIDGET ||
                item.getItemData().getMaterialType() == MaterialType.MATERIAL_EXCHANGE ||
                item.getItemData().getMaterialType() == MaterialType.MATERIAL_AVATAR_MATERIAL ||
                item.getItemData().getMaterialType() == MaterialType.MATERIAL_NOTICE_ADD_HP) {
                if (!this.getUnlockedMaterial().contains(item.getItemId())) {
                    var codexMaterial = GameData.getCodexMaterialDataIdMap().get(item.getItemId());
                    if (codexMaterial != null) {
                        this.getUnlockedMaterial().add(item.getItemId());
                        this.player.save();
                        this.player.sendPacket(new PacketCodexDataUpdateNotify(4, codexMaterial.getId()));
                    }
                }
            }
        } else if (type == ItemType.ITEM_RELIQUARY) {
            if (!this.getUnlockedReliquary().contains(item.getItemId())) {
                this.getUnlockedReliquary().add(item.getItemId());
                this.checkUnlockedSuits(item);
            }
        }
    }

    public void checkAnimal(GameEntity target, CodexAnimalData.CodexAnimalUnlockCondition condition) {
        if (target instanceof EntityMonster) {
            var monsterId = ((EntityMonster) target).getMonsterData().getId();
            var codexAnimal = GameData.getCodexAnimalDataMap().get(monsterId);

            if (!this.getUnlockedAnimal().containsKey(monsterId)) {
                if (codexAnimal != null) {
                    if (codexAnimal.getUnlockCondition() == condition || codexAnimal.getUnlockCondition() == null) {
                        this.getUnlockedAnimal().put(monsterId, 1);
                    }
                }
            } else {
                this.getUnlockedAnimal().put(monsterId, this.getUnlockedAnimal().get(monsterId) + 1);
            }
            this.player.save();
            this.player.sendPacket(new PacketCodexDataUpdateNotify(3, monsterId));
        }
    }

    public void checkUnlockedSuits(GameItem item) {
        int reliquaryId = item.getItemId();
        Optional<CodexReliquaryData> excelReliquarySuitList = GameData.getcodexReliquaryArrayList().stream().filter(
            x -> x.getCupId() == reliquaryId
                || x.getLeatherId() == reliquaryId
                || x.getCapId() == reliquaryId
                || x.getFlowerId() == reliquaryId
                || x.getSandId() == reliquaryId
        ).findFirst();
        if (excelReliquarySuitList.isPresent()) {
            var excelReliquarySuit = excelReliquarySuitList.get();
            if (!this.getUnlockedReliquarySuitCodex().contains(excelReliquarySuit.getId())) {
                if (
                    this.getUnlockedReliquary().contains(excelReliquarySuit.getCupId()) &&
                        this.getUnlockedReliquary().contains(excelReliquarySuit.getLeatherId()) &&
                        this.getUnlockedReliquary().contains(excelReliquarySuit.getCapId()) &&
                        this.getUnlockedReliquary().contains(excelReliquarySuit.getFlowerId()) &&
                        this.getUnlockedReliquary().contains(excelReliquarySuit.getSandId())
                ) {
                    this.getUnlockedReliquarySuitCodex().add(excelReliquarySuit.getId());
                    this.player.save();
                    this.player.sendPacket(new PacketCodexDataUpdateNotify(8, excelReliquarySuit.getId()));
                }
            }
        }
    }

    public Set<Integer> getUnlockedWeapon() {
        return this.unlockedWeapon;
    }

    public Map<Integer, Integer> getUnlockedAnimal() {
        return this.unlockedAnimal;
    }

    public Set<Integer> getUnlockedMaterial() {
        return this.unlockedMaterial;
    }

    public Set<Integer> getUnlockedBook() {
        return this.unlockedBook;
    }

    public Set<Integer> getUnlockedTip() {
        return this.unlockedTip;
    }

    public Set<Integer> getUnlockedView() {
        return this.unlockedView;
    }

    public Set<Integer> getUnlockedReliquary() {
        return this.unlockedReliquary;
    }

    public Set<Integer> getUnlockedReliquarySuitCodex() {
        return this.unlockedReliquarySuitCodex;
    }

}