package emu.grasscutter.game.managers.laylines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.gadget.GadgetWorktop;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.VisionTypeOuterClass;
import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.Utils;

public class LayLinesManager {
    private static final int LAY_LINES_BLUE_GADGET_ID = 70360057;
    private static final int LAY_LINES_GOLDEN_GADGET_ID = 70360056;
    Player player;
    ArrayList<ActiveLayLines> activeLayLines = new ArrayList<>();
    ArrayList<EntityGadget> activeChests = new ArrayList<>();
    private static final HashMap<Integer,ArrayList<Reward>> REWARDS_GOLDEN = new HashMap<>();
    private static final HashMap<Integer,ArrayList<Reward>> REWARDS_BLUE = new HashMap<>();
    static{
        ArrayList<Reward> rewards;
        rewards = new ArrayList<>();
        rewards.add(new Reward(202,12000));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_GOLDEN.put(0,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(202,20000));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_GOLDEN.put(1,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(202,28000));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_GOLDEN.put(2,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(202,36000));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_GOLDEN.put(3,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(202,44000));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_GOLDEN.put(4,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(202,52000));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_GOLDEN.put(5,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(202,60000));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_GOLDEN.put(6,rewards);

        //
        rewards = new ArrayList<>();
        rewards.add(new Reward(104001,7,8));
        rewards.add(new Reward(104002,3,4));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_BLUE.put(0,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(104001,10,12));
        rewards.add(new Reward(104002,5,6));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_BLUE.put(1,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(104002,10,11));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_BLUE.put(2,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(104002,13,14));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_BLUE.put(3,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(104003,2,3));
        rewards.add(new Reward(104002,6,7));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_BLUE.put(4,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(104003,3,4));
        rewards.add(new Reward(104002,6,7));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_BLUE.put(5,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(104003,4,5));
        rewards.add(new Reward(104002,6,7));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_BLUE.put(5,rewards);
    }

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
                Scene scene = getScene();
                scene.addEntity(chest);
                scene.setChallenge(null);
                activeChests.add(chest);
                it.remove();
            }
        }
    }
    public void createLayLineGadgetEntity(Position pos, Position rot){
        int worldLevel = getWorldLevel();

        Scene scene = getScene();
        EntityGadget entityGadget = new EntityGadget(scene, LAY_LINES_BLUE_GADGET_ID, pos, rot);
        entityGadget.buildContent();
        GadgetWorktop gadgetWorktop = ((GadgetWorktop) entityGadget.getContent());
        gadgetWorktop.addWorktopOptions(new int[]{187});
        gadgetWorktop.setOnSelectWorktopOptionEvent((Player player, GadgetWorktop context, int option) -> {
            synchronized (LayLinesManager.this) {
                for (ActiveLayLines activeLayLines : activeLayLines) {
                    if (activeLayLines.gadget == entityGadget) {
                        return false;
                    }
                }
                ActiveLayLines activeLayLine = new ActiveLayLines(entityGadget, 6, -1, worldLevel);
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

    public int getWorldLevel(){
        return getScene().getWorld().getWorldLevel();
    }

    public synchronized List<GameItem> onReward(EntityGadget gadget) {
        if(!activeChests.contains(gadget)){
            return null;
        }
        int worldLevel = getWorldLevel();
        ArrayList<GameItem> items = new ArrayList<>();
        ArrayList<Reward> rewards;
        if(gadget.getGadgetId() == LAY_LINES_BLUE_GADGET_ID){
            rewards = REWARDS_BLUE.get(worldLevel);
        }else{
            rewards = REWARDS_GOLDEN.get(worldLevel);
        }
        for(Reward reward : rewards){
            items.add(new GameItem(reward.itemId, Utils.randomRange(reward.minCount,reward.maxCount)));
        }
        return items;
    }
}
