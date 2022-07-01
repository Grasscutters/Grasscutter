package emu.grasscutter.game.managers.leylines;

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

public class LeyLinesManager {
    Player player;
    ArrayList<ActiveLeyLines> activeLeyLines = new ArrayList<>();
    ArrayList<ActiveLeyLines> activeChests = new ArrayList<>();
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
        REWARDS_BLUE.put(6,rewards);
    }

    public void createLeyLineGadgetEntity(LeyLinesType leyLinesType){
        createLeyLineGadgetEntity(player.getPos(), player.getRotation(), leyLinesType);
    }
    public synchronized void onTick(){
        Iterator<ActiveLeyLines> it = activeLeyLines.iterator();
        while(it.hasNext()) {
            ActiveLeyLines activeLeyLines = it.next();
            activeLeyLines.onTick();
            if(activeLeyLines.getPass()){
                EntityGadget chest = activeLeyLines.getChest();
                Scene scene = getScene();
                scene.addEntity(chest);
                scene.setChallenge(null);
                activeChests.add(activeLeyLines);
                it.remove();
            }
        }
    }
    public void createLeyLineGadgetEntity(Position pos, Position rot, LeyLinesType leyLinesType){
        int worldLevel = getWorldLevel();

        Scene scene = getScene();
        EntityGadget entityGadget = new EntityGadget(scene, leyLinesType.getGadgetId(), pos, rot);
        entityGadget.buildContent();
        GadgetWorktop gadgetWorktop = ((GadgetWorktop) entityGadget.getContent());
        gadgetWorktop.addWorktopOptions(new int[]{187});
        gadgetWorktop.setOnSelectWorktopOptionEvent((Player player, GadgetWorktop context, int option) -> {
            synchronized (LeyLinesManager.this) {
                for (ActiveLeyLines activeLeyLines : this.activeLeyLines) {
                    if (activeLeyLines.gadget == entityGadget) {
                        return false;
                    }
                }
                ActiveLeyLines leyLine = new ActiveLeyLines(entityGadget, 6, -1, worldLevel);
                entityGadget.updateState(201);
                scene.setChallenge(leyLine.getChallenge());
                scene.removeEntity(entityGadget, VisionTypeOuterClass.VisionType.VISION_TYPE_REMOVE);
                activeLeyLines.add(leyLine);
                leyLine.start();
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

    public synchronized List<GameItem> onReward(EntityGadget chest) {
        var it = activeChests.iterator();
        while(it.hasNext()){
            var activeChest = it.next();
            if(activeChest.getChest()==chest){
                int worldLevel = getWorldLevel();
                ArrayList<GameItem> items = new ArrayList<>();
                ArrayList<Reward> rewards;
                if(activeChest.gadget.getGadgetId() == LeyLinesType.LAY_LINES_BLUE_GADGET_ID.getGadgetId()){
                    rewards = REWARDS_BLUE.get(worldLevel);
                }else{
                    rewards = REWARDS_GOLDEN.get(worldLevel);
                }
                for(Reward reward : rewards){
                    items.add(new GameItem(reward.itemId, Utils.randomRange(reward.minCount,reward.maxCount)));
                }
                it.remove();
                return items;
            }
        }
        return null;
    }
}
