package emu.grasscutter.game.managers.leylines;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

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

public class ActiveLeyLines {
    private final SceneGroup tempSceneGroup;
    private final WorldChallenge challenge;
    private final EntityGadget gadget;
    private EntityGadget chest;
    private int step;
    private final int goal;
    private int generatedCount;
    private final int worldLevel;
    private boolean pass=false;
    private final ArrayList<EntityMonster> activeMonsters = new ArrayList<>();
    private final Queue<Integer> candidateMonsters = new ArrayDeque<>();
    private static final int BLOOMING_GADGET_ID = 70210109;
    public ActiveLeyLines(EntityGadget entityGadget, ArrayList<Integer> monsters, int timeout, int worldLevel) {
        this.tempSceneGroup = new SceneGroup();
        this.tempSceneGroup.id = entityGadget.getId();
        this.gadget=entityGadget;
        this.step=0;
        this.goal = monsters.size();
        this.candidateMonsters.addAll(monsters);
        this.worldLevel = worldLevel;
        ArrayList<ChallengeTrigger> challengeTriggers = new ArrayList<>();
        this.challenge = new WorldChallenge(entityGadget.getScene(),
            tempSceneGroup,
            1,
            1,
            List.of(goal, timeout),
            timeout,
            goal, challengeTriggers);
        challengeTriggers.add(new KillMonsterTrigger());
        //this.challengeTriggers.add(new InTimeTrigger());
    }
    public WorldChallenge getChallenge(){
        return this.challenge;
    }
    public void setMonsters(ArrayList<EntityMonster> monsters) {
        this.activeMonsters.clear();
        this.activeMonsters.addAll(monsters);
        for(EntityMonster monster : monsters){
            monster.setGroupId(this.tempSceneGroup.id);
        }
    }
    public int getAliveMonstersCount(){
        int count=0;
        for(EntityMonster monster: activeMonsters) {
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
                WorldLevelData worldLevelData = GameData.getWorldLevelDataMap().get(worldLevel);
                int monsterLevel = worldLevelData.getMonsterLevel();
                ArrayList<EntityMonster> newMonsters = new ArrayList<>();
                int willSpawn = Utils.randomRange(3,5);
                if(generatedCount+willSpawn>goal){
                    willSpawn = goal - generatedCount;
                }
                generatedCount+=willSpawn;
                for (int i = 0; i < willSpawn; i++) {
                    MonsterData monsterData = GameData.getMonsterDataMap().get(candidateMonsters.poll());
                    EntityMonster entity = new EntityMonster(scene, monsterData, pos.nearby2d(40), monsterLevel);
                    scene.addEntity(entity);
                    newMonsters.add(entity);
                }
                setMonsters(newMonsters);
            }else{
                if(getAliveMonstersCount() == 0) {
                    this.pass = true;
                    this.challenge.done();
                }
            }
        }
    }
    public EntityGadget getGadget(){
        return gadget;
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