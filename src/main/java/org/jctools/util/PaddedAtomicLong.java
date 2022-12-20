/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jctools.util;

import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;

import static org.jctools.util.UnsafeAccess.UNSAFE;
import static org.jctools.util.UnsafeAccess.fieldOffset;

abstract class PaddedAtomicLongL1Pad extends Number implements java.io.Serializable {
    private static final long serialVersionUID = 1;

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
    byte b170,b171,b172,b173,b174,b175,b176,b177;//128b
}

abstract class PaddedAtomicLongL1Field extends PaddedAtomicLongL1Pad {
    private final static long VALUE_OFFSET = fieldOffset(PaddedAtomicLongL1Field.class, "value");
    private volatile long value;

    public void spVal(long v) {
        UNSAFE.putLong(this, VALUE_OFFSET, v);
    }
    public void soVal(long v) {
        UNSAFE.putOrderedLong(this, VALUE_OFFSET, v);
    }

    public void svVal(long v) {
        value = v;
    }

    public long lvVal() {
        return value;
    }
    public long lpVal() {
        return UNSAFE.getLong(this, VALUE_OFFSET);
    }

    public boolean casVal(long expectedV, long newV) {
        return UNSAFE.compareAndSwapLong(this, VALUE_OFFSET, expectedV, newV);
    }

    public long getAndSetVal(long v) {
        if (UnsafeAccess.SUPPORTS_GET_AND_ADD_LONG) {
            return UNSAFE.getAndSetLong(this, VALUE_OFFSET, v);
        }
        else {
            long currV;
            do {
                currV = lvVal();
            } while (!casVal(currV, v));
            return currV;
        }
    }

    public long getAndAddVal(long delta) {
        if (UnsafeAccess.SUPPORTS_GET_AND_ADD_LONG) {
            return UNSAFE.getAndAddLong(this, VALUE_OFFSET, delta);
        }
        else {
            long currV;
            do {
                currV = lvVal();
            } while (!casVal(currV, currV + delta));
            return currV;
        }
    }
}

abstract class PaddedAtomicLongL2Pad extends PaddedAtomicLongL1Field {

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
    byte b170,b171,b172,b173,b174,b175,b176,b177;//128b
}

/**
 * A padded version of the {@link java.util.concurrent.atomic.AtomicLong}.
 */
public class PaddedAtomicLong extends PaddedAtomicLongL2Pad {
   /**
     * Creates a new PaddedAtomicLong with initial value {@code 0}.
     */
    public PaddedAtomicLong() {
    }

    /**
     * Creates a new PaddedAtomicLong with the given initial value.
     *
     * @param initialValue the initial value
     */
    public PaddedAtomicLong(long initialValue) {
        svVal(initialValue);
    }

    /**
     * Gets the current value.
     *
     * @return the current value
     * @see java.util.concurrent.atomic.AtomicLong#get()
     */
    public long get() {
        return lvVal();
    }

    /**
     * Sets to the given value.
     *
     * @param newValue the new value
     * @see java.util.concurrent.atomic.AtomicLong#set(long)
     */
    public void set(long newValue) {
        svVal(newValue);
    }

    /**
     * Eventually sets to the given value.
     *
     * @param newValue the new value
     * @see java.util.concurrent.atomic.AtomicLong#lazySet(long)
     */
    public void lazySet(long newValue) {
        soVal(newValue);
    }

    /**
     * Atomically sets to the given value and returns the old value.
     *
     * @param newValue the new value
     * @return the previous value
     * @see java.util.concurrent.atomic.AtomicLong#getAndSet(long)
     */
    public long getAndSet(long newValue) {
        return getAndSetVal(newValue);
    }

    /**
     * Atomically sets the value to the given updated value
     * if the current value {@code ==} the expected value.
     *
     * @param expect the expected value
     * @param update the new value
     * @return {@code true} if successful. False return indicates that
     * the actual value was not equal to the expected value.
     * @see java.util.concurrent.atomic.AtomicLong#compareAndSet(long, long)
     */
    public boolean compareAndSet(long expect, long update) {
        return casVal(expect, update);
    }

    /**
     * Atomically sets the value to the given updated value
     * if the current value {@code ==} the expected value.
     *
     * <p><a href="package-summary.html#weakCompareAndSet">May fail
     * spuriously and does not provide ordering guarantees</a>, so is
     * only rarely an appropriate alternative to {@code compareAndSet}.
     *
     * @param expect the expected value
     * @param update the new value
     * @return {@code true} if successful
     * @see java.util.concurrent.atomic.AtomicLong#weakCompareAndSet(long, long)
     */
    public boolean weakCompareAndSet(long expect, long update) {
        return casVal(expect, update);
    }

    /**
     * Atomically increments the current value by 1.
     *
     * @return the previous value
     * @see java.util.concurrent.atomic.AtomicLong#getAndIncrement()
     */
    public long getAndIncrement() {
        return getAndAddVal(1L);
    }

