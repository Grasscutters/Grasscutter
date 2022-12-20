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

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * Why should we resort to using Unsafe?<br>
 * <ol>
 * <li>To construct class fields which allow volatile/ordered/plain access: This requirement is covered by
 * {@link AtomicReferenceFieldUpdater} and similar but their performance is arguably worse than the DIY approach
 * (depending on JVM version) while Unsafe intrinsification is a far lesser challenge for JIT compilers.
 * <li>To construct flavors of {@link AtomicReferenceArray}.
 * <li>Other use cases exist but are not present in this library yet.
 * </ol>
 *
 * @author nitsanw
 */
@InternalAPI
public class UnsafeAccess
{
    public static final boolean SUPPORTS_GET_AND_SET_REF;
    public static final boolean SUPPORTS_GET_AND_ADD_LONG;
    public static final Unsafe UNSAFE;

    static
    {
        UNSAFE = getUnsafe();
        SUPPORTS_GET_AND_SET_REF = hasGetAndSetSupport();
        SUPPORTS_GET_AND_ADD_LONG = hasGetAndAddLongSupport();
    }

    private static Unsafe getUnsafe()
    {
        Unsafe instance;
        try
        {
            final Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            instance = (Unsafe) field.get(null);
        }
        catch (Exception ignored)
        {
            // Some platforms, notably Android, might not have a sun.misc.Unsafe implementation with a private
            // `theUnsafe` static instance. In this case we can try to call the default constructor, which is sufficient
            // for Android usage.
            try
            {
                Constructor<Unsafe> c = Unsafe.class.getDeclaredConstructor();
                c.setAccessible(true);
                instance = c.newInstance();
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    private static boolean hasGetAndSetSupport()
    {
        try
        {
            Unsafe.class.getMethod("getAndSetObject", Object.class, Long.TYPE, Object.class);
            return true;
        }
        catch (Exception ignored)
        {
        }
        return false;
    }

    private static boolean hasGetAndAddLongSupport()
    {
        try
        {
            Unsafe.class.getMethod("getAndAddLong", Object.class, Long.TYPE, Long.TYPE);
            return true;
        }
        catch (Exception ignored)
        {
        }
        return false;
    }

    public static long fieldOffset(Class clz, String fieldName) throws RuntimeException
    {
        try
        {
            return UNSAFE.objectFieldOffset(clz.getDeclaredField(fieldName));
        }
        catch (NoSuchFieldException e)
        {
            throw new RuntimeException(e);
        }
    }
}
