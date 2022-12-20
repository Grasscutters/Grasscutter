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
/**
 * This package aims to fill a gap in current JDK implementations in offering lock free (wait free where possible)
 * queues for inter-thread message passing with finer grained guarantees and an emphasis on performance.<br>
 * At the time of writing the only lock free queue available in the JDK is
 * {@link java.util.concurrent.ConcurrentLinkedQueue} which is an unbounded multi-producer, multi-consumer queue which
 * is further encumbered by the need to implement the full range of {@link java.util.Queue} methods. In this package we
 * offer a range of implementations:
 * <ol>
 * <li>Bounded/Unbounded SPSC queues - Serving the Single Producer Single Consumer use case.
 * <li>Bounded/Unbounded MPSC queues - The Multi Producer Single Consumer case also has a multi-lane implementation on
 * offer which trades the FIFO ordering(re-ordering is not limited) for reduced contention and increased throughput
 * under contention.
 * <li>Bounded SPMC/MPMC queues
 * </ol>
 * <p>
 * <br>
 * <b>Limited Queue methods support:</b><br>
 * The queues implement a subset of the {@link java.util.Queue} interface which is documented under the
 * {@link org.jctools.queues.MessagePassingQueue} interface. In particular {@link java.util.Queue#iterator()} is usually not
 * supported and dependent methods from {@link java.util.AbstractQueue} are also not supported such as:
 * <ol>
 * <li>{@link java.util.Queue#remove(Object)}
 * <li>{@link java.util.Queue#removeAll(java.util.Collection)}
 * <li>{@link java.util.Queue#removeIf(java.util.function.Predicate)}
 * <li>{@link java.util.Queue#contains(Object)}
 * <li>{@link java.util.Queue#containsAll(java.util.Collection)}
 * </ol>
 * A few queues do support a limited form of iteration. This support is documented in the Javadoc of the relevant queues.
 * <p>
 * <br>
 * <b>Memory layout controls and False Sharing:</b><br>
 * The classes in this package use what is considered at the moment the most reliable method of controlling class field
 * layout, namely inheritance. The method is described in this
 * <a href="http://psy-lob-saw.blogspot.com/2013/05/know-thy-java-object-memory-layout.html">post</a> which also covers
 * why other methods are currently suspect.<br>
 * Note that we attempt to tackle both active (write/write) and passive(read/write) false sharing case:
 * <ol>
 * <li>Hot counters (or write locations) are padded.
 * <li>Read-Only shared fields are padded.
 * <li>Array edges are NOT padded (though doing so is entirely legitimate).
 * </ol>
 * <p>
 * <br>
 * <b>Use of sun.misc.Unsafe:</b><br>
 * A choice is made in this library to utilize sun.misc.Unsafe for performance reasons. In this package we have two use
 * cases:
 * <ol>
 * <li>The queue counters in the queues are all inlined (i.e. are primitive fields of the queue classes). To allow
 * lazySet/CAS semantics to these fields we could use {@link java.util.concurrent.atomic.AtomicLongFieldUpdater} but
 * choose not to for performance reasons. On newer OpenJDKs where AFU is made more performant the difference is small.
 * <li>We use Unsafe to gain volatile/lazySet access to array elements. We could use
 * {@link java.util.concurrent.atomic.AtomicReferenceArray} but choose not to for performance reasons(extra reference
 * chase and redundant boundary checks).
 * </ol>
 * Both use cases should be made obsolete by VarHandles at some point.
 * <p>
 * <br>
 * <b>Avoiding redundant loads of fields:</b><br>
 * Because a volatile load will force any following field access to reload the field value an effort is made to cache
 * field values in local variables where possible and expose interfaces which allow the code to capitalize on such
 * caching. As a convention the local variable name will be the field name and will be final.
 * <p>
 * <br>
 * <b>Method naming conventions:</b><br>
 * The following convention is followed in method naming to highlight volatile/ordered/plain access to fields:
 * <ol>
 * <li>lpFoo/spFoo: these will be plain load or stores to the field. No memory ordering is needed or expected.
 * <li>soFoo: this is an ordered stored to the field (like
 * {@link java.util.concurrent.atomic.AtomicInteger#lazySet(int)}). Implies an ordering of stores (StoreStore barrier
 * before the store).
 * <li>lv/svFoo: these are volatile load/store. A store implies a StoreLoad barrier, a load implies LoadLoad barrier
 * before and LoadStore after.
 * <li>casFoo: compare and swap the field. StoreLoad if successful. See
 * {@link java.util.concurrent.atomic.AtomicInteger#compareAndSet(int, int)}
 * <li>xchgFoo: atomically get and set the field. Effectively a StoreLoad. See
 * {@link java.util.concurrent.atomic.AtomicInteger#getAndSet(int)}
 * <li>xaddFoo: atomically get and add to the field. Effectively a StoreLoad. See
 * {@link java.util.concurrent.atomic.AtomicInteger#getAndAdd(int)}
 * </ol>
 * It is generally expected that a volatile load signals the acquire of a field previously released by a non-plain
 * store.
 *
 * @author nitsanw
 */
package org.jctools.queues;
