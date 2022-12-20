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

@InternalAPI
public final class RangeUtil
{
    public static long checkPositive(long n, String name)
    {
        if (n <= 0)
        {
            throw new IllegalArgumentException(name + ": " + n + " (expected: > 0)");
        }

        return n;
    }

    public static int checkPositiveOrZero(int n, String name)
    {
        if (n < 0)
        {
            throw new IllegalArgumentException(name + ": " + n + " (expected: >= 0)");
        }

        return n;
    }

    public static int checkLessThan(int n, int expected, String name)
    {
        if (n >= expected)
        {
            throw new IllegalArgumentException(name + ": " + n + " (expected: < " + expected + ')');
        }

        return n;
    }

    public static int checkLessThanOrEqual(int n, long expected, String name)
    {
        if (n > expected)
        {
            throw new IllegalArgumentException(name + ": " + n + " (expected: <= " + expected + ')');
        }

        return n;
    }

    public static int checkGreaterThanOrEqual(int n, int expected, String name)
    {
        if (n < expected)
        {
            throw new IllegalArgumentException(name + ": " + n + " (expected: >= " + expected + ')');
        }

        return n;
    }
}
