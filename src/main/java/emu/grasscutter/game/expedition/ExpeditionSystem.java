package emu.grasscutter.game.expedition;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.DataLoader;
import emu.grasscutter.server.game.*;
import it.unimi.dsi.fastutil.ints.*;
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
        getExpeditionRewardDataList().clear();
        try {
            List<ExpeditionRewardInfo> banners =
                    DataLoader.loadList("ExpeditionReward.json", ExpeditionRewardInfo.class);
            if (banners.size() > 0) {
                for (ExpeditionRewardInfo di : banners) {
                    getExpeditionRewardDataList().put(di.getExpId(), di.getExpeditionRewardDataList());
                }
                Grasscutter.getLogger().debug("Expedition reward successfully loaded.");
            } else {
                Grasscutter.getLogger()
                        .error("Unable to load expedition reward. Expedition reward size is 0.");
            }
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load expedition reward.", e);
        }
    }
}
