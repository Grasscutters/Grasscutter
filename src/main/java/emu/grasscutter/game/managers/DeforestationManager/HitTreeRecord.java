package emu.grasscutter.game.managers.DeforestationManager;


import java.util.ArrayList;

public class HitTreeRecord {
    private final static int RECORD_EXPIRED_SECONDS = 60*5; // 5 min
    private final static int RECORD_MAX_TIMES = 3; // max number of wood
    private final static int RECORD_MAX_TIMES_OTHER_HIT_TREE = 10; // if hit 10 times other trees, reset wood

    private final int unique;
    private final ArrayList<Integer> otherTrees = new ArrayList<>(); // hit other tree
    private short count; // hit this tree times
    private long time; // last available hitting time
    HitTreeRecord(int unique){
        this.count = 0;
        this.time = 0;
        this.unique = unique;
    }

    /**
     * reset hit time
     */
    private void resetTime(){
        this.time = System.currentTimeMillis();
    }
    /**
     * @return true if hit record could be ignored
     */
    public boolean isInvalidRecord(){
        if(this.time>0){
            if(this.otherTrees.size()<RECORD_MAX_TIMES_OTHER_HIT_TREE){// not enough times for hitting others
                // not expired
                return System.currentTimeMillis() - this.time >= RECORD_EXPIRED_SECONDS * 1000L;
            }
        }
        return true;
    }
    /**
     * commit hit behavior
     */
    public boolean record(){
        this.otherTrees.clear();
        if(this.count < RECORD_MAX_TIMES) {
            this.count++;
            resetTime();
            return true;
        }
        return false;
    }
    /**
     * commit hit other tree behavior
     */
    public void recordOtherTree(int unique){
        if(!isInvalidRecord()) {
            if (!this.otherTrees.contains(unique)) {
                this.otherTrees.add(unique);
            }
        }
    }
    /**
     * get unique id
     */
    public int getUnique(){
        return unique;
    }

    @Override
    public String toString() {
        return "HitTreeRecord{" +
                "unique=" + unique +
                ", countOtherTree=" + this.otherTrees.size() +
                ", count=" + count +
                ", time=" + time +
                '}';
    }
}
