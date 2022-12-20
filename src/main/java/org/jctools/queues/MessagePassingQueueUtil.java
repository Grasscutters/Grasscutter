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

import org.jctools.queues.MessagePassingQueue.Consumer;
import org.jctools.queues.MessagePassingQueue.ExitCondition;
import org.jctools.queues.MessagePassingQueue.Supplier;
import org.jctools.queues.MessagePassingQueue.WaitStrategy;
import org.jctools.util.InternalAPI;
import org.jctools.util.PortableJvmInfo;

@InternalAPI
public final class MessagePassingQueueUtil
{
    public static <E> int drain(MessagePassingQueue<E> queue, Consumer<E> c, int limit)
    {
        if (null == c)
            throw new IllegalArgumentException("c is null");
        if (limit < 0)
            throw new IllegalArgumentException("limit is negative: " + limit);
        if (limit == 0)
            return 0;
        E e;
        int i = 0;
        for (; i < limit && (e = queue.relaxedPoll()) != null; i++)
        {
            c.accept(e);
        }
        return i;
    }

    public static <E> int drain(MessagePassingQueue<E> queue, Consumer<E> c)
    {
        if (null == c)
            throw new IllegalArgumentException("c is null");
        E e;
        int i = 0;
        while ((e = queue.relaxedPoll()) != null)
        {
            i++;
            c.accept(e);
        }
        return i;
    }

    public static <E> void drain(MessagePassingQueue<E> queue, Consumer<E> c, WaitStrategy wait, ExitCondition exit)
    {
        if (null == c)
            throw new IllegalArgumentException("c is null");
        if (null == wait)
            throw new IllegalArgumentException("wait is null");
        if (null == exit)
            throw new IllegalArgumentException("exit condition is null");

        int idleCounter = 0;
        while (exit.keepRunning())
        {
            final E e = queue.relaxedPoll();
            if (e == null)
            {
                idleCounter = wait.idle(idleCounter);
                continue;
            }
            idleCounter = 0;
            c.accept(e);
        }
    }

    public static <E> void fill(MessagePassingQueue<E> q, Supplier<E> s, WaitStrategy wait, ExitCondition exit)
    {
        if (null == wait)
            throw new IllegalArgumentException("waiter is null");
        if (null == exit)
            throw new IllegalArgumentException("exit condition is null");

        int idleCounter = 0;
        while (exit.keepRunning())
        {
            if (q.fill(s, PortableJvmInfo.RECOMENDED_OFFER_BATCH) == 0)
            {
                idleCounter = wait.idle(idleCounter);
                continue;
            }
            idleCounter = 0;
        }
    }

    public static <E> int fillBounded(MessagePassingQueue<E> q, Supplier<E> s)
    {
        return fillInBatchesToLimit(q, s, PortableJvmInfo.RECOMENDED_OFFER_BATCH, q.capacity());
    }

    public static <E> int fillInBatchesToLimit(MessagePassingQueue<E> q, Supplier<E> s, int batch, int limit)
    {
        long result = 0;// result is a long because we want to have a safepoint check at regular intervals
        do
        {
            final int filled = q.fill(s, batch);
            if (filled == 0)
            {
                return (int) result;
            }
            result += filled;
        }
        while (result <= limit);
        return (int) result;
    }

    public static <E> int fillUnbounded(MessagePassingQueue<E> q, Supplier<E> s)
    {
        return fillInBatchesToLimit(q, s, PortableJvmInfo.RECOMENDED_OFFER_BATCH, 4096);
    }
}
