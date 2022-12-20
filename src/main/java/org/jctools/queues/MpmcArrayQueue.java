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
package org.jctools.queues;

import org.jctools.util.RangeUtil;

import static org.jctools.util.UnsafeAccess.UNSAFE;
import static org.jctools.util.UnsafeAccess.fieldOffset;
import static org.jctools.util.UnsafeLongArrayAccess.*;
import static org.jctools.util.UnsafeRefArrayAccess.*;

abstract class MpmcArrayQueueL1Pad<E> extends ConcurrentSequencedCircularArrayQueue<E>
{
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

    MpmcArrayQueueL1Pad(int capacity)
    {
        super(capacity);
    }
}

//$gen:ordered-fields
abstract class MpmcArrayQueueProducerIndexField<E> extends MpmcArrayQueueL1Pad<E>
{
    private final static long P_INDEX_OFFSET = fieldOffset(MpmcArrayQueueProducerIndexField.class, "producerIndex");

    private volatile long producerIndex;

    MpmcArrayQueueProducerIndexField(int capacity)
    {
        super(capacity);
    }

    @Override
    public final long lvProducerIndex()
    {
        return producerIndex;
    }

    final boolean casProducerIndex(long expect, long newValue)
    {
        return UNSAFE.compareAndSwapLong(this, P_INDEX_OFFSET, expect, newValue);
    }
}

abstract class MpmcArrayQueueL2Pad<E> extends MpmcArrayQueueProducerIndexField<E>
{
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

    MpmcArrayQueueL2Pad(int capacity)
    {
        super(capacity);
    }
}

//$gen:ordered-fields
abstract class MpmcArrayQueueConsumerIndexField<E> extends MpmcArrayQueueL2Pad<E>
{
    private final static long C_INDEX_OFFSET = fieldOffset(MpmcArrayQueueConsumerIndexField.class, "consumerIndex");

    private volatile long consumerIndex;

    MpmcArrayQueueConsumerIndexField(int capacity)
    {
        super(capacity);
    }

    @Override
    public final long lvConsumerIndex()
    {
        return consumerIndex;
    }

    final boolean casConsumerIndex(long expect, long newValue)
    {
        return UNSAFE.compareAndSwapLong(this, C_INDEX_OFFSET, expect, newValue);
    }
}

abstract class MpmcArrayQueueL3Pad<E> extends MpmcArrayQueueConsumerIndexField<E>
{
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

    MpmcArrayQueueL3Pad(int capacity)
    {
        super(capacity);
    }
}

/**
 * A Multi-Producer-Multi-Consumer queue based on a {@link org.jctools.queues.ConcurrentCircularArrayQueue}. This
 * implies that any and all threads may call the offer/poll/peek methods and correctness is maintained. <br>
 * This implementation follows patterns documented on the package level for False Sharing protection.<br>
 * The algorithm for offer/poll is an adaptation of the one put forward by D. Vyukov (See <a
 * href="http://www.1024cores.net/home/lock-free-algorithms/queues/bounded-mpmc-queue">here</a>). The original
 * algorithm uses an array of structs which should offer nice locality properties but is sadly not possible in
 * Java (waiting on Value Types or similar). The alternative explored here utilizes 2 arrays, one for each
 * field of the struct. There is a further alternative in the experimental project which uses iteration phase
 * markers to achieve the same algo and is closer structurally to the original, but sadly does not perform as
 * well as this implementation.<br>
 * <p>
 * Tradeoffs to keep in mind:
 * <ol>
 * <li>Padding for false sharing: counter fields and queue fields are all padded as well as either side of
 * both arrays. We are trading memory to avoid false sharing(active and passive).
 * <li>2 arrays instead of one: The algorithm requires an extra array of longs matching the size of the
 * elements array. This is doubling/tripling the memory allocated for the buffer.
 * <li>Power of 2 capacity: Actual elements buffer (and sequence buffer) is the closest power of 2 larger or
 * equal to the requested capacity.
 * </ol>
 */
public class MpmcArrayQueue<E> extends MpmcArrayQueueL3Pad<E>
{
    public static final int MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.mpmc.max.lookahead.step", 4096);
    private final int lookAheadStep;

    public MpmcArrayQueue(final int capacity)
    {
        super(RangeUtil.checkGreaterThanOrEqual(capacity, 2, "capacity"));
        lookAheadStep = Math.max(2, Math.min(capacity() / 4, MAX_LOOK_AHEAD_STEP));
    }

