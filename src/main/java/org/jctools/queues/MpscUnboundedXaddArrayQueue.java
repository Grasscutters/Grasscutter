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

import org.jctools.util.PortableJvmInfo;

/**
 * An MPSC array queue which grows unbounded in linked chunks.<br>
 * Differently from {@link MpscUnboundedArrayQueue} it is designed to provide a better scaling when more
 * producers are concurrently offering.<br>
 * Users should be aware that {@link #poll()} could spin while awaiting a new element to be available:
 * to avoid this behaviour {@link #relaxedPoll()} should be used instead, accounting for the semantic differences
 * between the twos.
 *
 * @author https://github.com/franz1981
 */
public class MpscUnboundedXaddArrayQueue<E> extends MpUnboundedXaddArrayQueue<MpscUnboundedXaddChunk<E>, E>
{
    /**
     * @param chunkSize The buffer size to be used in each chunk of this queue
     * @param maxPooledChunks The maximum number of reused chunks kept around to avoid allocation, chunks are pre-allocated
     */
    public MpscUnboundedXaddArrayQueue(int chunkSize, int maxPooledChunks)
    {
        super(chunkSize, maxPooledChunks);
    }

    public MpscUnboundedXaddArrayQueue(int chunkSize)
    {
        this(chunkSize, 2);
    }

    @Override
    final MpscUnboundedXaddChunk<E> newChunk(long index, MpscUnboundedXaddChunk<E> prev, int chunkSize, boolean pooled)
    {
        return new MpscUnboundedXaddChunk(index, prev, chunkSize, pooled);
    }

    @Override
    public boolean offer(E e)
    {
        if (null == e)
        {
            throw new NullPointerException();
        }
        final int chunkMask = this.chunkMask;
        final int chunkShift = this.chunkShift;

        final long pIndex = getAndIncrementProducerIndex();

        final int piChunkOffset = (int) (pIndex & chunkMask);
        final long piChunkIndex = pIndex >> chunkShift;

        MpscUnboundedXaddChunk<E> pChunk = lvProducerChunk();
        if (pChunk.lvIndex() != piChunkIndex)
        {
            // Other producers may have advanced the producer chunk as we claimed a slot in a prev chunk, or we may have
            // now stepped into a brand new chunk which needs appending.
            pChunk = producerChunkForIndex(pChunk, piChunkIndex);
        }
        pChunk.soElement(piChunkOffset, e);
        return true;
    }

    private MpscUnboundedXaddChunk<E> pollNextBuffer(MpscUnboundedXaddChunk<E> cChunk, long cIndex)
    {
        final MpscUnboundedXaddChunk<E> next = spinForNextIfNotEmpty(cChunk, cIndex);

        if (next == null)
        {
            return null;
        }

        moveToNextConsumerChunk(cChunk, next);
        assert next.lvIndex() == cIndex >> chunkShift;
        return next;
    }

    private MpscUnboundedXaddChunk<E> spinForNextIfNotEmpty(MpscUnboundedXaddChunk<E> cChunk, long cIndex)
    {
        MpscUnboundedXaddChunk<E> next = cChunk.lvNext();
        if (next == null)
        {
            if (lvProducerIndex() == cIndex)
            {
                return null;
            }
            final long ccChunkIndex = cChunk.lvIndex();
            if (lvProducerChunkIndex() == ccChunkIndex) {
                // no need to help too much here or the consumer latency will be hurt
                next = appendNextChunks(cChunk, ccChunkIndex, 1);
            }
            while (next == null)
            {
                next = cChunk.lvNext();
            }
        }
        return next;
    }

    @Override
    public E poll()
    {
        final int chunkMask = this.chunkMask;
        final long cIndex = this.lpConsumerIndex();
        final int ciChunkOffset = (int) (cIndex & chunkMask);

        MpscUnboundedXaddChunk<E> cChunk = this.lvConsumerChunk();
        // start of new chunk?
        if (ciChunkOffset == 0 && cIndex != 0)
        {
            // pollNextBuffer will verify emptiness check
            cChunk = pollNextBuffer(cChunk, cIndex);
            if (cChunk == null)
            {
                return null;
            }
        }

        E e = cChunk.lvElement(ciChunkOffset);
        if (e == null)
        {
            if (lvProducerIndex() == cIndex)
            {
                return null;
            }
            else
            {
                e = cChunk.spinForElement(ciChunkOffset, false);
            }
        }
        cChunk.soElement(ciChunkOffset, null);
        soConsumerIndex(cIndex + 1);
        return e;
    }

