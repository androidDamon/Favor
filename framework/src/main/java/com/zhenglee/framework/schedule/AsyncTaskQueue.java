package com.zhenglee.framework.schedule;

import android.content.Context;
import android.os.AsyncTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhenglee on 15/10/21.
 */
public class AsyncTaskQueue {

    private static final Logger logger = LoggerFactory.getLogger(AsyncTaskQueue.class);

    private static ThreadFactory THREAD_FACTORY = new ThreadFactory() {

        private final AtomicInteger count = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + this.count.getAndIncrement());
        }
    };

    private static final class SerialExecutor implements Executor{

        private final Deque<Runnable> tasks = new LinkedList<Runnable>();

        private Runnable active;

        private AsyncTaskQueue queue;

        public SerialExecutor(AsyncTaskQueue queue){
            this.queue = queue;
        }

        @Override
        public synchronized void execute(final Runnable command) {
            this.tasks.offer(new Runnable() {
                @Override
                public void run() {
                    try {
                        command.run();
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    } finally {
                    }
                }
            });
        }

        protected synchronized void scheduleNext() {
            if ((this.active = this.tasks.poll()) != null) {
                try {
                    this.queue.scheduledExecutor.execute(this.active);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        protected synchronized void cancaelAll(Object object){
            this.tasks.clear();
        }
    }

    private final SerialExecutor serialExecutor;

    private final ScheduledExecutorService scheduledExecutor;

    AsyncTaskQueue(Context ctx) {
        this.serialExecutor = new SerialExecutor(this);
        this.scheduledExecutor = Executors.newSingleThreadScheduledExecutor(THREAD_FACTORY);
    }

    public <Params,Progress,Result>AsyncTask<Params,Progress,Result> add(AsyncTask<Params,Progress,Result> task,Params... params){
        return task.executeOnExecutor(this.serialExecutor,params);
    }

    public void cancelAll(Object object){
        this.serialExecutor.cancaelAll(object);
    }

    public void stop() {
        this.cancelAll(null);
        this.scheduledExecutor.shutdown();
    }

}
