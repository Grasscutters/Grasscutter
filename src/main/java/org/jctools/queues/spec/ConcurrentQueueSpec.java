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
package org.jctools.queues.spec;

import org.jctools.queues.MessagePassingQueue;

@Deprecated//(since = "3.0.0")
public final class ConcurrentQueueSpec
{
    public final int producers;
    public final int consumers;
    public final int capacity;
    public final Ordering ordering;
    public final Preference preference;

    public static ConcurrentQueueSpec createBoundedSpsc(int capacity)
    {
        return new ConcurrentQueueSpec(1, 1, capacity, Ordering.FIFO, Preference.NONE);
    }

    public static ConcurrentQueueSpec createBoundedMpsc(int capacity)
    {
        return new ConcurrentQueueSpec(0, 1, capacity, Ordering.FIFO, Preference.NONE);
    }

    public static ConcurrentQueueSpec createBoundedSpmc(int capacity)
    {
        return new ConcurrentQueueSpec(1, 0, capacity, Ordering.FIFO, Preference.NONE);
    }

    public static ConcurrentQueueSpec createBoundedMpmc(int capacity)
    {
        return new ConcurrentQueueSpec(0, 0, capacity, Ordering.FIFO, Preference.NONE);
    }

    public ConcurrentQueueSpec(int producers, int consumers, int capacity, Ordering ordering, Preference preference)
    {
        super();
        this.producers = producers;
        this.consumers = consumers;
        this.capacity = capacity < 1 ? MessagePassingQueue.UNBOUNDED_CAPACITY : capacity;
        this.ordering = ordering;
        this.preference = preference;
    }

    public boolean isSpsc()
    {
        return consumers == 1 && producers == 1;
    }

    public boolean isMpsc()
    {
        return consumers == 1 && producers != 1;
    }

    public boolean isSpmc()
    {
        return consumers != 1 && producers == 1;
    }

    public boolean isMpmc()
    {
        return consumers != 1 && producers != 1;
    }

    public boolean isBounded()
    {
        return capacity != MessagePassingQueue.UNBOUNDED_CAPACITY;
    }
}