    @Override
    public E peek()
    {
        final int chunkMask = this.chunkMask;
        final long cIndex = this.lpConsumerIndex();
        final int ciChunkOffset = (int) (cIndex & chunkMask);

        MpscUnboundedXaddChunk<E> cChunk = this.lpConsumerChunk();
        // start of new chunk?
        if (ciChunkOffset == 0 && cIndex != 0)
        {
            cChunk = spinForNextIfNotEmpty(cChunk, cIndex);
            if (cChunk == null)
            {
                return null;
            }
        }

        E e = cChunk.lvElement(ciChunkOffset);
        if (e == null)
        {
            if (lvProducerIndex() == cIndex)
            {
                return null;
            }
            else
            {
                e = cChunk.spinForElement(ciChunkOffset, false);
            }
        }
        return e;
    }

    @Override
    public E relaxedPoll()
    {
        final int chunkMask = this.chunkMask;
        final long cIndex = this.lpConsumerIndex();
        final int ciChunkOffset = (int) (cIndex & chunkMask);

        MpscUnboundedXaddChunk<E> cChunk = this.lpConsumerChunk();
        E e;
        // start of new chunk?
        if (ciChunkOffset == 0 && cIndex != 0)
        {
            final MpscUnboundedXaddChunk<E> next = cChunk.lvNext();
            if (next == null)
            {
                return null;
            }
            e = next.lvElement(0);

            // if the next chunk doesn't have the first element set we give up
            if (e == null)
            {
                return null;
            }
            moveToNextConsumerChunk(cChunk, next);

            cChunk = next;
        }
        else
        {
            e = cChunk.lvElement(ciChunkOffset);
            if (e == null)
            {
                return null;
            }
        }

        cChunk.soElement(ciChunkOffset, null);
        soConsumerIndex(cIndex + 1);
        return e;
    }

    @Override
    public E relaxedPeek()
    {
        final int chunkMask = this.chunkMask;
        final long cIndex = this.lpConsumerIndex();
        final int cChunkOffset = (int) (cIndex & chunkMask);

        MpscUnboundedXaddChunk<E> cChunk = this.lpConsumerChunk();

        // start of new chunk?
        if (cChunkOffset == 0 && cIndex !=0)
        {
            cChunk = cChunk.lvNext();
            if (cChunk == null)
            {
                return null;
            }
        }
        return cChunk.lvElement(cChunkOffset);
    }

    @Override
    public int fill(Supplier<E> s)
    {
        long result = 0;// result is a long because we want to have a safepoint check at regular intervals
        final int capacity = chunkMask + 1;
        final int offerBatch = Math.min(PortableJvmInfo.RECOMENDED_OFFER_BATCH, capacity);
        do
        {
            final int filled = fill(s, offerBatch);
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
        if (null == c)
            throw new IllegalArgumentException("c is null");
        if (limit < 0)
            throw new IllegalArgumentException("limit is negative: " + limit);
        if (limit == 0)
            return 0;

        final int chunkMask = this.chunkMask;

        long cIndex = this.lpConsumerIndex();

        MpscUnboundedXaddChunk<E> cChunk = this.lpConsumerChunk();

        for (int i = 0; i < limit; i++)
        {
            final int consumerOffset = (int) (cIndex & chunkMask);
            E e;
            if (consumerOffset == 0 && cIndex != 0)
            {
                final MpscUnboundedXaddChunk<E> next = cChunk.lvNext();
                if (next == null)
                {
                    return i;
                }
                e = next.lvElement(0);

                // if the next chunk doesn't have the first element set we give up
                if (e == null)
                {
                    return i;
                }
                moveToNextConsumerChunk(cChunk, next);

                cChunk = next;
            }
            else
            {
                e = cChunk.lvElement(consumerOffset);
                if (e == null)
                {
                    return i;
                }
            }
            cChunk.soElement(consumerOffset, null);
            final long nextConsumerIndex = cIndex + 1;
            soConsumerIndex(nextConsumerIndex);
            c.accept(e);
            cIndex = nextConsumerIndex;
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

        final int chunkShift = this.chunkShift;
        final int chunkMask = this.chunkMask;

        long pIndex = getAndAddProducerIndex(limit);
        MpscUnboundedXaddChunk<E> pChunk = null;
        for (int i = 0; i < limit; i++)
        {
            final int pChunkOffset = (int) (pIndex & chunkMask);
            final long chunkIndex = pIndex >> chunkShift;
            if (pChunk == null || pChunk.lvIndex() != chunkIndex)
            {
                pChunk = producerChunkForIndex(pChunk, chunkIndex);
            }
            pChunk.soElement(pChunkOffset, s.get());
            pIndex++;
        }
        return limit;
    }
}
