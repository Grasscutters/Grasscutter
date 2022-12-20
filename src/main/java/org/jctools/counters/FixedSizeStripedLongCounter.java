package org.jctools.counters;

import static org.jctools.util.UnsafeAccess.UNSAFE;

import java.util.concurrent.ThreadLocalRandom;

import org.jctools.util.PortableJvmInfo;
import org.jctools.util.Pow2;

/**
 * Basic class representing static striped long counter with
 * common mechanics for implementors.
 *
 * @author Tolstopyatov Vsevolod
 */
abstract class FixedSizeStripedLongCounterPrePad {
    byte b000,b001,b002,b003,b004,b005,b006,b007;//  8b
    byte b010,b011,b012,b013,b014,b015,b016,b017;// 16b
    byte b020,b021,b022,b023,b024,b025,b026,b027;// 24b
    byte b030,b031,b032,b033,b034,b035,b036,b037;// 32b
    byte b040,b041,b042,b043,b044,b045,b046,b047;// 40b
    byte b050,b051,b052,b053,b054,b055,b056,b057;// 48b
    byte b060,b061,b062,b063,b064,b065,b066,b067;// 56b
    byte b070,b071,b072,b073,b074,b075,b076,b077;// 64b
    byte b100,b101,b102,b103,b104,b105,b106,b107;// 72b
    byte b110,b111,b112,b113,b114,b115,b116,b117;// 80b
    byte b120,b121,b122,b123,b124,b125,b126,b127;// 88b
    byte b130,b131,b132,b133,b134,b135,b136,b137;// 96b
    byte b140,b141,b142,b143,b144,b145,b146,b147;//104b
    byte b150,b151,b152,b153,b154,b155,b156,b157;//112b
    byte b160,b161,b162,b163,b164,b165,b166,b167;//120b
    // byte b170,b171,b172,b173,b174,b175,b176,b177;//128b
}
abstract class FixedSizeStripedLongCounterFields extends FixedSizeStripedLongCounterPrePad {
    protected static final int CACHE_LINE_IN_LONGS = PortableJvmInfo.CACHE_LINE_SIZE / 8;
    // place first element at the end of the cache line of the array object
    protected static final long COUNTER_ARRAY_BASE = Math.max(UNSAFE.arrayBaseOffset(long[].class), PortableJvmInfo.CACHE_LINE_SIZE - 8);
    // element shift is enlarged to include the padding, still aligned to long
    protected static final long ELEMENT_SHIFT = Integer.numberOfTrailingZeros(PortableJvmInfo.CACHE_LINE_SIZE);

    // we pad each element in the array to effectively write a counter in each cache line
    protected final long[] cells;
    protected final int mask;
    protected FixedSizeStripedLongCounterFields(int stripesCount) {
        if (stripesCount <= 0) {
            throw new IllegalArgumentException("Expecting a stripesCount that is larger than 0");
        }
        int size = Pow2.roundToPowerOfTwo(stripesCount);
        cells = new long[CACHE_LINE_IN_LONGS * size];
        mask = (size - 1);
    }
}

public abstract class FixedSizeStripedLongCounter extends FixedSizeStripedLongCounterFields implements Counter {
    byte b000,b001,b002,b003,b004,b005,b006,b007;//  8b
    byte b010,b011,b012,b013,b014,b015,b016,b017;// 16b
    byte b020,b021,b022,b023,b024,b025,b026,b027;// 24b
    byte b030,b031,b032,b033,b034,b035,b036,b037;// 32b
    byte b040,b041,b042,b043,b044,b045,b046,b047;// 40b
    byte b050,b051,b052,b053,b054,b055,b056,b057;// 48b
    byte b060,b061,b062,b063,b064,b065,b066,b067;// 56b
    byte b070,b071,b072,b073,b074,b075,b076,b077;// 64b
    byte b100,b101,b102,b103,b104,b105,b106,b107;// 72b
    byte b110,b111,b112,b113,b114,b115,b116,b117;// 80b
    byte b120,b121,b122,b123,b124,b125,b126,b127;// 88b
    byte b130,b131,b132,b133,b134,b135,b136,b137;// 96b
    byte b140,b141,b142,b143,b144,b145,b146,b147;//104b
    byte b150,b151,b152,b153,b154,b155,b156,b157;//112b
    byte b160,b161,b162,b163,b164,b165,b166,b167;//120b
    //byte b170,b171,b172,b173,b174,b175,b176,b177;//128b

    private static final long PROBE = getProbeOffset();

    private static long getProbeOffset() {
        try {
            return UNSAFE.objectFieldOffset(Thread.class.getDeclaredField("threadLocalRandomProbe"));

        } catch (NoSuchFieldException e) {
            return -1L;
        }
    }

    public FixedSizeStripedLongCounter(int stripesCount) {
        super(stripesCount);
    }

    @Override
    public void inc() {
        inc(1L);
    }

    @Override
    public void inc(long delta) {
        inc(cells, counterOffset(index()), delta);
    }

    @Override
    public long get() {
        long result = 0L;
        long[] cells = this.cells;
        int length = mask + 1;
        for (int i = 0; i < length; i++) {
            result += UNSAFE.getLongVolatile(cells, counterOffset(i));
        }
        return result;
    }

    private long counterOffset(long i) {
        return COUNTER_ARRAY_BASE + (i << ELEMENT_SHIFT);
    }

    @Override
    public long getAndReset() {
        long result = 0L;
        long[] cells = this.cells;
        int length = mask + 1;
        for (int i = 0; i < length; i++) {
            result += getAndReset(cells, counterOffset(i));
        }
        return result;
    }

    protected abstract void inc(long[] cells, long offset, long value);

    protected abstract long getAndReset(long[] cells, long offset);

    private int index() {
        return probe() & mask;
    }


    /**
     * Returns the probe value for the current thread.
     * If target JDK version is 7 or higher, than ThreadLocalRandom-specific
     * value will be used, xorshift with thread id otherwise.
     */
    private int probe() {
        // Fast path for reliable well-distributed probe, available from JDK 7+.
        // As long as PROBE is final static this branch will be constant folded
        // (i.e removed).
        if (PROBE != -1) {
            int probe;
            if ((probe = UNSAFE.getInt(Thread.currentThread(), PROBE)) == 0) {
                ThreadLocalRandom.current(); // force initialization
                probe = UNSAFE.getInt(Thread.currentThread(), PROBE);
            }
            return probe;
        }

        /*
         * Else use much worse (for values distribution) method:
         * Mix thread id with golden ratio and then xorshift it
         * to spread consecutive ids (see Knuth multiplicative method as reference).
         */
        int probe = (int) ((Thread.currentThread().getId() * 0x9e3779b9) & Integer.MAX_VALUE);
        // xorshift
        probe ^= probe << 13;
        probe ^= probe >>> 17;
        probe ^= probe << 5;
        return probe;
    }
}

