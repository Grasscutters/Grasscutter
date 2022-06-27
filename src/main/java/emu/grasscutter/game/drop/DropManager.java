package emu.grasscutter.game.drop;

import emu.grasscutter.game.entity.EntityItem;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.loot.LootContext;
import emu.grasscutter.loot.LootRegistry;
import emu.grasscutter.loot.LootTable;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.utils.Position;

public class DropManager {
    public GameServer getGameServer() {
        return gameServer;
    }

    private final GameServer gameServer;

    private LootRegistry lootTables;

    public DropManager(GameServer gameServer) {
        this.gameServer = gameServer;
        this.load();
    }

    public void load() {
        lootTables = LootRegistry.getLootRegistry("MonsterDrop.json");
    }

    private void addDropEntity(GameItem gi, Scene dropScene, Position pos, int num, Player target) {
        var itemData = gi.getItemData();
        if (itemData.getItemType() != ItemType.ITEM_VIRTUAL || itemData.getGadgetId() != 0) {
            EntityItem entity = new EntityItem(dropScene, target, itemData, pos, num, true);
            // todo add share options
            // if (!dd.isShare())
            // dropScene.addEntityToSingleClient(target, entity);
            // else
            dropScene.addEntity(entity);
        } else {
            if (target != null) {
                target.getInventory().addItem(gi, ActionReason.SubfieldDrop, true);
            } else {
                // target is null if items will be added are shared. no one could pick it up because of the combination(give + shared)
                // so it will be sent to all players' inventories directly.
                dropScene.getPlayers().forEach(x -> x.getInventory().addItem(gi, ActionReason.SubfieldDrop, true));
            }
        }
    }

    private void processDrop(GameItem gameItem, EntityMonster em, Player gp) {
        if (gameItem == null) {
            return;
        }
        int num = gameItem.getCount();
        var itemData = gameItem.getItemData();
        if (itemData.isEquip()) {
            for (int i = 0; i < num; i++) {
                float range = (5f + (.1f * num));
                Position pos = em.getPosition().clone().addX((float) (Math.random() * range) - (range / 2)).addY(3f).addZ((float) (Math.random() * range) - (range / 2));
                addDropEntity(gameItem, em.getScene(), pos, num, gp);
            }
        } else {
            Position pos = em.getPosition().clone().addY(3f);
            addDropEntity(gameItem, em.getScene(), pos, num, gp);
        }
    }

    public void callDrop(EntityMonster em) {
        int id = em.getMonsterData().getId();
        LootTable lt = lootTables.getLootTable(id);
        LootContext ctx = new LootContext();
        ctx.victim = em;

        lt.loot(ctx).forEach(e -> processDrop(e, em, null));
    }
}
