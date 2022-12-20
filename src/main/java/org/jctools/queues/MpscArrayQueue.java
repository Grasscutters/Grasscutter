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

import static org.jctools.util.UnsafeAccess.UNSAFE;
import static org.jctools.util.UnsafeAccess.fieldOffset;
import static org.jctools.util.UnsafeRefArrayAccess.*;

abstract class MpscArrayQueueL1Pad<E> extends ConcurrentCircularArrayQueue<E>
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

    MpscArrayQueueL1Pad(int capacity)
    {
        super(capacity);
    }
}

//$gen:ordered-fields
abstract class MpscArrayQueueProducerIndexField<E> extends MpscArrayQueueL1Pad<E>
{
    private final static long P_INDEX_OFFSET = fieldOffset(MpscArrayQueueProducerIndexField.class, "producerIndex");

    private volatile long producerIndex;

    MpscArrayQueueProducerIndexField(int capacity)
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

abstract class MpscArrayQueueMidPad<E> extends MpscArrayQueueProducerIndexField<E>
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

    MpscArrayQueueMidPad(int capacity)
    {
        super(capacity);
    }
}

//$gen:ordered-fields
abstract class MpscArrayQueueProducerLimitField<E> extends MpscArrayQueueMidPad<E>
{
    private final static long P_LIMIT_OFFSET = fieldOffset(MpscArrayQueueProducerLimitField.class, "producerLimit");

    // First unavailable index the producer may claim up to before rereading the consumer index
    private volatile long producerLimit;

    MpscArrayQueueProducerLimitField(int capacity)
    {
        super(capacity);
        this.producerLimit = capacity;
    }

    final long lvProducerLimit()
    {
        return producerLimit;
    }

    final void soProducerLimit(long newValue)
    {
        UNSAFE.putOrderedLong(this, P_LIMIT_OFFSET, newValue);
    }
}

abstract class MpscArrayQueueL2Pad<E> extends MpscArrayQueueProducerLimitField<E>
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

    MpscArrayQueueL2Pad(int capacity)
    {
        super(capacity);
    }
}

//$gen:ordered-fields
abstract class MpscArrayQueueConsumerIndexField<E> extends MpscArrayQueueL2Pad<E>
{
    private final static long C_INDEX_OFFSET = fieldOffset(MpscArrayQueueConsumerIndexField.class, "consumerIndex");

    private volatile long consumerIndex;

    MpscArrayQueueConsumerIndexField(int capacity)
    {
        super(capacity);
    }

    @Override
    public final long lvConsumerIndex()
    {
        return consumerIndex;
    }

    final long lpConsumerIndex()
    {
        return UNSAFE.getLong(this, C_INDEX_OFFSET);
    }

    final void soConsumerIndex(long newValue)
    {
        UNSAFE.putOrderedLong(this, C_INDEX_OFFSET, newValue);
    }
}

abstract class MpscArrayQueueL3Pad<E> extends MpscArrayQueueConsumerIndexField<E>
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

    MpscArrayQueueL3Pad(int capacity)
    {
        super(capacity);
    }
}

/**
 * A Multi-Producer-Single-Consumer queue based on a {@link org.jctools.queues.ConcurrentCircularArrayQueue}. This
 * implies that any thread may call the offer method, but only a single thread may call poll/peek for correctness to
 * maintained. <br>
 * This implementation follows patterns documented on the package level for False Sharing protection.<br>
 * This implementation is using the <a href="http://sourceforge.net/projects/mc-fastflow/">Fast Flow</a>
 * method for polling from the queue (with minor change to correctly publish the index) and an extension of
 * the Leslie Lamport concurrent queue algorithm (originated by Martin Thompson) on the producer side.
 */
public class MpscArrayQueue<E> extends MpscArrayQueueL3Pad<E>
{

    public MpscArrayQueue(final int capacity)
    {
        super(capacity);
    }

