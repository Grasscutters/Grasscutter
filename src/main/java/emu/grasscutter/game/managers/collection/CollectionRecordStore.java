package emu.grasscutter.game.managers.collection;

import java.util.ArrayList;
import java.util.Iterator;

import dev.morphia.annotations.Entity;
import emu.grasscutter.utils.Position;

@Entity
public class CollectionRecordStore {
    @Entity
    static class Record{
        Position position;
        long expiredTime;
        long gadgetId;
        long scene;
        Record(Position position,
                long expiredTime,
                long gadgetId,
                long scene){
            this.position=position;
            this.expiredTime = expiredTime;
            this.gadgetId=gadgetId;
            this.scene=scene;

        }
    }
    private ArrayList<Record> gottenRecords;
    private ArrayList<Record> getRecords(){
        if(gottenRecords==null){
            gottenRecords = new ArrayList<>();
        }
        return gottenRecords;
    }
    public void addRecord(Position position,long gadgetId,long scene,long expiredMillisecond){
        ArrayList<Record> records;
        synchronized (records = getRecords()) {
            records.add(new Record(position,expiredMillisecond+System.currentTimeMillis(),gadgetId,scene));
        }
    }
    public boolean findRecord(Position position,long gadgetId,long scene){
        ArrayList<Record> records;
        synchronized (records = getRecords()) {
            long currentTime = System.currentTimeMillis();
            Iterator<Record> it = records.iterator();
            while (it.hasNext()) {
                Record record = it.next();
                if(record.gadgetId == gadgetId && record.scene == scene && record.position.equal3d(position)){
                    if (record.expiredTime < currentTime) {
                        it.remove();
                        return false;
                    }else{
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
