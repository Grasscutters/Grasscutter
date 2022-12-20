package org.jctools.queues;

/**
 * This interface is provided for monitoring purposes only and is only available on queues where it is easy to
 * provide it. The producer/consumer progress indicators usually correspond with the number of elements
 * offered/polled, but they are not guaranteed to maintain that semantic.
 */
public interface QueueProgressIndicators
{

    /**
     * This method has no concurrent visibility semantics. The value returned may be negative. Under normal
     * circumstances 2 consecutive calls to this method can offer an idea of progress made by producer threads
     * by subtracting the 2 results though in extreme cases (if producers have progressed by more than 2^64)
     * this may also fail.<br/>
     * This value will normally indicate number of elements passed into the queue, but may under some
     * circumstances be a derivative of that figure. This method should not be used to derive size or
     * emptiness.
     *
     * @return the current value of the producer progress index
     */
    long currentProducerIndex();

    /**
     * This method has no concurrent visibility semantics. The value returned may be negative. Under normal
     * circumstances 2 consecutive calls to this method can offer an idea of progress made by consumer threads
     * by subtracting the 2 results though in extreme cases (if consumers have progressed by more than 2^64)
     * this may also fail.<br/>
     * This value will normally indicate number of elements taken out of the queue, but may under some
     * circumstances be a derivative of that figure. This method should not be used to derive size or
     * emptiness.
     *
     * @return the current value of the consumer progress index
     */
    long currentConsumerIndex();
}
