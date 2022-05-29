package emu.grasscutter.game.managers.DeforestationManager;

import java.util.ArrayList;
import java.util.HashMap;

import dev.morphia.annotations.Transient;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.entity.EntityItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.HitTreeNotifyOuterClass;
import emu.grasscutter.net.proto.VectorOuterClass;
import emu.grasscutter.utils.Position;

public class DeforestationManager {
    @Transient private final Player player;
    @Transient private final ArrayList<HitTreeRecord> currentRecord;
    @Transient private final static HashMap<Integer, Integer> ColliderTypeToWoodItemID = new HashMap<>();
    static {
        /* define wood types which reflected to item id*/
        ColliderTypeToWoodItemID.put(1,101301);
        ColliderTypeToWoodItemID.put(2,101302);
        ColliderTypeToWoodItemID.put(3,101303);
        ColliderTypeToWoodItemID.put(4,101304);
        ColliderTypeToWoodItemID.put(5,101305);
        ColliderTypeToWoodItemID.put(6,101306);
        ColliderTypeToWoodItemID.put(7,101307);
        ColliderTypeToWoodItemID.put(8,101308);
        ColliderTypeToWoodItemID.put(9,101309);
        ColliderTypeToWoodItemID.put(10,101310);
        ColliderTypeToWoodItemID.put(11,101311);
        ColliderTypeToWoodItemID.put(12,101312);
    }
    public DeforestationManager(Player player){
        this.player = player;
        this.currentRecord = new ArrayList<>();
    }
    public void resetWood(){
        synchronized (currentRecord) {
            currentRecord.clear();
        }
    }
    public void onDeforestationInvoke(HitTreeNotifyOuterClass.HitTreeNotify hit){
        synchronized (currentRecord) {
            Grasscutter.getLogger().info("onDeforestationInvoke! Wood records {}", currentRecord);

            currentRecord.removeIf(HitTreeRecord::isInvalidRecord);

            VectorOuterClass.Vector hitPosition = hit.getHitPostion();
            int woodType = hit.getWoodType();
            if (ColliderTypeToWoodItemID.containsKey(woodType)) {// is a available wood type
                Scene scene = player.getScene();
                int itemId = ColliderTypeToWoodItemID.get(woodType);
                int positionHash = hitPosition.hashCode();
                HitTreeRecord record = searchRecord(positionHash);
                if (record == null) {
                    record = new HitTreeRecord(positionHash);
                    currentRecord.add(record);
                }
                for (HitTreeRecord everyRecord : currentRecord) {
                    if (everyRecord != record) {
                        everyRecord.recordOtherTree(positionHash);
                    }
                }
                if(record.record()) {
                    EntityItem entity = new EntityItem(scene,
                            null,
                            GameData.getItemDataMap().get(itemId),
                            new Position(hitPosition.getX(), hitPosition.getY(), hitPosition.getZ()),
                            1,
                            false);
                    scene.addEntity(entity);
                }
                //record.record()=false : too many wood they have deforested, no more wood dropped!
            } else {
                Grasscutter.getLogger().warn("No wood type {} found.", woodType);
            }
        }
        // unknown wood type
    }
    private HitTreeRecord searchRecord(int id){
        for (HitTreeRecord record : currentRecord) {
            if (record.getUnique() == id) {
                return record;
            }
        }
        return null;
    }
}
