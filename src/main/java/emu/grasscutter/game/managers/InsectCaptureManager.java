package emu.grasscutter.game.managers;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.EnvAnimalGatherConfigData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.entity.EntityVehicle;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.proto.VisionTypeOuterClass;

public record InsectCaptureManager(Player player) {
    public void arrestSmallCreature(GameEntity entity) {
        //System.out.println("arrestSmallCreature!");
        EnvAnimalGatherConfigData gather;
        int thingId;
        if (entity instanceof EntityMonster monster) {
            thingId = monster.getMonsterData().getId();
            gather = GameData.getEnvAnimalGatherConfigDataMap().get(thingId);
        } else if (entity instanceof EntityVehicle gadget) {
            thingId = gadget.getGadgetId();
            gather = GameData.getEnvAnimalGatherConfigDataMap().get(thingId);
        } else {
            return;
        }
        if (gather == null) {
            Grasscutter.getLogger().warn("monster/gather(id={}) couldn't be caught.", thingId);
            return;
        }
        String type = gather.getEntityType();
        if ((type.equals("Monster") && entity instanceof EntityMonster) || (type.equals("Gadget") && entity instanceof EntityVehicle)) {
            EnvAnimalGatherConfigData.GatherItem gatherItem = gather.gatherItem();
            ItemData data = GameData.getItemDataMap().get(gatherItem.getId());
            GameItem item = new GameItem(data, gatherItem.getCount());
            this.player.getInventory().addItem(item, ActionReason.SubfieldDrop);
            entity.getScene().removeEntity(entity, VisionTypeOuterClass.VisionType.VISION_TYPE_REMOVE);
        } else {
            Grasscutter.getLogger().warn("monster/gather(id={}) has a wrong type.", thingId);
        }
    }
}
