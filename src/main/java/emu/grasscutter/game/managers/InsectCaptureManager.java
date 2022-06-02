package emu.grasscutter.game.managers;

import java.util.HashMap;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.proto.VisionTypeOuterClass;

public class InsectCaptureManager {
    private final Player player;
    //Monster ID -> Item ID
    private static final HashMap<Integer,Integer> insectsMapping = new HashMap<>(); //HashMap<Monster Id,Item Id>
    public InsectCaptureManager(Player player) {
        this.player = player;
    }
    public void arrestSmallCreature(EntityMonster monster){
        int monsterId = monster.getMonsterData().getId();
        if(insectsMapping.containsKey(monsterId)) {
            int itemId = insectsMapping.get(monsterId);
            ItemData data = GameData.getItemDataMap().get(itemId);
            GameItem item = new GameItem(data, 1);
            player.getInventory().addItem(item, ActionReason.SubfieldDrop);
            monster.getScene().removeEntity(monster, VisionTypeOuterClass.VisionType.VISION_TYPE_REMOVE);
        }else{
            Grasscutter.getLogger().warn("Monster(id={}) couldn't be caught. please EDIT InsectCaptureManager class to allow it", monsterId);
        }
    }
    static{
        //Monsters(Id) who can be caught to Item Id

        //butterfly
        insectsMapping.put(28050105,100072);

        //crab
        insectsMapping.put(28010101,100073);
        insectsMapping.put(28010102,100073);
        insectsMapping.put(28010103,100073);
        insectsMapping.put(28010104,100073);
        insectsMapping.put(28010105,100073);

        //crystal butterfly
        insectsMapping.put(28050101,100085);
        insectsMapping.put(28050102,100085);
        insectsMapping.put(28050103,100085);
        insectsMapping.put(28050104,100085);

        //lizard
        insectsMapping.put(28010201,100083);
        insectsMapping.put(28010202,100083);
        insectsMapping.put(28010203,100083);
        insectsMapping.put(28010207,100083);

        //fish
        insectsMapping.put(28040101,100084);
        insectsMapping.put(28040102,100084);
        insectsMapping.put(28040103,100084);
        insectsMapping.put(28040104,100084);
        insectsMapping.put(28040105,100084);
        insectsMapping.put(28040106,100084);
        insectsMapping.put(28040107,100084);
        insectsMapping.put(28040108,100084);

        //frog
        insectsMapping.put(28010301,100081);
        insectsMapping.put(28010302,100081);
        insectsMapping.put(28010303,100081);
    }
}
