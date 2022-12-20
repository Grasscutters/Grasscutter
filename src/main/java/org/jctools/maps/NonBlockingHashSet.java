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
package org.jctools.maps;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A simple wrapper around {@link NonBlockingHashMap} making it implement the
 * {@link Set} interface.  All operations are Non-Blocking and multi-thread safe.
 *
 * @since 1.5
 * @author Cliff Click
 */
public class NonBlockingHashSet<E> extends AbstractSet<E> implements Serializable {
  private static final Object V = "";

  private final NonBlockingHashMap<E,Object> _map;

  /** Make a new empty {@link NonBlockingHashSet}.  */
  public NonBlockingHashSet() { super(); _map = new NonBlockingHashMap<E,Object>(); }

  /** Add {@code o} to the set.
   *  @return true if {@code o} was added to the set, false
   *  if {@code o} was already in the set.  */
  public boolean add( final E o ) { return _map.putIfAbsent(o,V) == null; }

  /**  @return true if {@code o} is in the set.  */
  public boolean contains   ( final Object     o ) { return _map.containsKey(o); }

  /**  @return Returns the match for {@code o} if {@code o} is in the set.  */
  public E get( final E o ) { return _map.getk(o); }

  /** Remove {@code o} from the set.
   * @return true if {@code o} was removed to the set, false
   * if {@code o} was not in the set.
   */
  public boolean remove( final Object o ) { return _map.remove(o) == V; }
  /** Current count of elements in the set.  Due to concurrent racing updates,
   *  the size is only ever approximate.  Updates due to the calling thread are
   *  immediately visible to calling thread.
   *  @return count of elements.   */
  public int size( ) { return _map.size(); }
  /** Empty the set. */
  public void clear( ) { _map.clear(); }

  public Iterator<E>iterator( ) { return _map.keySet().iterator(); }
}
