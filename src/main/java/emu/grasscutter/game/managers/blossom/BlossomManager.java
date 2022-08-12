package emu.grasscutter.game.managers.blossom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameDepot;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.RewardPreviewData;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.entity.gadget.GadgetWorktop;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.game.world.SpawnDataEntry;
import emu.grasscutter.net.proto.VisionTypeOuterClass;
import emu.grasscutter.server.packet.send.PacketBlossomBriefInfoNotify;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

public class BlossomManager {
    public BlossomManager(Scene scene) {
        this.scene = scene;
    }

    private final Scene scene;
    private final List<BlossomActivity> blossomActivities = new ArrayList<>();
    private final List<BlossomActivity> activeChests = new ArrayList<>();
    private final List<EntityGadget> createdEntity = new ArrayList<>();

    private final List<SpawnDataEntry> blossomConsumed = new ArrayList<>();

    public void onTick(){
        synchronized (blossomActivities){
            var it = blossomActivities.iterator();
            while(it.hasNext()){
                var active = it.next();
                active.onTick();
                if (active.getPass()) {
                    EntityGadget chest = active.getChest();
                    scene.addEntity(chest);
                    scene.setChallenge(null);
                    activeChests.add(active);
                    it.remove();
                }
            }
        }
    }
    public void recycleLeyLineGadgetEntity(List<GameEntity> entities){
        for(var entity : entities){
            if(entity instanceof EntityGadget gadget){
                createdEntity.remove(gadget);
            }
        }
        notifyIcon();
    }
    public void initBlossom(EntityGadget gadget){
        if(createdEntity.contains(gadget)){
            return;
        }
        if(blossomConsumed.contains(gadget.getSpawnEntry())){
            return;
        }
        var id = gadget.getGadgetId();
        if(BlossomType.valueOf(id)==null){
            return;
        }
        gadget.buildContent();
        gadget.setState(204);
        int worldLevel = getWorldLevel();
        GadgetWorktop gadgetWorktop = ((GadgetWorktop) gadget.getContent());
        gadgetWorktop.addWorktopOptions(new int[]{187});
        gadgetWorktop.setOnSelectWorktopOptionEvent((GadgetWorktop context, int option) -> {
            BlossomActivity activity;
            EntityGadget entityGadget = context.getGadget();
            synchronized (blossomActivities) {
                for (BlossomActivity activeLeyLines : this.blossomActivities) {
                    if (activeLeyLines.getGadget() == entityGadget) {
                        return false;
                    }
                }

                int volume=0;
                IntList monsters = new IntArrayList();
                while(true){
                    var remain = GameDepot.blossomConfig.getMonsterFightingVolume() - volume;
                    if(remain<=0){
                        break;
                    }
                    var rand = Utils.randomRange(1,100);
                    if(rand>85 && remain>=50){//15% ,generate strong monster
                        monsters.addAll(getRandomMonstersID(2,1));
                        volume+=50;
                    }else if(rand>50 && remain>=20) {//35% ,generate normal monster
                        monsters.addAll(getRandomMonstersID(1,1));
                        volume+=20;
                    }else{//50% ,generate weak monster
                        monsters.addAll(getRandomMonstersID(0,1));
                        volume+=10;
                    }
                }

                Grasscutter.getLogger().info("Blossom Monsters:"+monsters);

                activity = new BlossomActivity(entityGadget, monsters, -1, worldLevel);
                blossomActivities.add(activity);
            }
            entityGadget.updateState(201);
            scene.setChallenge(activity.getChallenge());
            scene.removeEntity(entityGadget, VisionTypeOuterClass.VisionType.VISION_TYPE_REMOVE);
            activity.start();
            return true;
        });
        createdEntity.add(gadget);
        notifyIcon();
    }
    public void notifyIcon(){
        var spawnLists = GameDepot.getSpawnLists();
        var spawns = new ArrayList<Map.Entry<Integer,SpawnDataEntry>>();
        for(var spawnList : spawnLists.values()){
            for(var entry : spawnList){
                for(var spawn : entry.getGroup().getSpawns()){
                    if(BlossomType.valueOf(spawn.getGadgetId())!=null && !blossomConsumed.contains(spawn)){
                        spawns.add(Map.entry(entry.getGroup().getSceneId(),spawn));
                    }
                }
            }
        }
        scene.broadcastPacket(new PacketBlossomBriefInfoNotify(spawns));
    }
    public int getWorldLevel(){
        return scene.getWorld().getWorldLevel();
    }
    private RewardPreviewData getRewardList(BlossomType type , int worldLevel){
        String freshType;
        if(type == BlossomType.GOLDEN_GADGET_ID){
            freshType = "BLOSSOM_REFRESH_SCOIN";
        }else if(type == BlossomType.BLUE_GADGET_ID){
            freshType = "BLOSSOM_REFRESH_EXP";
        }else{
            Grasscutter.getLogger().error("Illegal blossom type {}",type);
            return null;
        }
        var dataList = GameData.getBlossomRefreshExcelConfigDataMap();
        for(var data : dataList.values()){
            if(freshType.equals(data.getRefreshType())){
                var dropVecList = data.getDropVec();
                if((worldLevel+1)>dropVecList.length){
                    Grasscutter.getLogger().error("Illegal world level {}",worldLevel);
                    return null;
                }
                return GameData.getRewardPreviewDataMap().get(dropVecList[worldLevel].getPreviewReward());
            }
        }
        Grasscutter.getLogger().error("Cannot find blossom type {}",type);
        return null;
    }
    public List<GameItem> onReward(Player who,EntityGadget chest,boolean useCondensedResin) {
        synchronized (activeChests) {
            var it = activeChests.iterator();
            while (it.hasNext()) {
                var activeChest = it.next();
                if (activeChest.getChest() == chest) {
                    boolean pay;
                    if(useCondensedResin){
                        pay = who.getInventory().payItem(220007, 1);
                    }else{
                        pay = who.getInventory().payItem(106, 20);
                    }
                    if (pay) {
                        int worldLevel = getWorldLevel();
                        List<GameItem> items = new ArrayList<>();
                        var type = BlossomType.valueOf(activeChest.getGadget().getGadgetId());
                        RewardPreviewData blossomRewards = getRewardList(type,worldLevel);
                        if(blossomRewards ==null){
                            Grasscutter.getLogger().error("Blossom could not support world level : "+worldLevel);
                            return null;
                        }
                        var rewards = blossomRewards.getPreviewItems();
                        for (ItemParamData blossomReward : rewards) {
                            int rewardCount = blossomReward.getCount();
                            if(useCondensedResin){
                                rewardCount += blossomReward.getCount();//Double!
                            }
                            items.add(new GameItem(blossomReward.getItemId(),rewardCount));
                        }
                        it.remove();
                        recycleLeyLineGadgetEntity(List.of(activeChest.getGadget()));
                        blossomConsumed.add(activeChest.getGadget().getSpawnEntry());
                        return items;
                    }
                    return null;
                }
            }
        }
        return null;
    }

    public static IntList getRandomMonstersID(int difficulty,int count){
      IntList result = new IntArrayList();
      List<Integer> monsters = GameDepot.blossomConfig.getMonsterIdsPerDifficulty().get(difficulty);
        for(int i=0; i<count; i++){
            result.add((int) monsters.get(Utils.randomRange(0, monsters.size()-1)));
        }
        return result;
    }
}
