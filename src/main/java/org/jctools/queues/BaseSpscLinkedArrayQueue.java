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

import org.jctools.queues.IndexedQueueSizeUtil.IndexedQueue;
import org.jctools.util.PortableJvmInfo;

import java.util.AbstractQueue;
import java.util.Iterator;

import static org.jctools.queues.LinkedArrayQueueUtil.length;
import static org.jctools.queues.LinkedArrayQueueUtil.nextArrayOffset;
import static org.jctools.util.UnsafeAccess.UNSAFE;
import static org.jctools.util.UnsafeAccess.fieldOffset;
import static org.jctools.util.UnsafeRefArrayAccess.*;

abstract class BaseSpscLinkedArrayQueuePrePad<E> extends AbstractQueue<E> implements IndexedQueue
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
    //byte b160,b161,b162,b163,b164,b165,b166,b167;//120b
    //byte b170,b171,b172,b173,b174,b175,b176,b177;//128b
    //  * drop 16b , the cold fields act as buffer *
}

abstract class BaseSpscLinkedArrayQueueConsumerColdFields<E> extends BaseSpscLinkedArrayQueuePrePad<E>
{
    protected long consumerMask;
    protected E[] consumerBuffer;
}

// $gen:ordered-fields
abstract class BaseSpscLinkedArrayQueueConsumerField<E> extends BaseSpscLinkedArrayQueueConsumerColdFields<E>
{
    private final static long C_INDEX_OFFSET = fieldOffset(BaseSpscLinkedArrayQueueConsumerField.class, "consumerIndex");

    private volatile long consumerIndex;

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

abstract class BaseSpscLinkedArrayQueueL2Pad<E> extends BaseSpscLinkedArrayQueueConsumerField<E>
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
abstract class BaseSpscLinkedArrayQueueProducerFields<E> extends BaseSpscLinkedArrayQueueL2Pad<E>
{
    private final static long P_INDEX_OFFSET = fieldOffset(BaseSpscLinkedArrayQueueProducerFields.class,"producerIndex");

    private volatile long producerIndex;

    @Override
    public final long lvProducerIndex()
    {
        return producerIndex;
    }

    final void soProducerIndex(long newValue)
    {
        UNSAFE.putOrderedLong(this, P_INDEX_OFFSET, newValue);
    }

    final long lpProducerIndex()
    {
        return UNSAFE.getLong(this, P_INDEX_OFFSET);
    }

}

abstract class BaseSpscLinkedArrayQueueProducerColdFields<E> extends BaseSpscLinkedArrayQueueProducerFields<E>
{
    protected long producerBufferLimit;
    protected long producerMask; // fixed for chunked and unbounded

