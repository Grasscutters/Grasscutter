package emu.grasscutter.game.expedition;

import com.google.gson.reflect.TypeToken;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.DataLoader;
import emu.grasscutter.server.game.GameServer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.List;

import static emu.grasscutter.Configuration.*;

public class ExpeditionManager {
    public GameServer getGameServer() {
        return gameServer;
    }

    private final GameServer gameServer;

    public Int2ObjectMap<List<ExpeditionRewardDataList>> getExpeditionRewardDataList() { return expeditionRewardData; }

    private final Int2ObjectMap<List<ExpeditionRewardDataList>> expeditionRewardData;

    public ExpeditionManager(GameServer gameServer) {
        this.gameServer = gameServer;
        this.expeditionRewardData = new Int2ObjectOpenHashMap<>();
        this.load();
    }

    public synchronized void load() {
        try (Reader fileReader = new InputStreamReader(DataLoader.load("ExpeditionReward.json"))) {
            getExpeditionRewardDataList().clear();
            List<ExpeditionRewardInfo> banners = Grasscutter.getGsonFactory().fromJson(fileReader, TypeToken.getParameterized(Collection.class, ExpeditionRewardInfo.class).getType());
            if(banners.size() > 0) {
                for (ExpeditionRewardInfo di : banners) {
                    getExpeditionRewardDataList().put(di.getExpId(), di.getExpeditionRewardDataList());
                }
                Grasscutter.getLogger().info("Expedition reward successfully loaded.");
            } else {
                Grasscutter.getLogger().error("Unable to load expedition reward. Expedition reward size is 0.");
            }
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load expedition reward.", e);
        }
    }
}
