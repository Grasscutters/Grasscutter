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

import org.jctools.queues.spec.ConcurrentQueueSpec;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The queue factory produces {@link java.util.Queue} instances based on a best fit to the {@link ConcurrentQueueSpec}.
 * This allows minimal dependencies between user code and the queue implementations and gives users a way to express
 * their requirements on a higher level.
 *
 * @author nitsanw
 * @author akarnokd
 */
public class AtomicQueueFactory
{

    public static <E> Queue<E> newQueue(ConcurrentQueueSpec qs)
    {
        if (qs.isBounded())
        {
            // SPSC
            if (qs.isSpsc())
            {
                return new SpscAtomicArrayQueue<E>(qs.capacity);
            }
            // MPSC
            else if (qs.isMpsc())
            {
                return new MpscAtomicArrayQueue<E>(qs.capacity);
            }
            // SPMC
            else if (qs.isSpmc())
            {
                return new SpmcAtomicArrayQueue<E>(qs.capacity);
            }
            // MPMC
            else
            {
                return new MpmcAtomicArrayQueue<E>(qs.capacity);
            }
        }
        else
        {
            // SPSC
            if (qs.isSpsc())
            {
                return new SpscLinkedAtomicQueue<E>();
            }
            // MPSC
            else if (qs.isMpsc())
            {
                return new MpscLinkedAtomicQueue<E>();
            }
        }
        return new ConcurrentLinkedQueue<E>();
    }
}
