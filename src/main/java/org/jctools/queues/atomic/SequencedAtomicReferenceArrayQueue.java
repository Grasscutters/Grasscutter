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
package org.jctools.queues.atomic;

import java.util.concurrent.atomic.AtomicLongArray;

abstract class SequencedAtomicReferenceArrayQueue<E> extends
    AtomicReferenceArrayQueue<E>
{
    protected final AtomicLongArray sequenceBuffer;

    public SequencedAtomicReferenceArrayQueue(int capacity)
    {
        super(capacity);
        int actualCapacity = this.mask + 1;
        // pad data on either end with some empty slots.
        sequenceBuffer = new AtomicLongArray(actualCapacity);
        for (int i = 0; i < actualCapacity; i++)
        {
            soSequence(sequenceBuffer, i, i);
        }
    }

    protected final long calcSequenceOffset(long index)
    {
        return calcSequenceOffset(index, mask);
    }

    protected static int calcSequenceOffset(long index, int mask)
    {
        return (int) index & mask;
    }

    protected final void soSequence(AtomicLongArray buffer, int offset, long e)
    {
        buffer.lazySet(offset, e);
    }

    protected final long lvSequence(AtomicLongArray buffer, int offset)
    {
        return buffer.get(offset);
    }

}
