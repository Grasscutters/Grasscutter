package emu.grasscutter.game.managers.laylines;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import emu.grasscutter.data.excels.RewardPreviewData;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.entity.gadget.GadgetChest;
import emu.grasscutter.game.entity.gadget.GadgetWorktop;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.VisionTypeOuterClass;
import emu.grasscutter.scripts.data.SceneBossChest;
import emu.grasscutter.scripts.data.SceneGadget;
import emu.grasscutter.utils.Position;

public class LayLinesManager {
    private static final int LAY_LINES_BLUE_GADGET_ID = 70360057;
    private static final int LAY_LINES_GOLDEN_GADGET_ID = 70360056;
    Player player;
    ArrayList<ActiveLayLines> activeLayLines = new ArrayList<>();
    ArrayList<EntityGadget> activeChests = new ArrayList<>();
    public void createLayLineGadgetEntity(){
        createLayLineGadgetEntity(player.getPos(), player.getRotation());
    }
    public synchronized void onTick(){
        Iterator<ActiveLayLines> it = activeLayLines.iterator();
        while(it.hasNext()) {
            ActiveLayLines activeLayLines = it.next();
            activeLayLines.onTick();
            if(activeLayLines.getPass()){
                EntityGadget chest = activeLayLines.getChest();
                System.out.println("add entity id="+chest.getId());
                Scene scene = getScene();
                scene.addEntity(chest);
                scene.setChallenge(null);
                activeChests.add(chest);
                it.remove();
            }
        }
    }
    public void createLayLineGadgetEntity(Position pos, Position rot){
        Scene scene = getScene();
        EntityGadget entityGadget = new EntityGadget(scene, LAY_LINES_GOLDEN_GADGET_ID,pos,rot);
        entityGadget.buildContent();
        GadgetWorktop gadgetWorktop = ((GadgetWorktop)entityGadget.getContent());
        gadgetWorktop.addWorktopOptions(new int[]{187});
        gadgetWorktop.setOnSelectWorktopOptionEvent((Player player, GadgetWorktop context,int option)->{
            synchronized(LayLinesManager.this){
                for(ActiveLayLines activeLayLines: activeLayLines) {
                    if(activeLayLines.gadget == entityGadget){
                        return false;
                    }
                }
                ActiveLayLines activeLayLine = new ActiveLayLines(entityGadget,6,-1);
                entityGadget.updateState(201);
                scene.setChallenge(activeLayLine.getChallenge());
                scene.removeEntity(entityGadget, VisionTypeOuterClass.VisionType.VISION_TYPE_REMOVE);
                activeLayLines.add(activeLayLine);
                activeLayLine.start();
            }
            return true;
        });
        entityGadget.setState(204);
        player.getScene().addEntity(entityGadget);
    }
    public synchronized void setPlayer(Player player) {
        this.player = player;
    }
    private Scene getScene() {
        return player.getScene();
    }

    public synchronized List<GameItem> onReward(EntityGadget gadget) {
        if(!activeChests.contains(gadget)){
            return null;
        }
        ArrayList<GameItem> rewards = new ArrayList<>();
        rewards.add(new GameItem(202,60000));
        rewards.add(new GameItem(105,20));
        rewards.add(new GameItem(102,100));
        return rewards;
    }
}
