package kcp.highway.threadPool.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kcp.highway.threadPool.order.waiteStrategy.BlockingWaitConditionStrategy;
import kcp.highway.threadPool.order.waiteStrategy.WaitCondition;
import kcp.highway.threadPool.order.waiteStrategy.WaitConditionStrategy;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * M:N队列 均衡使用cpu
 * TODO 消费者执行一定任务释放线程给其他队列，防止其他队列服务饿死
 * Created by JinMiao
 * 2020/6/19.
 */
public class OrderedThreadPoolExecutor extends ThreadPoolExecutor {
    /**
     * A logger for this class (commented as it breaks MDCFlter tests)
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderedThreadPoolExecutor.class);

    /**
     * A default value for the initial pool size
     */
    private static final int DEFAULT_INITIAL_THREAD_POOL_SIZE = 0;

    /**
     * A default value for the maximum pool size
     */
    private static final int DEFAULT_MAX_THREAD_POOL = 16;

    /**
     * A default value for the KeepAlive delay
     */
    private static final int DEFAULT_KEEP_ALIVE = 30;

    private static final OrderedThreadSession EXIT_SIGNAL = new OrderedThreadSession();

    /**
     * A queue used to store the available sessions
     */
    private final Queue<OrderedThreadSession> waitingSessions = new ConcurrentLinkedQueue<>();

    private final WaitConditionStrategy waitConditionStrategy = new BlockingWaitConditionStrategy();

    private final Set<Worker> workers = new HashSet<>();

    private volatile int largestPoolSize;

    private final AtomicInteger idleWorkers = new AtomicInteger();

    private long completedTaskCount;

    private volatile boolean shutdown;


    /**
     * Creates a default ThreadPool, with default values :
     * - minimum pool size is 0
     * - maximum pool size is 16
     * - keepAlive set to 30 seconds
     * - A default ThreadFactory
     * - All events are accepted
     */
    public OrderedThreadPoolExecutor() {
        this(DEFAULT_INITIAL_THREAD_POOL_SIZE, DEFAULT_MAX_THREAD_POOL, DEFAULT_KEEP_ALIVE, TimeUnit.SECONDS, Executors
                .defaultThreadFactory());
    }

    /**
     * Creates a default ThreadPool, with default values :
     * - minimum pool size is 0
     * - keepAlive set to 30 seconds
     * - A default ThreadFactory
     * - All events are accepted
     *
     * @param maximumPoolSize The maximum pool size
     */
    public OrderedThreadPoolExecutor(int maximumPoolSize) {
        this(DEFAULT_INITIAL_THREAD_POOL_SIZE, maximumPoolSize, DEFAULT_KEEP_ALIVE, TimeUnit.SECONDS, Executors
                .defaultThreadFactory());
    }

    /**
     * Creates a default ThreadPool, with default values :
     * - keepAlive set to 30 seconds
     * - A default ThreadFactory
     * - All events are accepted
     *
     * @param corePoolSize    The initial pool sizePoolSize
     * @param maximumPoolSize The maximum pool size
     */
    public OrderedThreadPoolExecutor(int corePoolSize, int maximumPoolSize) {
        this(corePoolSize, maximumPoolSize, DEFAULT_KEEP_ALIVE, TimeUnit.SECONDS, Executors.defaultThreadFactory());
    }