    @Override
    public boolean offer(final E e)
    {
        if (null == e)
        {
            throw new NullPointerException();
        }
        final long mask = this.mask;
        final long capacity = mask + 1;
        final long[] sBuffer = sequenceBuffer;

        long pIndex;
        long seqOffset;
        long seq;
        long cIndex = Long.MIN_VALUE;// start with bogus value, hope we don't need it
        do
        {
            pIndex = lvProducerIndex();
            seqOffset = calcCircularLongElementOffset(pIndex, mask);
            seq = lvLongElement(sBuffer, seqOffset);
            // consumer has not moved this seq forward, it's as last producer left
            if (seq < pIndex)
            {
                // Extra check required to ensure [Queue.offer == false iff queue is full]
                if (pIndex - capacity >= cIndex && // test against cached cIndex
                    pIndex - capacity >= (cIndex = lvConsumerIndex())) // test against latest cIndex
                {
                    return false;
                }
                else
                {
                    seq = pIndex + 1; // (+) hack to make it go around again without CAS
                }
            }
        }
        while (seq > pIndex || // another producer has moved the sequence(or +)
            !casProducerIndex(pIndex, pIndex + 1)); // failed to increment

        // casProducerIndex ensures correct construction
        spRefElement(buffer, calcCircularRefElementOffset(pIndex, mask), e);
        // seq++;
        soLongElement(sBuffer, seqOffset, pIndex + 1);
        return true;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Because return null indicates queue is empty we cannot simply rely on next element visibility for poll
     * and must test producer index when next element is not visible.
     */
    @Override
    public E poll()
    {
        // local load of field to avoid repeated loads after volatile reads
        final long[] sBuffer = sequenceBuffer;
        final long mask = this.mask;

        long cIndex;
        long seq;
        long seqOffset;
        long expectedSeq;
        long pIndex = -1; // start with bogus value, hope we don't need it
        do
        {
            cIndex = lvConsumerIndex();
            seqOffset = calcCircularLongElementOffset(cIndex, mask);
            seq = lvLongElement(sBuffer, seqOffset);
            expectedSeq = cIndex + 1;
            if (seq < expectedSeq)
            {
                // slot has not been moved by producer
                if (cIndex >= pIndex && // test against cached pIndex
                    cIndex == (pIndex = lvProducerIndex())) // update pIndex if we must
                {
                    // strict empty check, this ensures [Queue.poll() == null iff isEmpty()]
                    return null;
                }
                else
                {
                    seq = expectedSeq + 1; // trip another go around
                }
            }
        }
        while (seq > expectedSeq || // another consumer beat us to it
            !casConsumerIndex(cIndex, cIndex + 1)); // failed the CAS

        final long offset = calcCircularRefElementOffset(cIndex, mask);
        final E e = lpRefElement(buffer, offset);
        spRefElement(buffer, offset, null);
        // i.e. seq += capacity
        soLongElement(sBuffer, seqOffset, cIndex + mask + 1);
        return e;
    }

    @Override
    public E peek()
    {
        // local load of field to avoid repeated loads after volatile reads
        final long[] sBuffer = sequenceBuffer;
        final long mask = this.mask;

        long cIndex;
        long seq;
        long seqOffset;
        long expectedSeq;
        long pIndex = -1; // start with bogus value, hope we don't need it
        E e;
        while (true)
        {
            cIndex = lvConsumerIndex();
            seqOffset = calcCircularLongElementOffset(cIndex, mask);
            seq = lvLongElement(sBuffer, seqOffset);
            expectedSeq = cIndex + 1;
            if (seq < expectedSeq)
            {
                // slot has not been moved by producer
                if (cIndex >= pIndex && // test against cached pIndex
                    cIndex == (pIndex = lvProducerIndex())) // update pIndex if we must
                {
                    // strict empty check, this ensures [Queue.poll() == null iff isEmpty()]
                    return null;
                }
            }
            else if (seq == expectedSeq)
            {
                final long offset = calcCircularRefElementOffset(cIndex, mask);
                e = lvRefElement(buffer, offset);
                if (lvConsumerIndex() == cIndex)
                    return e;
            }
        }
    }

    @Override
    public boolean relaxedOffer(E e)
    {
        if (null == e)
        {
            throw new NullPointerException();
        }
        final long mask = this.mask;
        final long[] sBuffer = sequenceBuffer;

        long pIndex;
        long seqOffset;
        long seq;
        do
        {
            pIndex = lvProducerIndex();
            seqOffset = calcCircularLongElementOffset(pIndex, mask);
            seq = lvLongElement(sBuffer, seqOffset);
            if (seq < pIndex)
            { // slot not cleared by consumer yet
                return false;
            }
        }
        while (seq > pIndex || // another producer has moved the sequence
            !casProducerIndex(pIndex, pIndex + 1)); // failed to increment

        // casProducerIndex ensures correct construction
        spRefElement(buffer, calcCircularRefElementOffset(pIndex, mask), e);
        soLongElement(sBuffer, seqOffset, pIndex + 1);
        return true;
    }

    @Override
    public E relaxedPoll()
    {
        final long[] sBuffer = sequenceBuffer;
        final long mask = this.mask;

        long cIndex;
        long seqOffset;
        long seq;
        long expectedSeq;
        do
        {
            cIndex = lvConsumerIndex();
            seqOffset = calcCircularLongElementOffset(cIndex, mask);
            seq = lvLongElement(sBuffer, seqOffset);
            expectedSeq = cIndex + 1;
            if (seq < expectedSeq)
            {
                return null;
            }
        }
        while (seq > expectedSeq || // another consumer beat us to it
            !casConsumerIndex(cIndex, cIndex + 1)); // failed the CAS

        final long offset = calcCircularRefElementOffset(cIndex, mask);
        final E e = lpRefElement(buffer, offset);
        spRefElement(buffer, offset, null);
        soLongElement(sBuffer, seqOffset, cIndex + mask + 1);
        return e;
    }

    @Override
    public E relaxedPeek()
    {
        // local load of field to avoid repeated loads after volatile reads
        final long[] sBuffer = sequenceBuffer;
        final long mask = this.mask;

        long cIndex;
        long seq;
        long seqOffset;
        long expectedSeq;
        E e;
        do
        {
            cIndex = lvConsumerIndex();
            seqOffset = calcCircularLongElementOffset(cIndex, mask);
            seq = lvLongElement(sBuffer, seqOffset);
            expectedSeq = cIndex + 1;
            if (seq < expectedSeq)
            {
                return null;
            }
            else if (seq == expectedSeq)
            {
                final long offset = calcCircularRefElementOffset(cIndex, mask);
                e = lvRefElement(buffer, offset);
                if (lvConsumerIndex() == cIndex)
                    return e;
            }
        }
        while (true);
    }

    @Override
    public int drain(Consumer<E> c, int limit)
    {
        if (null == c)
            throw new IllegalArgumentException("c is null");
        if (limit < 0)
            throw new IllegalArgumentException("limit is negative: " + limit);
        if (limit == 0)
            return 0;

        final long[] sBuffer = sequenceBuffer;
        final long mask = this.mask;
        final E[] buffer = this.buffer;
        final int maxLookAheadStep = Math.min(this.lookAheadStep, limit);
        int consumed = 0;

        while (consumed < limit)
        {
            final int remaining = limit - consumed;
            final int lookAheadStep = Math.min(remaining, maxLookAheadStep);
            final long cIndex = lvConsumerIndex();
            final long lookAheadIndex = cIndex + lookAheadStep - 1;
            final long lookAheadSeqOffset = calcCircularLongElementOffset(lookAheadIndex, mask);
            final long lookAheadSeq = lvLongElement(sBuffer, lookAheadSeqOffset);
            final long expectedLookAheadSeq = lookAheadIndex + 1;
            if (lookAheadSeq == expectedLookAheadSeq && casConsumerIndex(cIndex, expectedLookAheadSeq))
            {
                for (int i = 0; i < lookAheadStep; i++)
                {
                    final long index = cIndex + i;
                    final long seqOffset = calcCircularLongElementOffset(index, mask);
                    final long offset = calcCircularRefElementOffset(index, mask);
                    final long expectedSeq = index + 1;
                    while (lvLongElement(sBuffer, seqOffset) != expectedSeq)
                    {

                    }
                    final E e = lpRefElement(buffer, offset);
                    spRefElement(buffer, offset, null);
                    soLongElement(sBuffer, seqOffset, index + mask + 1);
                    c.accept(e);
                }
                consumed += lookAheadStep;
            }
            else
            {
                if (lookAheadSeq < expectedLookAheadSeq)
                {
                    if (notAvailable(cIndex, mask, sBuffer, cIndex + 1))
                    {
                        return consumed;
                    }
                }
                return consumed + drainOneByOne(c, remaining);
            }
        }
        return limit;
    }

    private int drainOneByOne(Consumer<E> c, int limit)
    {
        final long[] sBuffer = sequenceBuffer;
        final long mask = this.mask;
        final E[] buffer = this.buffer;

        long cIndex;
        long seqOffset;
        long seq;
        long expectedSeq;
        for (int i = 0; i < limit; i++)
        {
            do
            {
                cIndex = lvConsumerIndex();
                seqOffset = calcCircularLongElementOffset(cIndex, mask);
                seq = lvLongElement(sBuffer, seqOffset);
                expectedSeq = cIndex + 1;
                if (seq < expectedSeq)
                {
                    return i;
                }
            }
            while (seq > expectedSeq || // another consumer beat us to it
                !casConsumerIndex(cIndex, cIndex + 1)); // failed the CAS

            final long offset = calcCircularRefElementOffset(cIndex, mask);
            final E e = lpRefElement(buffer, offset);
            spRefElement(buffer, offset, null);
            soLongElement(sBuffer, seqOffset, cIndex + mask + 1);
            c.accept(e);
        }
        return limit;
    }

    @Override
    public int fill(Supplier<E> s, int limit)
    {
        if (null == s)
            throw new IllegalArgumentException("supplier is null");
        if (limit < 0)
            throw new IllegalArgumentException("limit is negative:" + limit);
        if (limit == 0)
            return 0;

        final long[] sBuffer = sequenceBuffer;
        final long mask = this.mask;
        final E[] buffer = this.buffer;
        final int maxLookAheadStep = Math.min(this.lookAheadStep, limit);
        int produced = 0;

        while (produced < limit)
        {
            final int remaining = limit - produced;
            final int lookAheadStep = Math.min(remaining, maxLookAheadStep);
            final long pIndex = lvProducerIndex();
            final long lookAheadIndex = pIndex + lookAheadStep - 1;
            final long lookAheadSeqOffset = calcCircularLongElementOffset(lookAheadIndex, mask);
            final long lookAheadSeq = lvLongElement(sBuffer, lookAheadSeqOffset);
            final long expectedLookAheadSeq = lookAheadIndex;
            if (lookAheadSeq == expectedLookAheadSeq && casProducerIndex(pIndex, expectedLookAheadSeq + 1))
            {
                for (int i = 0; i < lookAheadStep; i++)
                {
                    final long index = pIndex + i;
                    final long seqOffset = calcCircularLongElementOffset(index, mask);
                    final long offset = calcCircularRefElementOffset(index, mask);
                    while (lvLongElement(sBuffer, seqOffset) != index)
                    {

                    }
                    // Ordered store ensures correct construction
                    soRefElement(buffer, offset, s.get());
                    soLongElement(sBuffer, seqOffset, index + 1);
                }
                produced += lookAheadStep;
            }
            else
            {
                if (lookAheadSeq < expectedLookAheadSeq)
                {
                    if (notAvailable(pIndex, mask, sBuffer, pIndex))
                    {
                        return produced;
                    }
                }
                return produced + fillOneByOne(s, remaining);
            }
        }
        return limit;
    }

    private boolean notAvailable(long index, long mask, long[] sBuffer, long expectedSeq)
    {
        final long seqOffset = calcCircularLongElementOffset(index, mask);
        final long seq = lvLongElement(sBuffer, seqOffset);
        if (seq < expectedSeq)
        {
            return true;
        }
        return false;
    }

    private int fillOneByOne(Supplier<E> s, int limit)
    {
        final long[] sBuffer = sequenceBuffer;
        final long mask = this.mask;
        final E[] buffer = this.buffer;

        long pIndex;
        long seqOffset;
        long seq;
        for (int i = 0; i < limit; i++)
        {
            do
            {
                pIndex = lvProducerIndex();
                seqOffset = calcCircularLongElementOffset(pIndex, mask);
                seq = lvLongElement(sBuffer, seqOffset);
                if (seq < pIndex)
                { // slot not cleared by consumer yet
                    return i;
                }
            }
            while (seq > pIndex || // another producer has moved the sequence
                !casProducerIndex(pIndex, pIndex + 1)); // failed to increment
            // Ordered store ensures correct construction
            soRefElement(buffer, calcCircularRefElementOffset(pIndex, mask), s.get());
            soLongElement(sBuffer, seqOffset, pIndex + 1);
        }
        return limit;
    }

    @Override
    public int drain(Consumer<E> c)
    {
        return MessagePassingQueueUtil.drain(this, c);
    }

    @Override
    public int fill(Supplier<E> s)
    {
        return MessagePassingQueueUtil.fillBounded(this, s);
    }

    @Override
    public void drain(Consumer<E> c, WaitStrategy w, ExitCondition exit)
    {
        MessagePassingQueueUtil.drain(this, c, w, exit);
    }

    @Override
    public void fill(Supplier<E> s, WaitStrategy wait, ExitCondition exit)
    {
        MessagePassingQueueUtil.fill(this, s, wait, exit);
    }
}