    /**
     * {@link #offer}} if {@link #size()} is less than threshold.
     *
     * @param e         the object to offer onto the queue, not null
     * @param threshold the maximum allowable size
     * @return true if the offer is successful, false if queue size exceeds threshold
     * @since 1.0.1
     */
    public boolean offerIfBelowThreshold(final E e, int threshold)
    {
        if (null == e)
        {
            throw new NullPointerException();
        }

        final long mask = this.mask;
        final long capacity = mask + 1;

        long producerLimit = lvProducerLimit();
        long pIndex;
        do
        {
            pIndex = lvProducerIndex();
            long available = producerLimit - pIndex;
            long size = capacity - available;
            if (size >= threshold)
            {
                final long cIndex = lvConsumerIndex();
                size = pIndex - cIndex;
                if (size >= threshold)
                {
                    return false; // the size exceeds threshold
                }
                else
                {
                    // update producer limit to the next index that we must recheck the consumer index
                    producerLimit = cIndex + capacity;

                    // this is racy, but the race is benign
                    soProducerLimit(producerLimit);
                }
            }
        }
        while (!casProducerIndex(pIndex, pIndex + 1));
        /*
         * NOTE: the new producer index value is made visible BEFORE the element in the array. If we relied on
         * the index visibility to poll() we would need to handle the case where the element is not visible.
         */

        // Won CAS, move on to storing
        final long offset = calcCircularRefElementOffset(pIndex, mask);
        soRefElement(buffer, offset, e);
        return true; // AWESOME :)
    }

    /**
     * {@inheritDoc} <br>
     * <p>
     * IMPLEMENTATION NOTES:<br>
     * Lock free offer using a single CAS. As class name suggests access is permitted to many threads
     * concurrently.
     *
     * @see java.util.Queue#offer
     * @see org.jctools.queues.MessagePassingQueue#offer
     */
    @Override
    public boolean offer(final E e)
    {
        if (null == e)
        {
            throw new NullPointerException();
        }

        // use a cached view on consumer index (potentially updated in loop)
        final long mask = this.mask;
        long producerLimit = lvProducerLimit();
        long pIndex;
        do
        {
            pIndex = lvProducerIndex();
            if (pIndex >= producerLimit)
            {
                final long cIndex = lvConsumerIndex();
                producerLimit = cIndex + mask + 1;

                if (pIndex >= producerLimit)
                {
                    return false; // FULL :(
                }
                else
                {
                    // update producer limit to the next index that we must recheck the consumer index
                    // this is racy, but the race is benign
                    soProducerLimit(producerLimit);
                }
            }
        }
        while (!casProducerIndex(pIndex, pIndex + 1));
        /*
         * NOTE: the new producer index value is made visible BEFORE the element in the array. If we relied on
         * the index visibility to poll() we would need to handle the case where the element is not visible.
         */

        // Won CAS, move on to storing
        final long offset = calcCircularRefElementOffset(pIndex, mask);
        soRefElement(buffer, offset, e);
        return true; // AWESOME :)
    }

    /**
     * A wait free alternative to offer which fails on CAS failure.
     *
     * @param e new element, not null
     * @return 1 if next element cannot be filled, -1 if CAS failed, 0 if successful
     */
    public final int failFastOffer(final E e)
    {
        if (null == e)
        {
            throw new NullPointerException();
        }
        final long mask = this.mask;
        final long capacity = mask + 1;
        final long pIndex = lvProducerIndex();
        long producerLimit = lvProducerLimit();
        if (pIndex >= producerLimit)
        {
            final long cIndex = lvConsumerIndex();
            producerLimit = cIndex + capacity;
            if (pIndex >= producerLimit)
            {
                return 1; // FULL :(
            }
            else
            {
                // update producer limit to the next index that we must recheck the consumer index
                soProducerLimit(producerLimit);
            }
        }

        // look Ma, no loop!
        if (!casProducerIndex(pIndex, pIndex + 1))
        {
            return -1; // CAS FAIL :(
        }

        // Won CAS, move on to storing
        final long offset = calcCircularRefElementOffset(pIndex, mask);
        soRefElement(buffer, offset, e);
        return 0; // AWESOME :)
    }

    /**
     * {@inheritDoc}
     * <p>
     * IMPLEMENTATION NOTES:<br>
     * Lock free poll using ordered loads/stores. As class name suggests access is limited to a single thread.
     *
     * @see java.util.Queue#poll
     * @see org.jctools.queues.MessagePassingQueue#poll
     */
    @Override
    public E poll()
    {
        final long cIndex = lpConsumerIndex();
        final long offset = calcCircularRefElementOffset(cIndex, mask);
        // Copy field to avoid re-reading after volatile load
        final E[] buffer = this.buffer;

        // If we can't see the next available element we can't poll
        E e = lvRefElement(buffer, offset);
        if (null == e)
        {
            /*
             * NOTE: Queue may not actually be empty in the case of a producer (P1) being interrupted after
             * winning the CAS on offer but before storing the element in the queue. Other producers may go on
             * to fill up the queue after this element.
             */
            if (cIndex != lvProducerIndex())
            {
                do
                {
                    e = lvRefElement(buffer, offset);
                }
                while (e == null);
            }
            else
            {
                return null;
            }
        }

        spRefElement(buffer, offset, null);
        soConsumerIndex(cIndex + 1);
        return e;
    }

