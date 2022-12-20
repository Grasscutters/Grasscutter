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

import java.util.AbstractQueue;
import java.util.Iterator;

import static org.jctools.util.PortableJvmInfo.CPUs;
import static org.jctools.util.Pow2.isPowerOfTwo;
import static org.jctools.util.Pow2.roundToPowerOfTwo;

/**
 * Use a set number of parallel MPSC queues to diffuse the contention on tail.
 */
abstract class MpscCompoundQueueL0Pad<E> extends AbstractQueue<E> implements MessagePassingQueue<E>
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

abstract class MpscCompoundQueueColdFields<E> extends MpscCompoundQueueL0Pad<E>
{
    // must be power of 2
    protected final int parallelQueues;
    protected final int parallelQueuesMask;
    protected final MpscArrayQueue<E>[] queues;

    @SuppressWarnings("unchecked")
    MpscCompoundQueueColdFields(int capacity, int queueParallelism)
    {
        parallelQueues = isPowerOfTwo(queueParallelism) ? queueParallelism
            : roundToPowerOfTwo(queueParallelism) / 2;
        parallelQueuesMask = parallelQueues - 1;
        queues = new MpscArrayQueue[parallelQueues];
        int fullCapacity = roundToPowerOfTwo(capacity);
        RangeUtil.checkGreaterThanOrEqual(fullCapacity, parallelQueues, "fullCapacity");
        for (int i = 0; i < parallelQueues; i++)
        {
            queues[i] = new MpscArrayQueue<E>(fullCapacity / parallelQueues);
        }
    }
}

abstract class MpscCompoundQueueMidPad<E> extends MpscCompoundQueueColdFields<E>
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

    public MpscCompoundQueueMidPad(int capacity, int queueParallelism)
    {
        super(capacity, queueParallelism);
    }
}

abstract class MpscCompoundQueueConsumerQueueIndex<E> extends MpscCompoundQueueMidPad<E>
{
    int consumerQueueIndex;

    MpscCompoundQueueConsumerQueueIndex(int capacity, int queueParallelism)
    {
        super(capacity, queueParallelism);
    }
}

public class MpscCompoundQueue<E> extends MpscCompoundQueueConsumerQueueIndex<E>
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

    public MpscCompoundQueue(int capacity)
    {
        this(capacity, CPUs);
    }

    public MpscCompoundQueue(int capacity, int queueParallelism)
    {
        super(capacity, queueParallelism);
    }

    @Override
    public boolean offer(final E e)
    {
        if (null == e)
        {
            throw new NullPointerException();
        }
        final int parallelQueuesMask = this.parallelQueuesMask;
        int start = (int) (Thread.currentThread().getId() & parallelQueuesMask);
        final MpscArrayQueue<E>[] queues = this.queues;
        if (queues[start].offer(e))
        {
            return true;
        }
        else
        {
            return slowOffer(queues, parallelQueuesMask, start + 1, e);
        }
    }

    private boolean slowOffer(MpscArrayQueue<E>[] queues, int parallelQueuesMask, int start, E e)
    {
        final int queueCount = parallelQueuesMask + 1;
        final int end = start + queueCount;
        while (true)
        {
            int status = 0;
            for (int i = start; i < end; i++)
            {
                int s = queues[i & parallelQueuesMask].failFastOffer(e);
                if (s == 0)
                {
                    return true;
                }
                status += s;
            }
            if (status == queueCount)
            {
                return false;
            }
        }
    }

    @Override
    public E poll()
    {
        int qIndex = consumerQueueIndex & parallelQueuesMask;
        int limit = qIndex + parallelQueues;
        E e = null;
        for (; qIndex < limit; qIndex++)
        {
            e = queues[qIndex & parallelQueuesMask].poll();
            if (e != null)
            {
                break;
            }
        }
        consumerQueueIndex = qIndex;
        return e;
    }

    @Override
    public E peek()
    {
        int qIndex = consumerQueueIndex & parallelQueuesMask;
        int limit = qIndex + parallelQueues;
        E e = null;
        for (; qIndex < limit; qIndex++)
        {
            e = queues[qIndex & parallelQueuesMask].peek();
            if (e != null)
            {
                break;
            }
        }
        consumerQueueIndex = qIndex;
        return e;
    }

    @Override
    public int size()
    {
        int size = 0;
        for (MpscArrayQueue<E> lane : queues)
        {
            size += lane.size();
        }
        return size;
    }

    @Override
    public Iterator<E> iterator()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString()
    {
        return this.getClass().getName();
    }

    @Override
    public boolean relaxedOffer(E e)
    {
        if (null == e)
        {
            throw new NullPointerException();
        }
        final int parallelQueuesMask = this.parallelQueuesMask;
        int start = (int) (Thread.currentThread().getId() & parallelQueuesMask);
        final MpscArrayQueue<E>[] queues = this.queues;
        if (queues[start].failFastOffer(e) == 0)
        {
            return true;
        }
        else
        {
            // we already offered to first queue, try the rest
            for (int i = start + 1; i < start + parallelQueuesMask + 1; i++)
            {
                if (queues[i & parallelQueuesMask].failFastOffer(e) == 0)
                {
                    return true;
                }
            }
            // this is a relaxed offer, we can fail for any reason we like
            return false;
        }
    }

    @Override
    public E relaxedPoll()
    {
        int qIndex = consumerQueueIndex & parallelQueuesMask;
        int limit = qIndex + parallelQueues;
        E e = null;
        for (; qIndex < limit; qIndex++)
        {
            e = queues[qIndex & parallelQueuesMask].relaxedPoll();
            if (e != null)
            {
                break;
            }
        }
        consumerQueueIndex = qIndex;
        return e;
    }

    @Override
    public E relaxedPeek()
    {
        int qIndex = consumerQueueIndex & parallelQueuesMask;
        int limit = qIndex + parallelQueues;
        E e = null;
        for (; qIndex < limit; qIndex++)
        {
            e = queues[qIndex & parallelQueuesMask].relaxedPeek();
            if (e != null)
            {
                break;
            }
        }
        consumerQueueIndex = qIndex;
        return e;
    }

    @Override
    public int capacity()
    {
        return queues.length * queues[0].capacity();
    }


    @Override
    public int drain(Consumer<E> c)
    {
        final int limit = capacity();
        return drain(c, limit);
    }

    @Override
    public int fill(Supplier<E> s)
    {

        return MessagePassingQueueUtil.fillBounded(this, s);
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

        final int parallelQueuesMask = this.parallelQueuesMask;
        int start = (int) (Thread.currentThread().getId() & parallelQueuesMask);
        final MpscArrayQueue<E>[] queues = this.queues;
        int filled = queues[start].fill(s, limit);
        if (filled == limit)
        {
            return limit;
        }
        else
        {
            // we already offered to first queue, try the rest
            for (int i = start + 1; i < start + parallelQueuesMask + 1; i++)
            {
                filled += queues[i & parallelQueuesMask].fill(s, limit - filled);
                if (filled == limit)
                {
                    return limit;
                }
            }
            // this is a relaxed offer, we can fail for any reason we like
            return filled;
        }
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
}
