package emu.grasscutter.game.world;

import com.google.gson.reflect.TypeToken;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.game.GameServer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static emu.grasscutter.Configuration.DATA;

public class WorldDataManager {
    private final GameServer gameServer;
    private final Map<String, ChestReward> chestRewardMap;

    public WorldDataManager(GameServer gameServer){
        this.gameServer = gameServer;
        this.chestRewardMap = new HashMap<>();
        load();
    }

    public synchronized void load(){
        try {
            List<ChestReward> chestReward = Grasscutter.getGsonFactory().fromJson(
                    Files.readString(Path.of(DATA("ChestReward.json"))),
                    TypeToken.getParameterized(List.class, ChestReward.class).getType());
            chestReward.forEach(reward ->
                    reward.getObjNames().forEach(name -> chestRewardMap.put(name, reward)));

        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load chest reward config.", e);
        }
    }

    public Map<String, ChestReward> getChestRewardMap() {
        return chestRewardMap;
    }
}
