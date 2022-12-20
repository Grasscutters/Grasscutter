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

abstract class SpscArrayQueueColdField<E> extends ConcurrentCircularArrayQueue<E>
{
    public static final int MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096);
    final int lookAheadStep;

    SpscArrayQueueColdField(int capacity)
    {
        super(capacity);
        lookAheadStep = Math.min(capacity() / 4, MAX_LOOK_AHEAD_STEP);
    }
}

abstract class SpscArrayQueueL1Pad<E> extends SpscArrayQueueColdField<E>
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

    SpscArrayQueueL1Pad(int capacity)
    {
        super(capacity);
    }
}

// $gen:ordered-fields
abstract class SpscArrayQueueProducerIndexFields<E> extends SpscArrayQueueL1Pad<E>
{
    private final static long P_INDEX_OFFSET = fieldOffset(SpscArrayQueueProducerIndexFields.class, "producerIndex");

    private volatile long producerIndex;
    protected long producerLimit;

    SpscArrayQueueProducerIndexFields(int capacity)
    {
        super(capacity);
    }

    @Override
    public final long lvProducerIndex()
    {
        return producerIndex;
    }

    final long lpProducerIndex()
    {
        return UNSAFE.getLong(this, P_INDEX_OFFSET);
    }

    final void soProducerIndex(final long newValue)
    {
        UNSAFE.putOrderedLong(this, P_INDEX_OFFSET, newValue);
    }

}

abstract class SpscArrayQueueL2Pad<E> extends SpscArrayQueueProducerIndexFields<E>
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

    SpscArrayQueueL2Pad(int capacity)
    {
        super(capacity);
    }
}

//$gen:ordered-fields
abstract class SpscArrayQueueConsumerIndexField<E> extends SpscArrayQueueL2Pad<E>
{
    private final static long C_INDEX_OFFSET = fieldOffset(SpscArrayQueueConsumerIndexField.class, "consumerIndex");

    private volatile long consumerIndex;

    SpscArrayQueueConsumerIndexField(int capacity)
    {
        super(capacity);
    }

    public final long lvConsumerIndex()
    {
        return UNSAFE.getLongVolatile(this, C_INDEX_OFFSET);
    }

    final long lpConsumerIndex()
    {
        return UNSAFE.getLong(this, C_INDEX_OFFSET);
    }

    final void soConsumerIndex(final long newValue)
    {
        UNSAFE.putOrderedLong(this, C_INDEX_OFFSET, newValue);
    }
}

abstract class SpscArrayQueueL3Pad<E> extends SpscArrayQueueConsumerIndexField<E>
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

    SpscArrayQueueL3Pad(int capacity)
    {
        super(capacity);
    }
}


/**
 * A Single-Producer-Single-Consumer queue backed by a pre-allocated buffer.
 * <p>
 * This implementation is a mashup of the <a href="http://sourceforge.net/projects/mc-fastflow/">Fast Flow</a>
 * algorithm with an optimization of the offer method taken from the <a
 * href="http://staff.ustc.edu.cn/~bhua/publications/IJPP_draft.pdf">BQueue</a> algorithm (a variation on Fast
 * Flow), and adjusted to comply with Queue.offer semantics with regards to capacity.<br>
 * For convenience the relevant papers are available in the `resources` folder:<br>
 * <i>
 *     2010 - Pisa - SPSC Queues on Shared Cache Multi-Core Systems.pdf<br>
 *     2012 - Junchang- BQueue- Efﬁcient and Practical Queuing.pdf <br>
 * </i>
 * This implementation is wait free.
 */
public class SpscArrayQueue<E> extends SpscArrayQueueL3Pad<E>
{