    protected E[] producerBuffer;
}

abstract class BaseSpscLinkedArrayQueue<E> extends BaseSpscLinkedArrayQueueProducerColdFields<E>
    implements MessagePassingQueue<E>, QueueProgressIndicators
{

    private static final Object JUMP = new Object();

    @Override
    public final Iterator<E> iterator()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public final int size()
    {
        return IndexedQueueSizeUtil.size(this);
    }

    @Override
    public final boolean isEmpty()
    {
        return IndexedQueueSizeUtil.isEmpty(this);
    }

    @Override
    public String toString()
    {
        return this.getClass().getName();
    }

    @Override
    public long currentProducerIndex()
    {
        return lvProducerIndex();
    }

    @Override
    public long currentConsumerIndex()
    {
        return lvConsumerIndex();
    }

    protected final void soNext(E[] curr, E[] next)
    {
        long offset = nextArrayOffset(curr);
        soRefElement(curr, offset, next);
    }

    @SuppressWarnings("unchecked")
    protected final E[] lvNextArrayAndUnlink(E[] curr)
    {
        final long offset = nextArrayOffset(curr);
        final E[] nextBuffer = (E[]) lvRefElement(curr, offset);
        // prevent GC nepotism
        soRefElement(curr, offset, null);
        return nextBuffer;
    }

    @Override
    public boolean relaxedOffer(E e)
    {
        return offer(e);
    }

    @Override
    public E relaxedPoll()
    {
        return poll();
    }

    @Override
    public E relaxedPeek()
    {
        return peek();
    }

    @Override
    public int drain(Consumer<E> c)
    {
        return MessagePassingQueueUtil.drain(this, c);
    }

    @Override
    public int fill(Supplier<E> s)
    {
        long result = 0;// result is a long because we want to have a safepoint check at regular intervals
        final int capacity = capacity();
        do
        {
            final int filled = fill(s, PortableJvmInfo.RECOMENDED_OFFER_BATCH);
            if (filled == 0)
            {
                return (int) result;
            }
            result += filled;
        }
        while (result <= capacity);
        return (int) result;
    }

    @Override
    public int drain(Consumer<E> c, int limit)
    {
        return MessagePassingQueueUtil.drain(this, c, limit);
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

        for (int i = 0; i < limit; i++)
        {
            // local load of field to avoid repeated loads after volatile reads
            final E[] buffer = producerBuffer;
            final long index = lpProducerIndex();
            final long mask = producerMask;
            final long offset = calcCircularRefElementOffset(index, mask);
            // expected hot path
            if (index < producerBufferLimit)
            {
                writeToQueue(buffer, s.get(), index, offset);
            }
            else
            {
                if (!offerColdPath(buffer, mask, index, offset, null, s))
                {
                    return i;
                }
            }
        }
        return limit;
    }

    @Override
    public void drain(Consumer<E> c, WaitStrategy wait, ExitCondition exit)
    {
        MessagePassingQueueUtil.drain(this, c, wait, exit);
    }

    @Override
    public void fill(Supplier<E> s, WaitStrategy wait, ExitCondition exit)
    {
        MessagePassingQueueUtil.fill(this, s, wait, exit);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation is correct for single producer thread use only.
     */
    @Override
    public boolean offer(final E e)
    {
        // Objects.requireNonNull(e);
        if (null == e)
        {
            throw new NullPointerException();
        }
        // local load of field to avoid repeated loads after volatile reads
        final E[] buffer = producerBuffer;
        final long index = lpProducerIndex();
        final long mask = producerMask;
        final long offset = calcCircularRefElementOffset(index, mask);
        // expected hot path
        if (index < producerBufferLimit)
        {
            writeToQueue(buffer, e, index, offset);
            return true;
        }
        return offerColdPath(buffer, mask, index, offset, e, null);
    }

    abstract boolean offerColdPath(
        E[] buffer,
        long mask,
        long pIndex,
        long offset,
        E v,
        Supplier<? extends E> s);

    /**
     * {@inheritDoc}
     * <p>
     * This implementation is correct for single consumer thread use only.
     */
    @SuppressWarnings("unchecked")
    @Override
    public E poll()
    {
        // local load of field to avoid repeated loads after volatile reads
        final E[] buffer = consumerBuffer;
        final long index = lpConsumerIndex();
        final long mask = consumerMask;
        final long offset = calcCircularRefElementOffset(index, mask);
        final Object e = lvRefElement(buffer, offset);
        boolean isNextBuffer = e == JUMP;
        if (null != e && !isNextBuffer)
        {
            soConsumerIndex(index + 1);// this ensures correctness on 32bit platforms
            soRefElement(buffer, offset, null);
            return (E) e;
        }
        else if (isNextBuffer)
        {
            return newBufferPoll(buffer, index);
        }

        return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation is correct for single consumer thread use only.
     */
    @SuppressWarnings("unchecked")
    @Override
    public E peek()
    {
        final E[] buffer = consumerBuffer;
        final long index = lpConsumerIndex();
        final long mask = consumerMask;
        final long offset = calcCircularRefElementOffset(index, mask);
        final Object e = lvRefElement(buffer, offset);
        if (e == JUMP)
        {
            return newBufferPeek(buffer, index);
        }

        return (E) e;
    }

    final void linkOldToNew(
        final long currIndex,
        final E[] oldBuffer, final long offset,
        final E[] newBuffer, final long offsetInNew,
        final E e)
    {
        soRefElement(newBuffer, offsetInNew, e);
        // link to next buffer and add next indicator as element of old buffer
        soNext(oldBuffer, newBuffer);
        soRefElement(oldBuffer, offset, JUMP);
        // index is visible after elements (isEmpty/poll ordering)
        soProducerIndex(currIndex + 1);// this ensures atomic write of long on 32bit platforms
    }

    final void writeToQueue(final E[] buffer, final E e, final long index, final long offset)
    {
        soRefElement(buffer, offset, e);
        soProducerIndex(index + 1);// this ensures atomic write of long on 32bit platforms
    }

    private E newBufferPeek(final E[] buffer, final long index)
    {
        E[] nextBuffer = lvNextArrayAndUnlink(buffer);
        consumerBuffer = nextBuffer;
        final long mask = length(nextBuffer) - 2;
        consumerMask = mask;
        final long offset = calcCircularRefElementOffset(index, mask);
        return lvRefElement(nextBuffer, offset);
    }

    private E newBufferPoll(final E[] buffer, final long index)
    {
        E[] nextBuffer = lvNextArrayAndUnlink(buffer);
        consumerBuffer = nextBuffer;
        final long mask = length(nextBuffer) - 2;
        consumerMask = mask;
        final long offset = calcCircularRefElementOffset(index, mask);
        final E n = lvRefElement(nextBuffer, offset);
        if (null == n)
        {
            throw new IllegalStateException("new buffer must have at least one element");
        }
        else
        {
            soConsumerIndex(index + 1);// this ensures correctness on 32bit platforms
            soRefElement(nextBuffer, offset, null);
            return n;
        }
    }
}
