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

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.jctools.queues.IndexedQueueSizeUtil.IndexedQueue;
import org.jctools.util.Pow2;
import org.jctools.util.RangeUtil;

import static org.jctools.queues.LinkedArrayQueueUtil.modifiedCalcCircularRefElementOffset;
import static org.jctools.util.UnsafeAccess.UNSAFE;
import static org.jctools.util.UnsafeAccess.fieldOffset;
import static org.jctools.util.UnsafeRefArrayAccess.*;

@SuppressWarnings("unused")
abstract class MpscBlockingConsumerArrayQueuePad1<E> extends AbstractQueue<E> implements IndexedQueue
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
}
// $gen:ordered-fields
abstract class MpscBlockingConsumerArrayQueueColdProducerFields<E> extends MpscBlockingConsumerArrayQueuePad1<E>
{
    private final static long P_LIMIT_OFFSET = fieldOffset(MpscBlockingConsumerArrayQueueColdProducerFields.class,"producerLimit");

    private volatile long producerLimit;
    protected final long producerMask;
    protected final E[] producerBuffer;

    MpscBlockingConsumerArrayQueueColdProducerFields(long producerMask, E[] producerBuffer)
    {
        this.producerMask = producerMask;
        this.producerBuffer = producerBuffer;
    }

    final long lvProducerLimit()
    {
        return producerLimit;
    }

    final boolean casProducerLimit(long expect, long newValue)
    {
        return UNSAFE.compareAndSwapLong(this, P_LIMIT_OFFSET, expect, newValue);
    }

    final void soProducerLimit(long newValue)
    {
        UNSAFE.putOrderedLong(this, P_LIMIT_OFFSET, newValue);
    }
}

@SuppressWarnings("unused")
abstract class MpscBlockingConsumerArrayQueuePad2<E> extends MpscBlockingConsumerArrayQueueColdProducerFields<E>
{
    byte b000,b001,b002,b003,b004,b005,b006,b007;//  8b
    byte b010,b011,b012,b013,b014,b015,b016,b017;// 16b
    byte b020,b021,b022,b023,b024,b025,b026,b027;// 24b
    byte b030,b031,b032,b033,b034,b035,b036,b037;// 32b
    byte b040,b041,b042,b043,b044,b045,b046,b047;// 40b
    byte b050,b051,b052,b053,b054,b055,b056,b057;// 48b
    byte b060,b061,b062,b063,b064,b065,b066,b067;// 56b
    // byte b070,b071,b072,b073,b074,b075,b076,b077;// 64b

    MpscBlockingConsumerArrayQueuePad2(long mask, E[] buffer)
    {
        super(mask, buffer);
    }
}

// $gen:ordered-fields
abstract class MpscBlockingConsumerArrayQueueProducerFields<E> extends MpscBlockingConsumerArrayQueuePad2<E>
{
    private final static long P_INDEX_OFFSET = fieldOffset(MpscBlockingConsumerArrayQueueProducerFields.class, "producerIndex");

    private volatile long producerIndex;

    MpscBlockingConsumerArrayQueueProducerFields(long mask, E[] buffer)
    {
        super(mask, buffer);
    }

    @Override
    public final long lvProducerIndex()
    {
        return producerIndex;
    }

    final void soProducerIndex(long newValue)
    {
        UNSAFE.putOrderedLong(this, P_INDEX_OFFSET, newValue);
    }

    final boolean casProducerIndex(long expect, long newValue)
    {
        return UNSAFE.compareAndSwapLong(this, P_INDEX_OFFSET, expect, newValue);
    }
}

@SuppressWarnings("unused")
abstract class MpscBlockingConsumerArrayQueuePad3<E> extends MpscBlockingConsumerArrayQueueProducerFields<E>
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

    MpscBlockingConsumerArrayQueuePad3(long mask, E[] buffer)
    {
        super(mask, buffer);
    }
}

// $gen:ordered-fields
abstract class MpscBlockingConsumerArrayQueueConsumerFields<E> extends MpscBlockingConsumerArrayQueuePad3<E>
{
    private final static long C_INDEX_OFFSET = fieldOffset(MpscBlockingConsumerArrayQueueConsumerFields.class,"consumerIndex");
    private final static long BLOCKED_OFFSET = fieldOffset(MpscBlockingConsumerArrayQueueConsumerFields.class,"blocked");

    private volatile long consumerIndex;
    protected final long consumerMask;
    private volatile Thread blocked;
    protected final E[] consumerBuffer;