    /**
     * {@inheritDoc}
     * <p>
     * IMPLEMENTATION NOTES:<br>
     * Lock free peek using ordered loads. As class name suggests access is limited to a single thread.
     *
     * @see java.util.Queue#poll
     * @see org.jctools.queues.MessagePassingQueue#poll
     */
    @Override
    public E peek()
    {
        // Copy field to avoid re-reading after volatile load
        final E[] buffer = this.buffer;

        final long cIndex = lpConsumerIndex();
        final long offset = calcCircularRefElementOffset(cIndex, mask);
        E e = lvRefElement(buffer, offset);
        if (null == e)
        {
            /*
             * NOTE: Queue may not actually be empty in the case of a producer (P1) being interrupted after
             * winning the CAS on offer but before storing the element in the queue. Other producers may go on
             * to fill up the queue after this element.
             */
            if (cIndex != lvProducerIndex())
            {
                do
                {
                    e = lvRefElement(buffer, offset);
                }
                while (e == null);
            }
            else
            {
                return null;
            }
        }
        return e;
    }

    @Override
    public boolean relaxedOffer(E e)
    {
        return offer(e);
    }

    @Override
    public E relaxedPoll()
    {
        final E[] buffer = this.buffer;
        final long cIndex = lpConsumerIndex();
        final long offset = calcCircularRefElementOffset(cIndex, mask);

        // If we can't see the next available element we can't poll
        E e = lvRefElement(buffer, offset);
        if (null == e)
        {
            return null;
        }

        spRefElement(buffer, offset, null);
        soConsumerIndex(cIndex + 1);
        return e;
    }

    @Override
    public E relaxedPeek()
    {
        final E[] buffer = this.buffer;
        final long mask = this.mask;
        final long cIndex = lpConsumerIndex();
        return lvRefElement(buffer, calcCircularRefElementOffset(cIndex, mask));
    }

    @Override
    public int drain(final Consumer<E> c, final int limit)
    {
        if (null == c)
            throw new IllegalArgumentException("c is null");
        if (limit < 0)
            throw new IllegalArgumentException("limit is negative: " + limit);
        if (limit == 0)
            return 0;

        final E[] buffer = this.buffer;
        final long mask = this.mask;
        final long cIndex = lpConsumerIndex();

        for (int i = 0; i < limit; i++)
        {
            final long index = cIndex + i;
            final long offset = calcCircularRefElementOffset(index, mask);
            final E e = lvRefElement(buffer, offset);
            if (null == e)
            {
                return i;
            }
            spRefElement(buffer, offset, null);
            soConsumerIndex(index + 1); // ordered store -> atomic and ordered for size()
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

        final long mask = this.mask;
        final long capacity = mask + 1;
        long producerLimit = lvProducerLimit();
        long pIndex;
        int actualLimit;
        do
        {
            pIndex = lvProducerIndex();
            long available = producerLimit - pIndex;
            if (available <= 0)
            {
                final long cIndex = lvConsumerIndex();
                producerLimit = cIndex + capacity;
                available = producerLimit - pIndex;
                if (available <= 0)
                {
                    return 0; // FULL :(
                }
                else
                {
                    // update producer limit to the next index that we must recheck the consumer index
                    soProducerLimit(producerLimit);
                }
            }
            actualLimit = Math.min((int) available, limit);
        }
        while (!casProducerIndex(pIndex, pIndex + actualLimit));
        // right, now we claimed a few slots and can fill them with goodness
        final E[] buffer = this.buffer;
        for (int i = 0; i < actualLimit; i++)
        {
            // Won CAS, move on to storing
            final long offset = calcCircularRefElementOffset(pIndex + i, mask);
            soRefElement(buffer, offset, s.get());
        }
        return actualLimit;
    }

    @Override
    public int drain(Consumer<E> c)
    {
        return drain(c, capacity());
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
