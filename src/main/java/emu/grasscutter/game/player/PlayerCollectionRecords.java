package emu.grasscutter.game.player;

import dev.morphia.annotations.Entity;
import java.util.*;

@Entity(useDiscriminator = false)
public class PlayerCollectionRecords {
    private Map<Integer, CollectionRecord> records;

    private Map<Integer, CollectionRecord> getRecords() {
        if (records == null) {
            records = new HashMap<>();
        }
        return records;
    }

    public void addRecord(int configId, long expiredMillisecond) {
        Map<Integer, CollectionRecord> records;
        synchronized (records = getRecords()) {
            records.put(
                    configId,
                    new CollectionRecord(configId, expiredMillisecond + System.currentTimeMillis()));
        }
    }

    public boolean findRecord(int configId) {
        Map<Integer, CollectionRecord> records;
        synchronized (records = getRecords()) {
            CollectionRecord record = records.get(configId);

            if (record == null) {
                return false;
            }

            boolean expired = record.getExpiredTime() < System.currentTimeMillis();

            if (expired) {
                records.remove(configId);
                return false;
            }

            return true;
        }
    }

    @Entity
    public static class CollectionRecord {
        private int configId;
        private long expiredTime;

        @Deprecated // Morphia
        public CollectionRecord() {}

        public CollectionRecord(int configId, long expiredTime) {
            this.configId = configId;
            this.expiredTime = expiredTime;
        }

        public int getConfigId() {
            return configId;
        }

        public long getExpiredTime() {
            return expiredTime;
        }
    }
}
