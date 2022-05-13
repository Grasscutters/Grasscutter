package emu.grasscutter.game.expedition;

import com.google.gson.reflect.TypeToken;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.game.GameServer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.io.FileReader;
import java.util.Collection;
import java.util.List;

import static emu.grasscutter.Configuration.*;

public class ExpeditionManager {
    public GameServer getGameServer() {
        return gameServer;
    }

    private final GameServer gameServer;

    public Int2ObjectMap<List<ExpeditionRewardDataList>> getExpeditionRewardDataList() { return expeditionRewardData; }

    public List<ExpeditionAvatarEffect> getExpeditionAvatarEffectList() {
        return expeditionAvatarEffectList;
    }

    private final Int2ObjectMap<List<ExpeditionRewardDataList>> expeditionRewardData;
    private List<ExpeditionAvatarEffect> expeditionAvatarEffectList;

    public ExpeditionManager(GameServer gameServer) {
        this.gameServer = gameServer;
        this.expeditionRewardData = new Int2ObjectOpenHashMap<>();
        this.expeditionAvatarEffectList = null;
        this.load();
        this.loadEffects();
    }

    public synchronized void load() {
        try (FileReader fileReader = new FileReader(DATA("ExpeditionReward.json"))) {
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

    public synchronized void loadEffects() {
        try (FileReader fileReader = new FileReader(DATA("ExpeditionAvatarEffect.json"))) {
            List<ExpeditionAvatarEffect> banners = Grasscutter.getGsonFactory().fromJson(fileReader, TypeToken.getParameterized(Collection.class, ExpeditionAvatarEffect.class).getType());
            if(banners.size() > 0) {
                this.expeditionAvatarEffectList = banners;
                Grasscutter.getLogger().info("Expedition Avatar Effect successfully loaded.");
            } else {
                Grasscutter.getLogger().error("Unable to load expedition avatar effect. Expedition avatar effect size is 0.");
            }
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load expedition avatar effect.", e);
        }
    }

    public String expId2Area(int expId){
        return expId > 100 && expId < 200 ? "Mondstadt" : expId < 300 ? "Liyue" : "Inazuma";
    }
}
