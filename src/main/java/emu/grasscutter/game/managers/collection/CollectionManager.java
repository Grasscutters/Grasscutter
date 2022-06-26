package emu.grasscutter.game.managers.collection;

import com.google.gson.reflect.TypeToken;
import com.google.protobuf.InvalidProtocolBufferException;

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
import emu.grasscutter.game.entity.EntityBaseGadget;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.EntityItem;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.entity.gadget.GadgetContent;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.AbilityInvokeArgumentOuterClass.*;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.*;
import emu.grasscutter.net.proto.AbilityMetaModifierChangeOuterClass.*;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass;
import emu.grasscutter.net.proto.GatherGadgetInfoOuterClass;
import emu.grasscutter.net.proto.ModifierActionOuterClass;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass;
import emu.grasscutter.net.proto.VisionTypeOuterClass;
import emu.grasscutter.server.packet.send.PacketSceneEntityDisappearNotify;
import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public class CollectionManager {
    private static final long SECOND = 1000; //1 Second
    private static final long MINUTE = SECOND*60; //1 Minute
    private static final long HOUR = MINUTE*60; //1 Hour
    private static final long DAY = HOUR*24; //1 Day
    private static final HashMap<Integer,Long> DEFINE_REFRESH_TIME = new HashMap<>();// <GadgetId,Waiting Millisecond>
    private static final long DEFAULT_REFRESH_TIME = HOUR*6; // default 6 Hours

    static {
        DEFINE_REFRESH_TIME.put(70590027,3*DAY);//星银矿石 3 Days
        DEFINE_REFRESH_TIME.put(70590036,3*DAY);//紫晶块 3 Days
        DEFINE_REFRESH_TIME.put(70520003,3*DAY);//水晶 3 Days

        DEFINE_REFRESH_TIME.put(70590013,2*DAY);//嘟嘟莲 2 Days
        DEFINE_REFRESH_TIME.put(70540029,2*DAY);//清心 2 Days
        DEFINE_REFRESH_TIME.put(70540028,2*DAY);//星螺 2 Days
        DEFINE_REFRESH_TIME.put(70540027,2*DAY);//马尾 2 Days
        DEFINE_REFRESH_TIME.put(70540026,2*DAY);//琉璃袋 2 Days
        DEFINE_REFRESH_TIME.put(70540022,2*DAY);//落落莓 2 Days
        DEFINE_REFRESH_TIME.put(70540020,2*DAY);//慕风蘑菇 2 Days
        DEFINE_REFRESH_TIME.put(70540019,2*DAY);//风车菊 2 Days
        DEFINE_REFRESH_TIME.put(70540018,2*DAY);//塞西莉亚花 2 Days
        DEFINE_REFRESH_TIME.put(70540015,2*DAY);//霓裳花 2 Days
        DEFINE_REFRESH_TIME.put(70540014,2*DAY);//莲蓬 2 Days 
        DEFINE_REFRESH_TIME.put(70540013,2*DAY);//钩钩果 2 Days
        DEFINE_REFRESH_TIME.put(70540012,2*DAY);//琉璃百合 2 Days
        DEFINE_REFRESH_TIME.put(70540008,2*DAY);//绝云椒椒 2 Days
        DEFINE_REFRESH_TIME.put(70520018,2*DAY);//夜泊石 2 Days
        DEFINE_REFRESH_TIME.put(70520002,2*DAY);//白铁矿 2 Days
        DEFINE_REFRESH_TIME.put(70510012,2*DAY);//石珀 2 Days
        DEFINE_REFRESH_TIME.put(70510009,2*DAY);//蒲公英 2 Days
        DEFINE_REFRESH_TIME.put(70510007,2*DAY);//冰雾花 2 Days
        DEFINE_REFRESH_TIME.put(70510006,2*DAY);//烈焰花 2 Days
        DEFINE_REFRESH_TIME.put(70510005,2*DAY);//电气水晶 2 Days
        DEFINE_REFRESH_TIME.put(70510004,2*DAY);//小灯草 2 Days


        DEFINE_REFRESH_TIME.put(70540021,DAY);//日落果 1 Day
        DEFINE_REFRESH_TIME.put(70540005,DAY);//松果 1 Day
        DEFINE_REFRESH_TIME.put(70540003,DAY);//苹果 1 Day
        DEFINE_REFRESH_TIME.put(70540001,DAY);//树莓 1 Day
        DEFINE_REFRESH_TIME.put(70520019,DAY);//魔晶块 1 Days
        DEFINE_REFRESH_TIME.put(70520008,DAY);//金鱼草 1 Days
        DEFINE_REFRESH_TIME.put(70520007,DAY);//白萝卜 1 Days
        DEFINE_REFRESH_TIME.put(70520006,DAY);//胡萝卜 1 Days
        DEFINE_REFRESH_TIME.put(70520004,DAY);//蘑菇 1 Day
        DEFINE_REFRESH_TIME.put(70520001,DAY);//铁矿 1 Day

        DEFINE_REFRESH_TIME.put(70520009,12*HOUR);//薄荷 12 Hours
        DEFINE_REFRESH_TIME.put(70520005,12*HOUR);//甜甜花 12 Hours
    }
    
    private final static HashMap<Integer, List<CollectionData>> CollectionResourcesData = new HashMap<>();
    private final HashMap<CollectionData,EntityGadget> spawnedEntities = new HashMap<>();
    private CollectionRecordStore collectionRecordStore;
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
                        collectionDataList.removeIf(collectionData -> collectionData.gadget == null);
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
    private static long getGadgetRefreshTime(int gadgetId){
        return DEFINE_REFRESH_TIME.getOrDefault(gadgetId,DEFAULT_REFRESH_TIME);
    }
    
    public synchronized void setPlayer(Player player) {
        this.player = player;
        this.collectionRecordStore = player.getCollectionRecordStore();
    }
    public synchronized void onGadgetEntities(int range){
        Scene scene = player.getScene();
        int sceneId = scene.getId();
        Position playerPosition = player.getPos();
        if(CollectionResourcesData.containsKey(sceneId)){
            ArrayList<GameEntity> addEntities = new ArrayList<>();
            ArrayList<GameEntity> removeEntities = new ArrayList<>();
            List<CollectionData> collectionDataList = CollectionResourcesData.get(sceneId);
            for(CollectionData data:collectionDataList){
                if(data.motionInfo.pos.computeDistance(playerPosition)<range){
                    if(!spawnedEntities.containsKey(data)) {
                        if(collectionRecordStore.findRecord(data.motionInfo.pos,data.gadget.gadgetId,sceneId)){
                            continue;
                        }
                        EntityGadget entityGadget = new EntityGadget(scene, data.gadget.gadgetId, data.motionInfo.pos, data.motionInfo.rot, new GadgetContent() {
                            @Override
                            public boolean onInteract(Player player, GadgetInteractReqOuterClass.GadgetInteractReq req) {
                                synchronized (CollectionManager.this) {
                                    try {
                                        GameEntity gadget = scene.getEntityById(req.getGadgetEntityId());
                                        for (Map.Entry<CollectionData, EntityGadget> entry : spawnedEntities.entrySet()) {
                                            if (entry.getValue() == gadget) {
                                                CollectionData.Gadget gadgetInfo = entry.getKey().gadget;
                                                int itemId = gadgetInfo.gatherGadget.itemId;
                                                ItemData data = GameData.getItemDataMap().get(itemId);
                                                GameItem item = new GameItem(data, 1);
                                                player.getInventory().addItem(item, ActionReason.SubfieldDrop);
                                                scene.removeEntity(gadget, VisionTypeOuterClass.VisionType.VISION_TYPE_REMOVE);
                                                collectionRecordStore.addRecord(gadget.getPosition(), gadgetInfo.gadgetId, sceneId, getGadgetRefreshTime(gadgetInfo.gadgetId));
                                                return true;
                                            }
                                        }
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
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
            try {
                if (removeEntities.size() > 0) {
                    scene.removeEntities(removeEntities, VisionTypeOuterClass.VisionType.VISION_TYPE_REMOVE);
                }
            }catch (Throwable ignored){

            }
            try {
                if(addEntities.size()>0) {
                    scene.addEntities(addEntities,VisionTypeOuterClass.VisionType.VISION_TYPE_MEET);
                }
            }catch (Throwable ignored){

            }
        }else{
            Grasscutter.getLogger().warn("Collection Scene {} Resources Data not found.",sceneId);
        }
    }
    public synchronized CollectionData findCollection(int entityId){
        for (Map.Entry<CollectionData, EntityGadget> entry : spawnedEntities.entrySet()) {
            if (entry.getValue().getId() == entityId) {
                return entry.getKey();
            }
        }
        return null;
    }
    public synchronized boolean onRockDestroy(AbilityInvokeEntry abilityInvokeEntry) {
        Scene scene = player.getScene();
        int entityId = abilityInvokeEntry.getEntityId();
        CollectionData collectionData = findCollection(entityId);
        if(collectionData==null){
            return false;
        }
        GameEntity targetEntity = scene.getEntityById(entityId);
        // Make sure the target is an gadget.
        if (!(targetEntity instanceof EntityGadget targetGadget)) {
            return false;
        }
        if(abilityInvokeEntry.getArgumentType() == AbilityInvokeArgument.ABILITY_INVOKE_ARGUMENT_META_MODIFIER_CHANGE){
            try {
                AbilityMetaModifierChange data = AbilityMetaModifierChange.parseFrom(abilityInvokeEntry.getAbilityData());
                if (data.getAction() == ModifierActionOuterClass.ModifierAction.REMOVED) {
                    CollectionData.Gadget gadgetInfo = collectionData.gadget;
                    int itemId = gadgetInfo.gatherGadget.itemId;
                    Position hitPosition = targetEntity.getPosition();
                    int times = Utils.randomRange(1,2);
                    for(int i=0;i<times;i++) {
                        EntityItem entity = new EntityItem(scene,
                                player,
                                GameData.getItemDataMap().get(itemId),
                                new Position(
                                        hitPosition.getX()+(float)Utils.randomRange(1,5)/5,
                                        hitPosition.getY()+2f,
                                        hitPosition.getZ()+(float)Utils.randomRange(1,5)/5
                                ),
                                1,
                                false);
                        scene.addEntity(entity);
                    }
                    scene.killEntity(targetGadget,player.getTeamManager().getCurrentAvatarEntity().getId());
                    collectionRecordStore.addRecord(targetGadget.getPosition(),gadgetInfo.gadgetId,scene.getId(),getGadgetRefreshTime(gadgetInfo.gadgetId));
                    spawnedEntities.remove(collectionData);
                }
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
