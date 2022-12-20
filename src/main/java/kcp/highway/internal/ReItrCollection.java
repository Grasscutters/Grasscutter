package kcp.highway.internal;

import java.util.Collection;

/**
 * @author <a href="mailto:szhnet@gmail.com">szh</a>
 */
public interface ReItrCollection<E> extends Collection<E> {

    @Override
    ReusableIterator<E> iterator();

}
