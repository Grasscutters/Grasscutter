package emu.grasscutter.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Map's KEY is automatic expire if key long time no use (query or modify from HashMap)
 *
 * WARNING: If you want to discard this instance, please call clear() method to ensure recycle timer is closed,
 * or memory leak emerged!!!!
 *
 * @author zhangshuai(CSDN) & 赵怡然(Github: @zhaodice)
 */
public class AutoRecycleHashMap<K, V> extends HashMap<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 8;

    private int expireSecond;

    // auto recycler
    private Timer timer = null;

    // expired time container
    private Map<Object, Long> timerMap;

    // callback when data expired
    @SuppressWarnings("rawtypes")
    private TimerExpireHashMapCallback timerExpireHashMapCallback ;

    public AutoRecycleHashMap(int expireSecond) {
        super();
        init(expireSecond);
    }

    /**
     * init expired time record container
     */
    private void init(int expireSecond) {
        this.timerMap = new HashMap<>(AutoRecycleHashMap.DEFAULT_INITIAL_CAPACITY);
        this.expireSecond = expireSecond;
    }

    /**
     * get data
     */
    @Override
    public V get(Object key) {
        Long expireTime = checkKeyExpireTime(key);
        if (expireTime == null || expireTime > 0) {
            setKeyExpireTime(key);
            return super.get(key);
        }
        return null;
    }

    /**
     * put data
     */
    public V put(K key, V value) {
        synchronized (this) {
            setKeyExpireTime(key);
            if (timer == null) {
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void run() {
                        synchronized (AutoRecycleHashMap.this) {
                            long currentTime = System.currentTimeMillis();
                            Iterator<Object> it = timerMap.keySet().iterator();
                            while(it.hasNext()){
                                Object key = it.next();
                                Long keyTime = timerMap.get(key);
                                if (currentTime >= keyTime) {
                                    if (timerExpireHashMapCallback != null) {
                                        try {
                                            timerExpireHashMapCallback.callback(key, AutoRecycleHashMap.super.get(key));
                                        } catch (RuntimeException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    asyncRemove(key);
                                    it.remove();
                                }
                            }
                        }
                    }
                }, 0, (expireSecond / 20 + 1) * 1000L);
            }
            return super.put(key, value);
        }
    }

    /**
     * return seconds how long the key will expired
     */
    public Long checkKeyExpireTime(Object key) {
        Long second = timerMap.get(key);
        if(second == null) {
            return null;
        }
        long currentTime = System.currentTimeMillis();
        return ((second - currentTime) / 1000);
    }

    /**
     * update seconds how long the key will expired
     */
    public void setKeyExpireTime(Object key) {
        long currentTime = System.currentTimeMillis();
        long expireTime = currentTime + (expireSecond * 1000L);
        synchronized (this) {
            timerMap.put(key, expireTime);
        }
    }

    /**
     * callback for expired data
     */
    public void setTimerExpireHashMapCallback(TimerExpireHashMapCallback<K, V> timerExpireHashMapCallback) {
        this.timerExpireHashMapCallback = timerExpireHashMapCallback;
    }

    interface TimerExpireHashMapCallback<K, V> {
        void callback(K key, V value) throws RuntimeException;
    }

    @Override
    public void clear() {
        super.clear();
        checkEmptyForStopTimer();
    }

    @Override
    public V remove(Object key) {
        synchronized (this) {
            return asyncRemove(key);
        }
    }
    V asyncRemove(Object key){
        V v = super.remove(key);
        checkEmptyForStopTimer();
        return v;
    }
    private void checkEmptyForStopTimer(){
        if (timer!=null && isEmpty()) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }
}

