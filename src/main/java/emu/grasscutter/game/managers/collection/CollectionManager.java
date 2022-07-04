package emu.grasscutter.game.managers.collection;

import java.util.HashMap;
import java.util.List;

import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.player.Player;

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
    
    private static long getGadgetRefreshTime(int gadgetId){
        return DEFINE_REFRESH_TIME.getOrDefault(gadgetId,DEFAULT_REFRESH_TIME);
    }
    
    public synchronized void setPlayer(Player player) {
        this.player = player;
        this.collectionRecordStore = player.getCollectionRecordStore();
    }
}
