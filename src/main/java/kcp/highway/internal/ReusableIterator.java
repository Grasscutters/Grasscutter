package kcp.highway.internal;

import java.util.Iterator;

/**
 * Reusable iterator
 *
 * @author <a href="mailto:szhnet@gmail.com">szh</a>
 */
public interface ReusableIterator<E> extends Iterator<E> {

    /**
     * Reset the iterator to initial state.
     *
     * @return this object
     */
    ReusableIterator<E> rewind();

}
