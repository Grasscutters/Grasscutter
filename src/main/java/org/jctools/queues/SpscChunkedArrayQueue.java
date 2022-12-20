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

import org.jctools.util.Pow2;
import org.jctools.util.RangeUtil;

import static org.jctools.util.UnsafeRefArrayAccess.*;

/**
 * An SPSC array queue which starts at <i>initialCapacity</i> and grows to <i>maxCapacity</i> in linked chunks
 * of the initial size. The queue grows only when the current chunk is full and elements are not copied on
 * resize, instead a link to the new chunk is stored in the old chunk for the consumer to follow.<br>
 *
 * @param <E>
 */
public class SpscChunkedArrayQueue<E> extends BaseSpscLinkedArrayQueue<E>
{
    private final int maxQueueCapacity;
    private long producerQueueLimit;

    public SpscChunkedArrayQueue(int capacity)
    {
        this(Math.max(8, Pow2.roundToPowerOfTwo(capacity / 8)), capacity);
    }

    public SpscChunkedArrayQueue(int chunkSize, int capacity)
    {
        RangeUtil.checkGreaterThanOrEqual(capacity, 16, "capacity");
        // minimal chunk size of eight makes sure minimal lookahead step is 2
        RangeUtil.checkGreaterThanOrEqual(chunkSize, 8, "chunkSize");

        maxQueueCapacity = Pow2.roundToPowerOfTwo(capacity);
        int chunkCapacity = Pow2.roundToPowerOfTwo(chunkSize);
        RangeUtil.checkLessThan(chunkCapacity, maxQueueCapacity, "chunkCapacity");

        long mask = chunkCapacity - 1;
        // need extra element to point at next array
        E[] buffer = allocateRefArray(chunkCapacity + 1);
        producerBuffer = buffer;
        producerMask = mask;
        consumerBuffer = buffer;
        consumerMask = mask;
        producerBufferLimit = mask - 1; // we know it's all empty to start with
        producerQueueLimit = maxQueueCapacity;
    }

    @Override
    final boolean offerColdPath(E[] buffer, long mask, long pIndex, long offset, E v, Supplier<? extends E> s)
    {
        // use a fixed lookahead step based on buffer capacity
        final long lookAheadStep = (mask + 1) / 4;
        long pBufferLimit = pIndex + lookAheadStep;

        long pQueueLimit = producerQueueLimit;

        if (pIndex >= pQueueLimit)
        {
            // we tested against a potentially out of date queue limit, refresh it
            final long cIndex = lvConsumerIndex();
            producerQueueLimit = pQueueLimit = cIndex + maxQueueCapacity;
            // if we're full we're full
            if (pIndex >= pQueueLimit)
            {
                return false;
            }
        }
        // if buffer limit is after queue limit we use queue limit. We need to handle overflow so
        // cannot use Math.min
        if (pBufferLimit - pQueueLimit > 0)
        {
            pBufferLimit = pQueueLimit;
        }

        // go around the buffer or add a new buffer
        if (pBufferLimit > pIndex + 1 && // there's sufficient room in buffer/queue to use pBufferLimit
            null == lvRefElement(buffer, calcCircularRefElementOffset(pBufferLimit, mask)))
        {
            producerBufferLimit = pBufferLimit - 1; // joy, there's plenty of room
            writeToQueue(buffer, v == null ? s.get() : v, pIndex, offset);
        }
        else if (null == lvRefElement(buffer, calcCircularRefElementOffset(pIndex + 1, mask)))
        { // buffer is not full
            writeToQueue(buffer, v == null ? s.get() : v, pIndex, offset);
        }
        else
        {
            // we got one slot left to write into, and we are not full. Need to link new buffer.
            // allocate new buffer of same length
            final E[] newBuffer = allocateRefArray((int) (mask + 2));
            producerBuffer = newBuffer;

            linkOldToNew(pIndex, buffer, offset, newBuffer, offset, v == null ? s.get() : v);
        }
        return true;
    }

    @Override
    public int capacity()
    {
        return maxQueueCapacity;
    }
}