    public SpscArrayQueue(final int capacity)
    {
        super(Math.max(capacity, 4));
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation is correct for single producer thread use only.
     */
    @Override
    public boolean offer(final E e)
    {
        if (null == e)
        {
            throw new NullPointerException();
        }
        // local load of field to avoid repeated loads after volatile reads
        final E[] buffer = this.buffer;
        final long mask = this.mask;
        final long producerIndex = this.lpProducerIndex();

        if (producerIndex >= producerLimit &&
            !offerSlowPath(buffer, mask, producerIndex))
        {
            return false;
        }
        final long offset = calcCircularRefElementOffset(producerIndex, mask);

        soRefElement(buffer, offset, e);
        soProducerIndex(producerIndex + 1); // ordered store -> atomic and ordered for size()
        return true;
    }

    private boolean offerSlowPath(final E[] buffer, final long mask, final long producerIndex)
    {
        final int lookAheadStep = this.lookAheadStep;
        if (null == lvRefElement(buffer,
            calcCircularRefElementOffset(producerIndex + lookAheadStep, mask)))
        {
            producerLimit = producerIndex + lookAheadStep;
        }
        else
        {
            final long offset = calcCircularRefElementOffset(producerIndex, mask);
            if (null != lvRefElement(buffer, offset))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation is correct for single consumer thread use only.
     */
    @Override
    public E poll()
    {
        final long consumerIndex = this.lpConsumerIndex();
        final long offset = calcCircularRefElementOffset(consumerIndex, mask);
        // local load of field to avoid repeated loads after volatile reads
        final E[] buffer = this.buffer;
        final E e = lvRefElement(buffer, offset);
        if (null == e)
        {
            return null;
        }
        soRefElement(buffer, offset, null);
        soConsumerIndex(consumerIndex + 1); // ordered store -> atomic and ordered for size()
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
        return lvRefElement(buffer, calcCircularRefElementOffset(lpConsumerIndex(), mask));
    }

    @Override
    public boolean relaxedOffer(final E message)
    {
        return offer(message);
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
    public int drain(final Consumer<E> c)
    {
        return drain(c, capacity());
    }

    @Override
    public int fill(final Supplier<E> s)
    {
        return fill(s, capacity());
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
        final long consumerIndex = this.lpConsumerIndex();

        for (int i = 0; i < limit; i++)
        {
            final long index = consumerIndex + i;
            final long offset = calcCircularRefElementOffset(index, mask);
            final E e = lvRefElement(buffer, offset);
            if (null == e)
            {
                return i;
            }
            soRefElement(buffer, offset, null);
            soConsumerIndex(index + 1); // ordered store -> atomic and ordered for size()
            c.accept(e);
        }
        return limit;
    }

    @Override
    public int fill(final Supplier<E> s, final int limit)
    {
        if (null == s)
            throw new IllegalArgumentException("supplier is null");
        if (limit < 0)
            throw new IllegalArgumentException("limit is negative:" + limit);
        if (limit == 0)
            return 0;

        final E[] buffer = this.buffer;
        final long mask = this.mask;
        final int lookAheadStep = this.lookAheadStep;
        final long producerIndex = this.lpProducerIndex();

        for (int i = 0; i < limit; i++)
        {
            final long index = producerIndex + i;
            final long lookAheadElementOffset =
                calcCircularRefElementOffset(index + lookAheadStep, mask);
            if (null == lvRefElement(buffer, lookAheadElementOffset))
            {
                int lookAheadLimit = Math.min(lookAheadStep, limit - i);
                for (int j = 0; j < lookAheadLimit; j++)
                {
                    final long offset = calcCircularRefElementOffset(index + j, mask);
                    soRefElement(buffer, offset, s.get());
                    soProducerIndex(index + j + 1); // ordered store -> atomic and ordered for size()
                }
                i += lookAheadLimit - 1;
            }
            else
            {
                final long offset = calcCircularRefElementOffset(index, mask);
                if (null != lvRefElement(buffer, offset))
                {
                    return i;
                }
                soRefElement(buffer, offset, s.get());
                soProducerIndex(index + 1); // ordered store -> atomic and ordered for size()
            }

        }
        return limit;
    }

    @Override
    public void drain(final Consumer<E> c, final WaitStrategy w, final ExitCondition exit)
    {
        if (null == c)
            throw new IllegalArgumentException("c is null");
        if (null == w)
            throw new IllegalArgumentException("wait is null");
        if (null == exit)
            throw new IllegalArgumentException("exit condition is null");

        final E[] buffer = this.buffer;
        final long mask = this.mask;
        long consumerIndex = this.lpConsumerIndex();

        int counter = 0;
        while (exit.keepRunning())
        {
            for (int i = 0; i < 4096; i++)
            {
                final long offset = calcCircularRefElementOffset(consumerIndex, mask);
                final E e = lvRefElement(buffer, offset);
                if (null == e)
                {
                    counter = w.idle(counter);
                    continue;
                }
                consumerIndex++;
                counter = 0;
                soRefElement(buffer, offset, null);
                soConsumerIndex(consumerIndex); // ordered store -> atomic and ordered for size()
                c.accept(e);
            }
        }
    }

    @Override
    public void fill(final Supplier<E> s, final WaitStrategy w, final ExitCondition e)
    {
        if (null == w)
            throw new IllegalArgumentException("waiter is null");
        if (null == e)
            throw new IllegalArgumentException("exit condition is null");
        if (null == s)
            throw new IllegalArgumentException("supplier is null");

        final E[] buffer = this.buffer;
        final long mask = this.mask;
        final int lookAheadStep = this.lookAheadStep;
        long producerIndex = this.lpProducerIndex();
        int counter = 0;
        while (e.keepRunning())
        {
            final long lookAheadElementOffset =
                calcCircularRefElementOffset(producerIndex + lookAheadStep, mask);
            if (null == lvRefElement(buffer, lookAheadElementOffset))
            {
                for (int j = 0; j < lookAheadStep; j++)
                {
                    final long offset = calcCircularRefElementOffset(producerIndex, mask);
                    producerIndex++;
                    soRefElement(buffer, offset, s.get());
                    soProducerIndex(producerIndex); // ordered store -> atomic and ordered for size()
                }
            }
            else
            {
                final long offset = calcCircularRefElementOffset(producerIndex, mask);
                if (null != lvRefElement(buffer, offset))
                {
                    counter = w.idle(counter);
                    continue;
                }
                producerIndex++;
                counter = 0;
                soRefElement(buffer, offset, s.get());
                soProducerIndex(producerIndex); // ordered store -> atomic and ordered for size()
            }
        }
    }
}
