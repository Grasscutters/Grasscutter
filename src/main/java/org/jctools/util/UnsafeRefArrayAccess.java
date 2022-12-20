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
package org.jctools.util;

import static org.jctools.util.UnsafeAccess.UNSAFE;

@InternalAPI
public final class UnsafeRefArrayAccess
{
    public static final long REF_ARRAY_BASE;
    public static final int REF_ELEMENT_SHIFT;

    static
    {
        final int scale = UnsafeAccess.UNSAFE.arrayIndexScale(Object[].class);
        if (4 == scale)
        {
            REF_ELEMENT_SHIFT = 2;
        }
        else if (8 == scale)
        {
            REF_ELEMENT_SHIFT = 3;
        }
        else
        {
            throw new IllegalStateException("Unknown pointer size: " + scale);
        }
        REF_ARRAY_BASE = UnsafeAccess.UNSAFE.arrayBaseOffset(Object[].class);
    }

    /**
     * A plain store (no ordering/fences) of an element to a given offset
     *
     * @param buffer this.buffer
     * @param offset computed via {@link UnsafeRefArrayAccess#calcRefElementOffset(long)}
     * @param e      an orderly kitty
     */
    public static <E> void spRefElement(E[] buffer, long offset, E e)
    {
        UNSAFE.putObject(buffer, offset, e);
    }

    /**
     * An ordered store of an element to a given offset
     *
     * @param buffer this.buffer
     * @param offset computed via {@link UnsafeRefArrayAccess#calcCircularRefElementOffset}
     * @param e      an orderly kitty
     */
    public static <E> void soRefElement(E[] buffer, long offset, E e)
    {
        UNSAFE.putOrderedObject(buffer, offset, e);
    }

    /**
     * A plain load (no ordering/fences) of an element from a given offset.
     *
     * @param buffer this.buffer
     * @param offset computed via {@link UnsafeRefArrayAccess#calcRefElementOffset(long)}
     * @return the element at the offset
     */
    @SuppressWarnings("unchecked")
    public static <E> E lpRefElement(E[] buffer, long offset)
    {
        return (E) UNSAFE.getObject(buffer, offset);
    }

    /**
     * A volatile load of an element from a given offset.
     *
     * @param buffer this.buffer
     * @param offset computed via {@link UnsafeRefArrayAccess#calcRefElementOffset(long)}
     * @return the element at the offset
     */
    @SuppressWarnings("unchecked")
    public static <E> E lvRefElement(E[] buffer, long offset)
    {
        return (E) UNSAFE.getObjectVolatile(buffer, offset);
    }

    /**
     * @param index desirable element index
     * @return the offset in bytes within the array for a given index
     */
    public static long calcRefElementOffset(long index)
    {
        return REF_ARRAY_BASE + (index << REF_ELEMENT_SHIFT);
    }

    /**
     * Note: circular arrays are assumed a power of 2 in length and the `mask` is (length - 1).
     *
     * @param index desirable element index
     * @param mask (length - 1)
     * @return the offset in bytes within the circular array for a given index
     */
    public static long calcCircularRefElementOffset(long index, long mask)
    {
        return REF_ARRAY_BASE + ((index & mask) << REF_ELEMENT_SHIFT);
    }

    /**
     * This makes for an easier time generating the atomic queues, and removes some warnings.
     */
    @SuppressWarnings("unchecked")
    public static <E> E[] allocateRefArray(int capacity)
    {
        return (E[]) new Object[capacity];
    }
}