    /**
     * Atomically decrements the current value by 1.
     *
     * @return the previous value
     * @see java.util.concurrent.atomic.AtomicLong#getAndDecrement()
     */
    public long getAndDecrement() {
        return getAndAddVal(-1L);
    }

    /**
     * Atomically adds to the current value the given value.
     *
     * @param delta the value to add
     * @return the previous value
     * @see java.util.concurrent.atomic.AtomicLong#getAndAdd(long)
     */
    public long getAndAdd(long delta) {
        return getAndAddVal(delta);
    }

    /**
     * Atomically increments the current value by one.
     *
     * @return the updated value
     * @see java.util.concurrent.atomic.AtomicLong#incrementAndGet()
     */
    public long incrementAndGet() {
        return getAndAddVal(1L) + 1L;
    }

    /**
     * Atomically decrements the current value by one.
     *
     * @return the updated value
     * @see java.util.concurrent.atomic.AtomicLong#decrementAndGet()
     */
    public long decrementAndGet() {
        return getAndAddVal(-1L) - 1L;
    }

    /**
     * Atomically adds to current value te given value.
     *
     * @param delta the value to add
     * @return the updated value
     * @see java.util.concurrent.atomic.AtomicLong#addAndGet(long)
     */
    public long addAndGet(long delta) {
        return getAndAddVal( delta) + delta;
    }

    /**
     * Atomically updates the current value with the results of
     * applying the given function, returning the previous value. The
     * function should be side-effect-free, since it may be re-applied
     * when attempted updates fail due to contention among threads.
     *
     * @param updateFunction a side-effect-free function
     * @return the previous value
     * @see java.util.concurrent.atomic.AtomicLong#getAndUpdate(LongUnaryOperator)
    */
    public long getAndUpdate(LongUnaryOperator updateFunction) {
        long prev, next;
        do {
            prev = lvVal();
            next = updateFunction.applyAsLong(prev);
        } while (!casVal(prev, next));
        return prev;
    }

    /**
     * Atomically updates the current value with the results of
     * applying the given function, returning the updated value. The
     * function should be side-effect-free, since it may be re-applied
     * when attempted updates fail due to contention among threads.
     *
     * @param updateFunction a side-effect-free function
     * @return the updated value
     * @see java.util.concurrent.atomic.AtomicLong#updateAndGet(LongUnaryOperator)
     */
    public long updateAndGet(LongUnaryOperator updateFunction) {
        long prev, next;
        do {
            prev = lvVal();
            next = updateFunction.applyAsLong(prev);
        } while (!casVal(prev, next));
        return next;
    }

    /**
     * Atomically updates the current value with the results of
     * applying the given function to the current and given values,
     * returning the previous value. The function should be
     * side-effect-free, since it may be re-applied when attempted
     * updates fail due to contention among threads.  The function
     * is applied with the current value as its first argument,
     * and the given update as the second argument.
     *
     * @param v the update value
     * @param f a side-effect-free function of two arguments
     * @return the previous value
     * @see java.util.concurrent.atomic.AtomicLong#getAndAccumulate(long, LongBinaryOperator)
     */
    public long getAndAccumulate(long v, LongBinaryOperator f) {
        long prev, next;
        do {
            prev = lvVal();
            next = f.applyAsLong(prev, v);
        } while (!casVal(prev, next));
        return prev;
    }


    /**
     * {@link java.util.concurrent.atomic.AtomicLong#accumulateAndGet(long, LongBinaryOperator)}
     */
    public long accumulateAndGet(long x, LongBinaryOperator f) {
        long prev, next;
        do {
            prev = lvVal();
            next = f.applyAsLong(prev, x);
        } while (!casVal(prev, next));
        return next;
    }

    /**
     * Returns the String representation of the current value.
     *
     * @return the String representation of the current value
     */
    @Override
    public String toString() {
        return Long.toString(lvVal());
    }

    /**
     * Returns the value as an {@code int}.
     *
     * @see java.util.concurrent.atomic.AtomicLong#intValue()
     */
    @Override
    public int intValue() {
        return (int) lvVal();
    }

    /**
     * Returns the value as a {@code long}.
     *
     * @see java.util.concurrent.atomic.AtomicLong#longValue()
     */
    @Override
    public long longValue() {
        return lvVal();
    }

    /**
     * Returns the value of a {@code float}.
     *
     * @see java.util.concurrent.atomic.AtomicLong#floatValue()
     */
    @Override
    public float floatValue() {
        return (float) lvVal();
    }

    /**
     * Returns the value of a {@code double}.
     *
     * @see java.util.concurrent.atomic.AtomicLong#doubleValue()
     */
    @Override
    public double doubleValue() {
        return (double) lvVal();
    }
}