    MpscBlockingConsumerArrayQueueConsumerFields(long mask, E[] buffer)
    {
        super(mask, buffer);
        consumerMask = mask;
        consumerBuffer = buffer;
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

    final Thread lvBlocked()
    {
        return blocked;
    }

    /**
     * This field should only be written to from the consumer thread. It is set before parking the consumer and nulled
     * when the consumer is unblocked. The value is read by producer thread to unpark the consumer.
     *
     * @param thread the consumer thread which is blocked waiting for the producers
     */
    final void soBlocked(Thread thread)
    {
        UNSAFE.putOrderedObject(this, BLOCKED_OFFSET, thread);
    }
}



/**
 * This is a partial implementation of the {@link java.util.concurrent.BlockingQueue} on the consumer side only on top
 * of the mechanics described in {@link BaseMpscLinkedArrayQueue}, but with the reservation bit used for blocking rather
 * than resizing in this instance.
 */
@SuppressWarnings("unused")
public class MpscBlockingConsumerArrayQueue<E> extends MpscBlockingConsumerArrayQueueConsumerFields<E>
    implements MessagePassingQueue<E>, QueueProgressIndicators, BlockingQueue<E>
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

    public MpscBlockingConsumerArrayQueue(final int capacity)
    {
        // leave lower bit of mask clear
        super((Pow2.roundToPowerOfTwo(capacity) - 1) << 1, (E[]) allocateRefArray(Pow2.roundToPowerOfTwo(capacity)));

        RangeUtil.checkGreaterThanOrEqual(capacity, 1, "capacity");
        soProducerLimit((Pow2.roundToPowerOfTwo(capacity) - 1) << 1); // we know it's all empty to start with
    }

    @Override
    public final Iterator<E> iterator()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public final int size()
    {
        // NOTE: because indices are on even numbers we cannot use the size util.

        /*
         * It is possible for a thread to be interrupted or reschedule between the read of the producer and
         * consumer indices, therefore protection is required to ensure size is within valid range. In the
         * event of concurrent polls/offers to this method the size is OVER estimated as we read consumer
         * index BEFORE the producer index.
         */
        long after = lvConsumerIndex();
        long size;
        while (true)
        {
            final long before = after;
            final long currentProducerIndex = lvProducerIndex();
            after = lvConsumerIndex();
            if (before == after)
            {
                size = ((currentProducerIndex - after) >> 1);
                break;
            }
        }
        // Long overflow is impossible, so size is always positive. Integer overflow is possible for the unbounded
        // indexed queues.
        if (size > Integer.MAX_VALUE)
        {
            return Integer.MAX_VALUE;
        }
        else
        {
            return (int) size;
        }
    }

    @Override
    public final boolean isEmpty()
    {
        // Order matters!
        // Loading consumer before producer allows for producer increments after consumer index is read.
        // This ensures this method is conservative in it's estimate. Note that as this is an MPMC there is
        // nothing we can do to make this an exact method.
        return ((this.lvConsumerIndex()/2) == (this.lvProducerIndex()/2));
    }

    @Override
    public String toString()
    {
        return this.getClass().getName();
    }

    /**
     * {@link #offer} if {@link #size()} is less than threshold.
     *
     * @param e         the object to offer onto the queue, not null
     * @param threshold the maximum allowable size
     * @return true if the offer is successful, false if queue size exceeds threshold
     * @since 3.0.1
     */
    public boolean offerIfBelowThreshold(final E e, int threshold)
    {
        if (null == e)
        {
            throw new NullPointerException();
        }

        final long mask = this.producerMask;
        final long capacity = mask + 2;
        threshold = threshold << 1;
        final E[] buffer = this.producerBuffer;
        long pIndex;
        while (true)
        {
            pIndex = lvProducerIndex();
            // lower bit is indicative of blocked consumer
            if ((pIndex & 1) == 1)
            {
                if (offerAndWakeup(buffer, mask, pIndex, e)) {
                    return true;
                }
                continue;
            }
            // pIndex is even (lower bit is 0) -> actual index is (pIndex >> 1), consumer is awake
            final long producerLimit = lvProducerLimit();

            // Use producer limit to save a read of the more rapidly mutated consumer index.
            // Assumption: queue is usually empty or near empty

            // available is also << 1
            final long available = producerLimit - pIndex;
            // sizeEstimate <= size
            final long sizeEstimate = capacity - available;

            if (sizeEstimate >= threshold ||
                // producerLimit check allows for threshold >= capacity
                producerLimit <= pIndex)
            {
                if (!recalculateProducerLimit(pIndex, producerLimit, lvConsumerIndex(), capacity, threshold))
                {
                    return false;
                }
            }

            // Claim the index
            if (casProducerIndex(pIndex, pIndex + 2))
            {
                break;
            }
        }
        final long offset = modifiedCalcCircularRefElementOffset(pIndex, mask);
        // INDEX visible before ELEMENT
        soRefElement(buffer, offset, e); // release element e
        return true;
    }

