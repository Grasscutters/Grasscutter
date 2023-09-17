package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.data.excels.monster.MonsterData;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.QuestCreateEntityReqOuterClass.QuestCreateEntityReq;
import emu.grasscutter.scripts.data.SceneGadget;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketQuestCreateEntityRsp;
import lombok.val;

@Opcodes(PacketOpcodes.QuestCreateEntityReq)
public class HandlerQuestCreateEntityReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        val req = QuestCreateEntityReq.parseFrom(payload);
        val entity = req.getEntity();
        val scene = session.getPlayer().getWorld().getSceneById(entity.getSceneId());

        val pos = new Position(entity.getPos());
        val rot = new Position(entity.getRot());
        GameEntity gameEntity = null;
        switch (entity.getEntityCase()) {
            case GADGET_ID -> {
                val gadgetId = entity.getGadgetId();
                val gadgetInfo = entity.getGadget();
                var gadgetData = GameData.getGadgetDataMap().get(gadgetId);
                gameEntity =
                        switch (gadgetData.getType()) {
                            case Vehicle -> new EntityVehicle(scene, session.getPlayer(), gadgetId, 0, pos, rot);
                            case Chest -> {
                                var chest = gadgetInfo.getChest();
                                var gadget = new EntityGadget(scene, gadgetId, pos, rot);
                                // Create the gadget data for the chest.
                                var metaGadget = new SceneGadget();
                                metaGadget.drop_count = 1; // TODO: Check if more items should be dropped.
                                metaGadget.chest_drop_id = chest.getChestDropId();
                                metaGadget.setShowcutscene(chest.getIsShowCutscene());
                                // Apply the gadget data to the chest.
                                gadget.setMetaGadget(metaGadget);

                                yield gadget;
                            }
                            default -> new EntityGadget(scene, gadgetId, pos, rot);
                        };

                if (gameEntity instanceof EntityGadget gadget) {
                    gadget.buildContent();
                }
            }
            case ITEM_ID -> {
                val itemId = entity.getItemId();
                ItemData itemData = GameData.getItemDataMap().get(itemId);
                gameEntity = new EntityItem(scene, null, itemData, pos, 1, true);
            }
            case MONSTER_ID -> {
                val monsterId = entity.getMonsterId();
                val level = entity.getLevel();
                MonsterData monsterData = GameData.getMonsterDataMap().get(monsterId);
                gameEntity = new EntityMonster(scene, monsterData, pos, rot, level);
            }
            case NPC_ID -> {}
        }

        if (gameEntity != null) {
            scene.addEntity(gameEntity);
        }

        val createdEntityId = gameEntity != null ? gameEntity.getId() : -1;

        session.send(new PacketQuestCreateEntityRsp(createdEntityId, req));
    }
}
