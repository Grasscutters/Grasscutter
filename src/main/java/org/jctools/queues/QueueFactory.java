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

import org.jctools.queues.spec.ConcurrentQueueSpec;
import org.jctools.queues.spec.Ordering;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The queue factory produces {@link java.util.Queue} instances based on a best fit to the {@link ConcurrentQueueSpec}.
 * This allows minimal dependencies between user code and the queue implementations and gives users a way to express
 * their requirements on a higher level.
 *
 * @author nitsanw
 */
@Deprecated//(since = "3.0.0")
public class QueueFactory
{

    public static <E> Queue<E> newQueue(ConcurrentQueueSpec qs)
    {
        if (qs.isBounded())
        {
            // SPSC
            if (qs.isSpsc())
            {
                return new SpscArrayQueue<E>(qs.capacity);
            }
            // MPSC
            else if (qs.isMpsc())
            {
                if (qs.ordering != Ordering.NONE)
                {
                    return new MpscArrayQueue<E>(qs.capacity);
                }
                else
                {
                    return new MpscCompoundQueue<E>(qs.capacity);
                }
            }
            // SPMC
            else if (qs.isSpmc())
            {
                return new SpmcArrayQueue<E>(qs.capacity);
            }
            // MPMC
            else
            {
                return new MpmcArrayQueue<E>(qs.capacity);
            }
        }
        else
        {
            // SPSC
            if (qs.isSpsc())
            {
                return new SpscLinkedQueue<E>();
            }
            // MPSC
            else if (qs.isMpsc())
            {
                return new MpscLinkedQueue();
            }
        }
        return new ConcurrentLinkedQueue<E>();
    }
}