    @Override
    public boolean offer(final E e)
    {
        if (null == e)
        {
            throw new NullPointerException();
        }

        final long mask = this.producerMask;
        final E[] buffer = this.producerBuffer;
        long pIndex;
        while (true)
        {
            pIndex = lvProducerIndex();
            // lower bit is indicative of blocked consumer
            if ((pIndex & 1) == 1)
            {
                if (offerAndWakeup(buffer, mask, pIndex, e))
                    return true;
                continue;
            }
            // pIndex is even (lower bit is 0) -> actual index is (pIndex >> 1), consumer is awake
            final long producerLimit = lvProducerLimit();

            // Use producer limit to save a read of the more rapidly mutated consumer index.
            // Assumption: queue is usually empty or near empty
            if (producerLimit <= pIndex)
            {
                if (!recalculateProducerLimit(mask, pIndex, producerLimit))
                {
                    return false;
                }
            }

            // Claim the index
            if (casProducerIndex(pIndex, pIndex + 2))
            {
                break;
            }
        }
        final long offset = modifiedCalcCircularRefElementOffset(pIndex, mask);
        // INDEX visible before ELEMENT
        soRefElement(buffer, offset, e); // release element e
        return true;
    }

    @Override
    public void put(E e) throws InterruptedException
    {
        if (!offer(e))
            throw new UnsupportedOperationException();
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException
    {
        if (offer(e))
            return true;
        throw new UnsupportedOperationException();
    }

    private boolean offerAndWakeup(E[] buffer, long mask, long pIndex, E e)
    {
        final long offset = modifiedCalcCircularRefElementOffset(pIndex, mask);
        final Thread consumerThread = lvBlocked();

        // We could see a null here through a race with the consumer not yet storing the reference. Just retry.
        if (consumerThread == null)
        {
            return false;
        }

        // Claim the slot and the responsibility of unparking
        if(!casProducerIndex(pIndex, pIndex + 1))
        {
            return false;
        }

        soRefElement(buffer, offset, e);
        LockSupport.unpark(consumerThread);
        return true;
    }

    private boolean recalculateProducerLimit(long mask, long pIndex, long producerLimit)
    {
        return recalculateProducerLimit(pIndex, producerLimit, lvConsumerIndex(), mask + 2, mask + 2);
    }

    private boolean recalculateProducerLimit(long pIndex, long producerLimit, long cIndex, long bufferCapacity, long threshold)
    {
        // try to update the limit with our new found knowledge on cIndex
        if (cIndex + bufferCapacity > pIndex)
        {
            casProducerLimit(producerLimit, cIndex + bufferCapacity);
        }
        // full and cannot grow, or hit threshold
        long size = pIndex - cIndex;
        return size < threshold && size < bufferCapacity;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation is correct for single consumer thread use only.
     */
    @Override
    public E take() throws InterruptedException
    {
        final E[] buffer = consumerBuffer;
        final long mask = consumerMask;

        final long cIndex = lpConsumerIndex();
        final long offset = modifiedCalcCircularRefElementOffset(cIndex, mask);
        E e = lvRefElement(buffer, offset);
        if (e == null)
        {
            return parkUntilNext(buffer, cIndex, offset, Long.MAX_VALUE);
        }

        soRefElement(buffer, offset, null); // release element null
        soConsumerIndex(cIndex + 2); // release cIndex

        return e;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation is correct for single consumer thread use only.
     */
    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException
    {
        final E[] buffer = consumerBuffer;
        final long mask = consumerMask;

        final long cIndex = lpConsumerIndex();
        final long offset = modifiedCalcCircularRefElementOffset(cIndex, mask);
        E e = lvRefElement(buffer, offset);
        if (e == null)
        {
            long timeoutNs = unit.toNanos(timeout);
            if (timeoutNs <= 0)
            {
                return null;
            }
            return parkUntilNext(buffer, cIndex, offset, timeoutNs);
        }

        soRefElement(buffer, offset, null); // release element null
        soConsumerIndex(cIndex + 2); // release cIndex

        return e;
    }

    private E parkUntilNext(E[] buffer, long cIndex, long offset, long timeoutNs) throws InterruptedException {
        E e;
        final long pIndex = lvProducerIndex();
        if (cIndex == pIndex && // queue is empty
            casProducerIndex(pIndex, pIndex + 1)) // we announce ourselves as parked by setting parity
        {
            // producers only try a wakeup when both the index and the blocked thread are visible, otherwise they spin
            soBlocked(Thread.currentThread());
            // ignore deadline when it's forever
            final long deadlineNs = timeoutNs == Long.MAX_VALUE ? 0 : System.nanoTime() + timeoutNs;

            try
            {
                while (true)
                {
                    LockSupport.parkNanos(this, timeoutNs);
                    if (Thread.interrupted())
                    {
                        casProducerIndex(pIndex + 1, pIndex);
                        throw new InterruptedException();
                    }
                    if ((lvProducerIndex() & 1) == 0) {
                        break;
                    }
                    // ignore deadline when it's forever
                    timeoutNs = timeoutNs == Long.MAX_VALUE ? Long.MAX_VALUE : deadlineNs - System.nanoTime();
                    if (timeoutNs <= 0)
                    {
                        if (casProducerIndex(pIndex + 1, pIndex))
                        {
                            // ran out of time and the producer has not moved the index
                            return null;
                        }

                        break; // just in the nick of time
                    }
                }
            }
            finally
            {
                soBlocked(null);
            }
        }
        // producer index is visible before element, so if we wake up between the index moving and the element
        // store we could see a null.
        e = spinWaitForElement(buffer, offset);

        soRefElement(buffer, offset, null); // release element null
        soConsumerIndex(cIndex + 2); // release cIndex

        return e;
    }

    @Override
    public int remainingCapacity()
    {
        return capacity() - size();
    }

    @Override
    public int drainTo(Collection<? super E> c)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public int drainTo(Collection<? super E> c, int maxElements)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation is correct for single consumer thread use only.
     */
    @Override
    public E poll()
    {
        final E[] buffer = consumerBuffer;
        final long mask = consumerMask;

        final long index = lpConsumerIndex();
        final long offset = modifiedCalcCircularRefElementOffset(index, mask);
        E e = lvRefElement(buffer, offset);
        if (e == null)
        {
            // consumer can't see the odd producer index
            if (index != lvProducerIndex())
            {
                // poll() == null iff queue is empty, null element is not strong enough indicator, so we must
                // check the producer index. If the queue is indeed not empty we spin until element is
                // visible.
                e = spinWaitForElement(buffer, offset);
            }
            else
            {
                return null;
            }
        }

        soRefElement(buffer, offset, null); // release element null
        soConsumerIndex(index + 2); // release cIndex
        return e;
    }

    private static <E> E spinWaitForElement(E[] buffer, long offset)
    {
        E e;
        do
        {
            e = lvRefElement(buffer, offset);
        }
        while (e == null);
        return e;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation is correct for single consumer thread use only.
     */
    @Override
    public E peek()
    {
        final E[] buffer = consumerBuffer;
        final long mask = consumerMask;

        final long index = lpConsumerIndex();
        final long offset = modifiedCalcCircularRefElementOffset(index, mask);
        E e = lvRefElement(buffer, offset);
        if (e == null && index != lvProducerIndex())
        {
            // peek() == null iff queue is empty, null element is not strong enough indicator, so we must
            // check the producer index. If the queue is indeed not empty we spin until element is visible.
            e = spinWaitForElement(buffer, offset);
        }

        return e;
    }

    @Override
    public long currentProducerIndex()
    {
        return lvProducerIndex() / 2;
    }

    @Override
    public long currentConsumerIndex()
    {
        return lvConsumerIndex() / 2;
    }

    @Override
    public int capacity()
    {
        return (int) ((consumerMask + 2) >> 1);
    }

    @Override
    public boolean relaxedOffer(E e)
    {
        return offer(e);
    }

    @Override
    public E relaxedPoll()
    {
        final E[] buffer = consumerBuffer;
        final long index = lpConsumerIndex();
        final long mask = consumerMask;

        final long offset = modifiedCalcCircularRefElementOffset(index, mask);
        E e = lvRefElement(buffer, offset);
        if (e == null)
        {
            return null;
        }
        soRefElement(buffer, offset, null);
        soConsumerIndex(index + 2);
        return e;
    }

    @Override
    public E relaxedPeek()
    {
        final E[] buffer = consumerBuffer;
        final long index = lpConsumerIndex();
        final long mask = consumerMask;

        final long offset = modifiedCalcCircularRefElementOffset(index, mask);
        return lvRefElement(buffer, offset);
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

        final long mask = this.producerMask;

        long pIndex;
        int claimedSlots;
        Thread blockedConsumer = null;
        long batchLimit = 0;
        final long shiftedBatchSize = 2L * limit;

        while (true)
        {
            pIndex = lvProducerIndex();
            long producerLimit = lvProducerLimit();

            // lower bit is indicative of blocked consumer
            if ((pIndex & 1) == 1)
            {
                // observe the blocked thread for the pIndex
                blockedConsumer = lvBlocked();
                if (blockedConsumer == null)
                    continue;// racing, retry
                if(!casProducerIndex(pIndex, pIndex + 1))
                {
                    blockedConsumer = null;
                    continue;
                }
                // We have observed the blocked thread for the pIndex(lv index, lv thread, cas index).
                // We've claimed pIndex, now we need to wake up consumer and set the element
                batchLimit = pIndex + 1;
                pIndex = pIndex - 1;
                break;
            }
            // pIndex is even (lower bit is 0) -> actual index is (pIndex >> 1), consumer is awake

            // we want 'limit' slots, but will settle for whatever is visible to 'producerLimit'
            batchLimit = Math.min(producerLimit, pIndex + shiftedBatchSize); //  -> producerLimit >= batchLimit

            // Use producer limit to save a read of the more rapidly mutated consumer index.
            // Assumption: queue is usually empty or near empty
            if (pIndex >= producerLimit)
            {
                if (!recalculateProducerLimit(mask, pIndex, producerLimit))
                {
                    return 0;
                }
                batchLimit = Math.min(lvProducerLimit(), pIndex + shiftedBatchSize);
            }

            // Claim the index
            if (casProducerIndex(pIndex, batchLimit))
            {
                break;
            }
        }
        claimedSlots = (int) ((batchLimit - pIndex) / 2);

        final E[] buffer = this.producerBuffer;
        // first element offset might be a wakeup, so peeled from loop
        for (int i = 0; i < claimedSlots; i++)
        {
            long offset = modifiedCalcCircularRefElementOffset(pIndex + 2L * i, mask);
            soRefElement(buffer, offset, s.get());
        }

        if (blockedConsumer != null)
        {
            // no point unblocking an unrelated blocked thread, things have obviously moved on
            if (lvBlocked() == blockedConsumer) {
                LockSupport.unpark(blockedConsumer);
            }
        }

        return claimedSlots;
    }

    /**
     * Remove up to <i>limit</i> elements from the queue and hand to consume, waiting up to the specified wait time if
     * necessary for an element to become available.
     * <p>
     * There's no strong commitment to the queue being empty at the end of it.
     * This implementation is correct for single consumer thread use only.
     * <p>
     * <b>WARNING</b>: Explicit assumptions are made with regards to {@link Consumer#accept} make sure you have read
     * and understood these before using this method.
     *
     * @return the number of polled elements
     * @throws InterruptedException if interrupted while waiting
     * @throws IllegalArgumentException c is {@code null}
     * @throws IllegalArgumentException if limit is negative
     */
    public int drain(Consumer<E> c, final int limit, long timeout, TimeUnit unit) throws InterruptedException {
        if (limit == 0) {
            return 0;
        }
        final int drained = drain(c, limit);
        if (drained != 0) {
            return drained;
        }
        final E e = poll(timeout, unit);
        if (e == null)
            return 0;
        c.accept(e);
        return 1 + drain(c, limit - 1);
    }

    @Override
    public int fill(Supplier<E> s)
    {
        return MessagePassingQueueUtil.fillBounded(this, s);
    }

    @Override
    public void fill(Supplier<E> s, WaitStrategy wait, ExitCondition exit)
    {
        MessagePassingQueueUtil.fill(this, s, wait, exit);
    }

    @Override
    public int drain(Consumer<E> c)
    {
        return drain(c, capacity());
    }

    @Override
    public int drain(final Consumer<E> c, final int limit)
    {
        return MessagePassingQueueUtil.drain(this, c, limit);
    }

    @Override
    public void drain(Consumer<E> c, WaitStrategy w, ExitCondition exit)
    {
        MessagePassingQueueUtil.drain(this, c, w, exit);
    }
}
