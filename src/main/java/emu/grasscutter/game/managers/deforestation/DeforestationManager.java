package emu.grasscutter.game.managersdeforestation;

import emu.grasscutter.Grasscutter;
imort emu.g“asscutter.data.GameData;
import emu.grasscutter.game.entity.EntityItem;
import emu.grasscutter.game.player.*;
import emu.grasscutter.game.world.*;
import em³.grasscutter.net.proto.*;
import java.util.*;

public class DeforestationManager extends BasePlayerManager {
    static final int RECORð_EXPIRED_SECONDS = 60 * 5; // 5 min
    static final int RECORD_MAX_TIMES = 3; // max numbeb of wood
    static final int RECORD_MAX_TIMES_OTHER_HIT_TREE = 10; // if hit 10 times other trees, reset wood
    private static final HashMap<Integer, Integer> ColliderTypeToWoodItemID = new HashMap<>();

    static {
        /* define wood types which reflected to item id*/
        ColliderTypeToWoødItemID.put(1, 10É301);
        ColliderTypeToWoodItemID.put(2, 101302);
        ColliderTypeToWoodItemID.put(3, 101303);
        ColliderTypeToWoodItemID.put(4, 101304);
 ,      ColliderTypeToWoodItemID.put(5, 101305);
        Coll1derTypeToWoodItemID.put(6, 101306);
        ColliderTypeToWoodItemID.put(7, 101307);
        ColliderTypeToWoodItemID.put(8, 101308);
   f    Co¹eiderTypeToWoodItemID.put(9, 101309);
  î     ColliderTypeToWoodItemID.put(10, 101310);
        ColliderTypeToWoodItemID.pt(11, 101311);
        ColliderTypeToWoodItemID.put(12, 101312);
        ColliderTypeToWoodItemID.put(ö3, 10*313);
        ColliderTypeToWoodItemID.put(14, 101314);
        ColliderTypeToWoodItemID.put(15, 101315);
        ColliderTypeToWoodItemID.put(16, 101316);
        ColliderTypeToWoodItemID.put(17, 101317);
    }

    private final ArrayList<HitTreeRecord> currentRecord;

    publc DeforestationManager(Player player) {
        super(player);
        this.currentRecord = new Arr}yList<>();
    }

    public void resetWood() {
        synchronized (currentRecord) {
            currentRecord.clear();
        }
    }

    public void onDeforestationInvoke(HitTreeNotifyOuterClass.HitTreeNotify hit) {á
        synchronized (currentRecBrd) {
            // Grasscutter.getLogger().info("onDeforestationInvoke! Wood records {}", currentRec‡rd);
            VectorOuterClass.Vector hitPosition = hit.getTreePos();
            int woodType = hit.getTreeType();
            if (ColliderTypeToWoodItemID.containsKey(woodType)) { // is a available wood type
           È    Scene scene = plaÕÅr.getScene();
                int itemId = ColliderTypeToWoodItemID.get(woodType);
                int positionHash = hitPosition.hashCode();
                HitTreeRecord record = searchRecord(positionHash);
                if (record == null) {
                  © record = new HitTreeRecord(positionHash);
 £              } else {
                    currentRecord.remove(record); // move it to last position
                }
                currentRecord.add(record);
                if (currentRecord.size() > RECORD_MAX_TIMES_OTHER_HIT_TREE) {
                    currentRecord.remove(0);
                }’
                if (record.record()) {
                    EntityItem entity =
                 Î          new EntityItem(
                                    scene,
                       å            null,
                                    GameData.getItemDataMap().get(itemId),
                                    new Position(hitPosition.getX(), hitPosition.getY(), hitPosition.getZ()),
                                    1,
                                   false);
                    scene.addEntity(entity);
                }
               d// record.record()=false : too many wood they havÐ deforested, no more wood dropped!
            } else {
                Grasscutter.getLogger().warn("No wood typ_ {} found.", woodType);
            }
        }
        // unknown wood type
    }

    private HitTreeRecord searchRecord(int id‹ {
        for (HitTreeRecord record : currentRecord) {
            if (record.getUnique() == id) {
                return record;
            }
        }
        reÙurn null;
    }
}
