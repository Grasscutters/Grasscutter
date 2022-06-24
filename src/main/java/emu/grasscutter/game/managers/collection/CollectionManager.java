package emu.grasscutter.game.managers.collection;

import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.DataLoader;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.data.excels.SceneData;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.entity.gadget.GadgetContent;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass;
import emu.grasscutter.net.proto.GatherGadgetInfoOuterClass;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass;
import emu.grasscutter.net.proto.VisionTypeOuterClass;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public class CollectionManager {
    //GameData.getSceneDataMap();
    private final static HashMap<Integer, List<CollectionData>> CollectionResourcesData = new HashMap<>();
    private final HashMap<CollectionData,EntityGadget> spawnedEntities = new HashMap<>();
    private final ArrayList<CollectionData> gottenEntities = new ArrayList<>();
    Player player;
    static {
        try {
            Int2ObjectMap<SceneData> scenes = GameData.getSceneDataMap();
            for (int i = 0; i < scenes.size(); i++) {
                SceneData scene = scenes.get(i);
                if(scene!=null) {
                    int sceneId = scene.getId();
                    try (Reader fileReader = new InputStreamReader(DataLoader.load("collectionResources/" + sceneId + ".json"))) {
                        List<CollectionData> collectionDataList = Grasscutter.getGsonFactory().fromJson(fileReader, TypeToken.getParameterized(Collection.class, CollectionData.class).getType());
                        CollectionResourcesData.put(sceneId, collectionDataList);
                    } catch (Exception ignore) {

                    }
                }
            }
            Grasscutter.getLogger().info("Collection {} Scenes Resources Data successfully loaded.", CollectionResourcesData.size());
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
    private double computeDistance(Position a, Position b){
        double detX = a.getX()-b.getX();
        double detY = a.getY()-b.getY();
        return Math.sqrt(detX*detX+detY*detY);
    }
    public CollectionManager(Player player){
        this.player = player;
    }
    public void onGadgetEntities(int range){
        Scene scene = player.getScene();
        int sceneId = scene.getId();
        Position playerPosition = player.getPos();
        if(CollectionResourcesData.containsKey(sceneId)){
            ArrayList<GameEntity> addEntities = new ArrayList<>();
            ArrayList<GameEntity> removeEntities = new ArrayList<>();
            List<CollectionData> collectionDataList = CollectionResourcesData.get(sceneId);
            for(CollectionData data:collectionDataList){
                if(computeDistance(data.motionInfo.pos,playerPosition)<range){
                    if(!spawnedEntities.containsKey(data)) {
                        EntityGadget entityGadget = new EntityGadget(scene, data.gadget.gadgetId, data.motionInfo.pos, data.motionInfo.rot, new GadgetContent() {
                            @Override
                            public boolean onInteract(Player player, GadgetInteractReqOuterClass.GadgetInteractReq req) {
                                try{
                                    GameEntity gadget = scene.getEntityById(req.getGadgetEntityId());
                                    for (Map.Entry<CollectionData, EntityGadget> entry : spawnedEntities.entrySet()) {
                                        if (entry.getValue() == gadget) {
                                            int itemId = entry.getKey().gadget.gatherGadget.itemId;
                                            ItemData data = GameData.getItemDataMap().get(itemId);
                                            GameItem item = new GameItem(data, 1);
                                            player.getInventory().addItem(item, ActionReason.SubfieldDrop);
                                            scene.removeEntity(gadget, VisionTypeOuterClass.VisionType.VISION_TYPE_REMOVE);
                                            break;
                                        }
                                    }
                                }catch (Throwable e){
                                    e.printStackTrace();
                                }
                                return false;
                            }

                            @Override
                            public void onBuildProto(SceneGadgetInfoOuterClass.SceneGadgetInfo.Builder gadgetInfo) {
                                gadgetInfo.setIsEnableInteract(data.gadget.isEnableInteract);
                                gadgetInfo.setAuthorityPeerId(data.gadget.authorityPeerId);
                                if (data.gadget.gatherGadget != null) {
                                    gadgetInfo.setGatherGadget(
                                            GatherGadgetInfoOuterClass.GatherGadgetInfo.newBuilder().setItemId(data.gadget.gatherGadget.itemId).build()
                                    );
                                }
                            }
                        });
                        entityGadget.setConfigId(data.gadget.configId);
                        entityGadget.setGroupId(data.gadget.groupId);
                        for (CollectionData.Prop prop : data.fightPropList) {
                            entityGadget.setFightProperty(FightProperty.getPropById(prop.propType), prop.propValue);
                        }
                        spawnedEntities.put(data,entityGadget);
                        addEntities.add(entityGadget);
                    }
                }else{ // out of range
                    if(spawnedEntities.containsKey(data)) {
                        removeEntities.add(spawnedEntities.get(data));
                        spawnedEntities.remove(data);
                    }
                }
            }
            if(removeEntities.size()>0) {
                scene.removeEntities(removeEntities,VisionTypeOuterClass.VisionType.VISION_TYPE_REMOVE);
            }
            if(addEntities.size()>0) {
                scene.addEntities(addEntities,VisionTypeOuterClass.VisionType.VISION_TYPE_MEET);
            }
        }else{
            Grasscutter.getLogger().warn("Collection Scene {} Resources Data not found.",sceneId);
        }
    }

}