    /**
     * Creates a default ThreadPool, with default values :
     * - A default ThreadFactory
     * - All events are accepted
     *
     * @param corePoolSize    The initial pool sizePoolSize
     * @param maximumPoolSize The maximum pool size
     * @param keepAliveTime   Default duration for a thread
     * @param unit            Time unit used for the keepAlive value
     */
    public OrderedThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, Executors.defaultThreadFactory());
    }


    /**
     * Creates a new instance of a OrderedThreadPoolExecutor.
     *
     * @param corePoolSize    The initial pool sizePoolSize
     * @param maximumPoolSize The maximum pool size
     * @param keepAliveTime   Default duration for a thread
     * @param unit            Time unit used for the keepAlive value
     * @param threadFactory   The factory used to create threads
     */
    public OrderedThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                     ThreadFactory threadFactory) {
        // We have to initialize the pool with default values (0 and 1) in order to
        // handle the exception in a better way. We can't add a try {} catch() {}
        // around the super() call.
        super(DEFAULT_INITIAL_THREAD_POOL_SIZE, 1, keepAliveTime, unit, new SynchronousQueue<Runnable>(),
                threadFactory, new AbortPolicy());

        if (corePoolSize < DEFAULT_INITIAL_THREAD_POOL_SIZE) {
            throw new IllegalArgumentException("corePoolSize: " + corePoolSize);
        }

        if ((maximumPoolSize <= 0) || (maximumPoolSize < corePoolSize)) {
            throw new IllegalArgumentException("maximumPoolSize: " + maximumPoolSize);
        }

        // Now, we can setup the pool sizes
        super.setMaximumPoolSize(maximumPoolSize);
        super.setCorePoolSize(corePoolSize);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setRejectedExecutionHandler(RejectedExecutionHandler handler) {
        // Ignore the request.  It must always be AbortPolicy.
    }

    /**
     * Add a new thread to execute a task, if needed and possible.
     * It depends on the current pool size. If it's full, we do nothing.
     */
    private void addWorker() {
        synchronized (workers) {
            if (workers.size() >= super.getMaximumPoolSize()) {
                return;
            }

            // Create a new worker, and add it to the thread pool
            Worker worker = new Worker();
            Thread thread = getThreadFactory().newThread(worker);

            // As we have added a new thread, it's considered as idle.
            idleWorkers.incrementAndGet();

            // Now, we can start it.
            thread.start();
            workers.add(worker);

            if (workers.size() > largestPoolSize) {
                largestPoolSize = workers.size();
            }
        }
    }

    /**
     * Add a new Worker only if there are no idle worker.
     */
    private void addWorkerIfNecessary() {
        if (idleWorkers.get() == 0) {
            synchronized (workers) {
                if (workers.isEmpty() || (idleWorkers.get() == 0)) {
                    addWorker();
                }
            }
        }
    }

    private void removeWorker() {
        synchronized (workers) {
            if (workers.size() <= super.getCorePoolSize()) {
                return;
            }
            waitingSessions.offer(EXIT_SIGNAL);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaximumPoolSize(int maximumPoolSize) {
        if ((maximumPoolSize <= 0) || (maximumPoolSize < super.getCorePoolSize())) {
            throw new IllegalArgumentException("maximumPoolSize: " + maximumPoolSize);
        }

        synchronized (workers) {
            super.setMaximumPoolSize(maximumPoolSize);
            int difference = workers.size() - maximumPoolSize;
            while (difference > 0) {
                removeWorker();
                --difference;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {

        long deadline = System.currentTimeMillis() + unit.toMillis(timeout);

        synchronized (workers) {
            while (!isTerminated()) {
                long waitTime = deadline - System.currentTimeMillis();
                if (waitTime <= 0) {
                    break;
                }

                workers.wait(waitTime);
            }
        }
        return isTerminated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isShutdown() {
        return shutdown;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTerminated() {
        if (!shutdown) {
            return false;
        }

        synchronized (workers) {
            return workers.isEmpty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shutdown() {
        if (shutdown) {
            return;
        }

        shutdown = true;

        synchronized (workers) {
            for (int i = workers.size(); i > 0; i--) {
                waitingSessions.offer(EXIT_SIGNAL);
            }
            waitConditionStrategy.signalAllWhenBlocking();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Runnable> shutdownNow() {
        shutdown();

        List<Runnable> answer = new ArrayList<>();
        OrderedThreadSession session;

        while ((session = waitingSessions.poll()) != null) {
            if (session == EXIT_SIGNAL) {
                waitingSessions.offer(EXIT_SIGNAL);
                Thread.yield(); // Let others take the signal.
                continue;
            }

            Queue<Runnable> queue = session.getQueue();

            synchronized (queue) {
                for (Runnable task : queue) {
                    answer.add(task);
                }
                queue.clear();
            }
        }

        return answer;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Runnable task) {
        if (shutdown) {
            rejectTask(task);
        }

        IOrderTask event = (IOrderTask) task;

        // Get the associated session
        OrderedThreadSession session = event.getSession();

        // Get the sqession's queue of events
        Queue<Runnable> tasksQueue = session.getQueue();


        boolean offerSession = tasksQueue.offer(event);
        //没有投递成功
        if (!offerSession) {
            //TODO 队列满了应该怎么办？ 丢弃该消息?
            return;
        }
        boolean processing = offerWaitSession(session);
        if(!processing){
            addWorkerIfNecessary();
        }
    }


    private boolean offerWaitSession(OrderedThreadSession session) {
        boolean processing=false;
        //判断任务是否在执行中
        if (session.getProcessingCompleted().compareAndSet(true, false)) {
            waitingSessions.offer(session);
            processing = true;
        }
        waitConditionStrategy.signalAllWhenBlocking();
        return processing;
    }


    private void rejectTask(Runnable task) {
        getRejectedExecutionHandler().rejectedExecution(task, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getActiveCount() {
        synchronized (workers) {
            return workers.size() - idleWorkers.get();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getCompletedTaskCount() {
        synchronized (workers) {
            long answer = completedTaskCount;
            for (Worker w : workers) {
                answer += w.completedTaskCount.get();
            }

            return answer;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLargestPoolSize() {
        return largestPoolSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPoolSize() {
        synchronized (workers) {
            return workers.size();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getTaskCount() {
        return getCompletedTaskCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTerminating() {
        synchronized (workers) {
            return isShutdown() && !isTerminated();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int prestartAllCoreThreads() {
        int answer = 0;
        synchronized (workers) {
            for (int i = super.getCorePoolSize() - workers.size(); i > 0; i--) {
                addWorker();
                answer++;
            }
        }
        return answer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean prestartCoreThread() {
        synchronized (workers) {
            if (workers.size() < super.getCorePoolSize()) {
                addWorker();
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlockingQueue<Runnable> getQueue() {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void purge() {
        // Nothing to purge in this implementation.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Runnable task) {
        IOrderTask event = (IOrderTask) task;
        OrderedThreadSession session = event.getSession();
        Queue<Runnable> tasksQueue = session.getQueue();
        return tasksQueue.remove(task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCorePoolSize(int corePoolSize) {
        if (corePoolSize < 0) {
            throw new IllegalArgumentException("corePoolSize: " + corePoolSize);
        }
        if (corePoolSize > super.getMaximumPoolSize()) {
            throw new IllegalArgumentException("corePoolSize exceeds maximumPoolSize");
        }

        synchronized (workers) {
            if (super.getCorePoolSize() > corePoolSize) {
                for (int i = super.getCorePoolSize() - corePoolSize; i > 0; i--) {
                    removeWorker();
                }
            }
            super.setCorePoolSize(corePoolSize);
        }
    }

    private class Worker implements Runnable, WaitCondition<OrderedThreadSession> {

        private AtomicLong completedTaskCount = new AtomicLong(0);

        private Thread thread;

        /**
         * @inheritedDoc
         */
        @Override
        public void run() {
            thread = Thread.currentThread();

            try {
                for (;;) {
                    OrderedThreadSession session = null;
                    try {
                        session = waitConditionStrategy.waitFor(this, getKeepAliveTime(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    idleWorkers.decrementAndGet();
                    try {
                        if (session == null) {
                            synchronized (workers) {
                                if (workers.size() > getCorePoolSize()) {
                                    // Remove now to prevent duplicate exit.
                                    workers.remove(this);
                                    break;
                                }
                            }
                            continue;
                        }

                        if (session == EXIT_SIGNAL) {
                            break;
                        }
                        runTasks(session);
                    } finally {
                        idleWorkers.incrementAndGet();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                synchronized (workers) {
                    workers.remove(this);
                    OrderedThreadPoolExecutor.this.completedTaskCount += completedTaskCount.get();
                    workers.notifyAll();
                }
            }
        }

        private OrderedThreadSession fetchSession() {
            OrderedThreadSession session = null;
            long currentTime = System.currentTimeMillis();
            long deadline = currentTime + getKeepAliveTime(TimeUnit.MILLISECONDS);

            for (; ; ) {
                try {
                    long waitTime = deadline - currentTime;

                    if (waitTime <= 0) {
                        break;
                    }

                    try {
                        session = waitConditionStrategy.waitFor(this, waitTime, TimeUnit.MILLISECONDS);
                        //session = waitingSessions.poll(waitTime, TimeUnit.MILLISECONDS);
                        break;
                    } finally {
                        if (session != null) {
                            currentTime = System.currentTimeMillis();
                        }
                    }
                } catch (InterruptedException e) {
                    // Ignore.
                    continue;
                }
            }

            return session;
        }

        private void runTasks(OrderedThreadSession session) {
            int runTaskCount = session.getRunTaskCount();
            for (; ; ) {
                //if(runTaskCount--==0){
                //    break;
                //}
                Runnable task;
                Queue<Runnable> tasksQueue = session.getQueue();

                task = tasksQueue.poll();

                if (task == null) {
                    session.getProcessingCompleted().set(true);
                    break;
                }

                //判断任务之后判断队列是否还有任务
                //if(!session.getQueue().isEmpty()){
                //    offerWaitSession(session);
                //}
                runTask(task);
            }
        }

        private void runTask(Runnable task) {
            beforeExecute(thread, task);
            boolean ran = false;
            try {
                task.run();
                ran = true;
                afterExecute(task, null);
                completedTaskCount.incrementAndGet();
            } catch (RuntimeException e) {
                if (!ran) {
                    afterExecute(task, e);
                }
                throw e;
            }
        }

        @Override
        public OrderedThreadSession getAttach() {
            return waitingSessions.poll();
        }
    }


}