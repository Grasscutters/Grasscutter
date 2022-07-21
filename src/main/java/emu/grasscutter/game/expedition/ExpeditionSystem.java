package emu.grasscutter.game.expedition;

import com.google.gson.reflect.TypeToken;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.DataLoader;
import emu.grasscutter.server.game.BaseGameSystem;
import emu.grasscutter.server.game.GameServer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import static emu.grasscutter.config.Configuration.*;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.List;

public class ExpeditionSystem extends BaseGameSystem {
    private final Int2ObjectMap<List<ExpeditionRewardDataList>> expeditionRewardData;

    public ExpeditionSystem(GameServer server) {
        super(server);
        this.expeditionRewardData = new Int2ObjectOpenHashMap<>();
        this.load();
    }

    public Int2ObjectMap<List<ExpeditionRewardDataList>> getExpeditionRewardDataList() {
        return expeditionRewardData;
    }

    public synchronized void load() {
        try (Reader fileReader = DataLoader.loadReader("ExpeditionReward.json")) {
            getExpeditionRewardDataList().clear();
            List<ExpeditionRewardInfo> banners = Grasscutter.getGsonFactory().fromJson(fileReader, TypeToken.getParameterized(Collection.class, ExpeditionRewardInfo.class).getType());
            if (banners.size() > 0) {
                for (ExpeditionRewardInfo di : banners) {
                    getExpeditionRewardDataList().put(di.getExpId(), di.getExpeditionRewardDataList());
                }
                Grasscutter.getLogger().debug("Expedition reward successfully loaded.");
            } else {
                Grasscutter.getLogger().error("Unable to load expedition reward. Expedition reward size is 0.");
            }
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load expedition reward.", e);
        }
    }
}
