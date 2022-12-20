package org.jctools.counters;

/**
 * Base counter interface.
 *
 * @author Tolstopyatov Vsevolod
 */
public interface Counter {

    void inc();

    void inc(long delta);

    long get();

    long getAndReset();
}
