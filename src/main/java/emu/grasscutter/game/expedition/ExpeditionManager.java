package emu.grasscutter.game.expedition;

import com.google.gson.reflect.TypeToken;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.DataLoader;
import emu.grasscutter.server.game.GameServer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.List;

public class ExpeditionManager {
    public GameServer getGameServer() {
        return this.gameServer;
    }

    private final GameServer gameServer;

    public Int2ObjectMap<List<ExpeditionRewardDataList>> getExpeditionRewardDataList() {
        return this.expeditionRewardData;
    }

    private final Int2ObjectMap<List<ExpeditionRewardDataList>> expeditionRewardData;

    public ExpeditionManager(GameServer gameServer) {
        this.gameServer = gameServer;
        this.expeditionRewardData = new Int2ObjectOpenHashMap<>();
        this.load();
    }

    public synchronized void load() {
        try (Reader fileReader = new InputStreamReader(DataLoader.load("ExpeditionReward.json"))) {
            this.getExpeditionRewardDataList().clear();
            List<ExpeditionRewardInfo> banners = Grasscutter.getGsonFactory().fromJson(fileReader, TypeToken.getParameterized(Collection.class, ExpeditionRewardInfo.class).getType());
            if (banners.size() > 0) {
                for (ExpeditionRewardInfo di : banners) {
                    this.getExpeditionRewardDataList().put(di.getExpId(), di.getExpeditionRewardDataList());
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
