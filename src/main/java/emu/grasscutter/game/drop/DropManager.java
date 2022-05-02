package emu.grasscutter.game.drop;

import com.google.gson.reflect.TypeToken;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.game.entity.EntityItem;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.io.FileReader;
import java.util.Collection;
import java.util.List;

public class DropManager {
    public GameServer getGameServer() {
        return gameServer;
    }

    private final GameServer gameServer;

    public Int2ObjectMap<List<DropData>> getDropData() {
        return dropData;
    }

    private final Int2ObjectMap<List<DropData>> dropData;

    public DropManager(GameServer gameServer) {
        this.gameServer = gameServer;
        this.dropData = new Int2ObjectOpenHashMap<>();
        this.load();
    }

    public synchronized void load() {
        try (FileReader fileReader = new FileReader(Grasscutter.getConfig().DATA_FOLDER + "Drop.json")) {
            getDropData().clear();
            List<DropInfo> banners = Grasscutter.getGsonFactory().fromJson(fileReader, TypeToken.getParameterized(Collection.class, DropInfo.class).getType());
            if(banners.size() > 0) {
                for (DropInfo di : banners) {
                    getDropData().put(di.getMonsterId(), di.getDropDataList());
                }
                Grasscutter.getLogger().info("Drop data successfully loaded.");
            } else {
                Grasscutter.getLogger().error("Unable to load drop data. Drop data size is 0.");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void processDrop(DropData dd, EntityMonster em, Player gp) {
        int target = Utils.randomRange(1, 10000);
        if (target >= dd.getMinWeight() && target < dd.getMaxWeight()) {
            ItemData itemData = GameData.getItemDataMap().get(dd.getItemId());
            int num = Utils.randomRange(dd.getMinCount(), dd.getMaxCount());
            if (!dd.isGive()) {
                if (itemData.isEquip()) {
                    for (int i = 0; i < num; i++) {
                        float range = (5f + (.1f * num));
                        Position pos = em.getPosition().clone().addX((float) (Math.random() * range) - (range / 2)).addY(3f).addZ((float) (Math.random() * range) - (range / 2));
                        EntityItem entity = new EntityItem(em.getScene(), gp.getNextGameGuid(), itemData, num, dd.isShare(), pos);
                        if (!dd.isShare())
                            em.getScene().addEntityToSingleClient(gp, entity);
                        else
                            em.getScene().addEntity(entity);
                    }
                } else {
                    Position pos = em.getPosition().clone().addY(3f);
                    EntityItem entity = new EntityItem(em.getScene(), gp.getNextGameGuid(), itemData, num, dd.isShare(), pos);
                    if (!dd.isShare())
                        em.getScene().addEntityToSingleClient(gp, entity);
                    else
                        em.getScene().addEntity(entity);
                }
            }
        }
    }

    public void callDrop(EntityMonster em) {
        int id = em.getMonsterData().getId();
        if (getDropData().containsKey(id)) {
            for (DropData dd : getDropData().get(id)) {
                if (dd.isShare())
                    processDrop(dd, em, null);
                else {
                    for (Player gp : em.getScene().getPlayers()) {
                        processDrop(dd, em, gp);
                    }
                }
            }
        }
    }
}
