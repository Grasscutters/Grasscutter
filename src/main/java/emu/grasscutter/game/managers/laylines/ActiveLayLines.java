package emu.grasscutter.game.managers.laylines;

import java.util.ArrayList;
import java.util.List;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.MonsterData;
import emu.grasscutter.data.excels.WorldLevelData;
import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.game.dungeons.challenge.trigger.ChallengeTrigger;
import emu.grasscutter.game.dungeons.challenge.trigger.KillMonsterTrigger;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.scripts.data.SceneBossChest;
import emu.grasscutter.scripts.data.SceneGadget;
import emu.grasscutter.scripts.data.SceneGroup;
import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.Utils;

public class ActiveLayLines{
    SceneGroup tempSceneGroup;
    WorldChallenge challenge;
    EntityGadget gadget;
    EntityGadget chest;
    int step;
    int goal;
    int generatedCount;
    int worldLevel;
    boolean pass=false;
    ArrayList<EntityMonster> monsters = new ArrayList<>();
    ArrayList<ChallengeTrigger> challengeTriggers = new ArrayList<>();
    private static final int BLOOMING_GADGET_ID = 70210109;
    public ActiveLayLines(EntityGadget entityGadget,int goal,int timeout,int worldLevel) {
        this.tempSceneGroup = new SceneGroup();
        this.tempSceneGroup.id = entityGadget.getId();
        this.gadget=entityGadget;
        this.step=0;
        this.worldLevel = worldLevel;
        this.challenge = new WorldChallenge(entityGadget.getScene(),
            tempSceneGroup,
            1,
            1,
            List.of(goal, timeout),
            timeout,
            goal,challengeTriggers);
        this.goal=goal;
        this.challengeTriggers.add(new KillMonsterTrigger());
        //this.challengeTriggers.add(new InTimeTrigger());
    }
    public WorldChallenge getChallenge(){
        return this.challenge;
    }
    public void setMonsters(ArrayList<EntityMonster> monsters) {
        this.monsters.clear();
        this.monsters.addAll(monsters);
        for(EntityMonster monster : monsters){
            monster.setGroupId(this.tempSceneGroup.id);
        }
    }
    public int getAliveMonstersCount(){
        if(monsters==null){
            return 0;
        }
        int count=0;
        for(EntityMonster monster: monsters) {
            if(monster.isAlive()){
                count++;
            }
        }
        return count;
    }
    public boolean getPass(){
        return pass;
    }
    public void start(){
        challenge.start();
    }
    public void onTick() {
        Scene scene = gadget.getScene();
        Position pos = gadget.getPosition();
        if(getAliveMonstersCount() <= 2){
            if(generatedCount<goal){
                step++;
                MonsterData monsterData = GameData.getMonsterDataMap().get(21010101);
                WorldLevelData worldLevelData = GameData.getWorldLevelDataMap().get(worldLevel);
                int monsterLevel = worldLevelData.getMonsterLevel();
                ArrayList<EntityMonster> monsters = new ArrayList<>();
                int willSpawn = Utils.randomRange(3,5);
                if(generatedCount+willSpawn>goal){
                    willSpawn = goal - generatedCount;
                }
                generatedCount+=willSpawn;
                for (int i = 0; i < willSpawn; i++) {
                    EntityMonster entity = new EntityMonster(scene, monsterData, pos.nearby2d(40), monsterLevel);
                    scene.addEntity(entity);
                    monsters.add(entity);
                }
                setMonsters(monsters);
            }else{
                if(getAliveMonstersCount() == 0) {
                    pass = true;
                }
            }
        }
    }
    public EntityGadget getChest(){
        if(chest==null) {
            EntityGadget rewordGadget = new EntityGadget(gadget.getScene(), BLOOMING_GADGET_ID, gadget.getPosition());
            SceneGadget metaGadget = new SceneGadget();
            metaGadget.boss_chest = new SceneBossChest();
            metaGadget.boss_chest.resin = 20;
            rewordGadget.setMetaGadget(metaGadget);
            rewordGadget.buildContent();
            chest = rewordGadget;
        }
        return chest;
    }
}