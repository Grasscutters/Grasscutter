package emu.grasscutter.game.world;

import com.google.gson.reflect.TypeToken;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.DataLoader;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.RewardPreviewData;
import emu.grasscutter.game.entity.gadget.chest.BossChestInteractHandler;
import emu.grasscutter.game.entity.gadget.chest.ChestInteractHandler;
import emu.grasscutter.game.entity.gadget.chest.NormalChestInteractHandler;
import emu.grasscutter.server.game.GameServer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldDataManager {
    private final GameServer gameServer;
    private final Map<String, ChestInteractHandler> chestInteractHandlerMap; // chestType-Handler

    public WorldDataManager(GameServer gameServer){
        this.gameServer = gameServer;
        this.chestInteractHandlerMap = new HashMap<>();
        loadChestConfig();
    }

    public synchronized void loadChestConfig(){
        // set the special chest first
        chestInteractHandlerMap.put("SceneObj_Chest_Flora", new BossChestInteractHandler());

    	try(InputStream is = DataLoader.load("ChestReward.json"); InputStreamReader isr = new InputStreamReader(is)) {
            List<ChestReward> chestReward = Grasscutter.getGsonFactory().fromJson(
            		isr,
                    TypeToken.getParameterized(List.class, ChestReward.class).getType());
            
            chestReward.forEach(reward ->
                    reward.getObjNames().forEach(
                            name -> chestInteractHandlerMap.putIfAbsent(name, new NormalChestInteractHandler(reward))));

        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load chest reward config.", e);
        }
    }

    public Map<String, ChestInteractHandler> getChestInteractHandlerMap() {
        return chestInteractHandlerMap;
    }

    public RewardPreviewData getRewardByBossId(int monsterId){
        var investigationMonsterData = GameData.getInvestigationMonsterDataMap().values().parallelStream()
                .filter(imd -> imd.getMonsterIdList() != null && !imd.getMonsterIdList().isEmpty())
                .filter(imd -> imd.getMonsterIdList().get(0) == monsterId)
                .findFirst();

        if(investigationMonsterData.isEmpty()){
            return null;
        }
        return GameData.getRewardPreviewDataMap().get(investigationMonsterData.get().getRewardPreviewId());
    }
}
