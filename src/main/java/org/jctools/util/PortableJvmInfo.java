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

/**
 * JVM Information that is standard and available on all JVMs (i.e. does not use unsafe)
 */
@InternalAPI
public interface PortableJvmInfo {
    int CACHE_LINE_SIZE = Integer.getInteger("jctools.cacheLineSize", 64);
    int CPUs = Runtime.getRuntime().availableProcessors();
    int RECOMENDED_OFFER_BATCH = CPUs * 4;
    int RECOMENDED_POLL_BATCH = CPUs * 4;
}
