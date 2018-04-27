package com.fuheryu.futty.future.impl;

import com.fuheryu.futty.future.ChannelFuture;
import com.fuheryu.futty.future.ChannelFutureListener;
import com.fuheryu.futty.future.ChannelFutureProgressListener;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by fuheyu on 2018/4/26.
 */
public class DefaultChannelFuture implements ChannelFuture{

    private static final Throwable CANCELLED = new Throwable();

    private static volatile boolean useDeadLockChecker = true;
    private static boolean disabledDeadLockCheckerOnce;

    public static boolean isUseDeadLockChecker() {
        return useDeadLockChecker;
    }

    public static void setUseDeadLockChecker(boolean useDeadLockChecker) {

        DefaultChannelFuture.useDeadLockChecker = useDeadLockChecker;
    }

    private final Channel channel;
    private final boolean cancellable;

    private ChannelFutureListener firstListener;
    private List<ChannelFutureListener> otherListeners;
    private List<ChannelFutureProgressListener> progressListeners;
    private boolean done;
    private Throwable cause;
    private int waiters;

    public DefaultChannelFuture(Channel channel, boolean cancellable) {
        this.channel = channel;
        this.cancellable = cancellable;
    }

    public Channel getChannel() {
        return channel;
    }

    public synchronized boolean isDone() {
        return done;
    }

    public synchronized boolean isSuccess() {
        return done && cause == null;
    }

    public synchronized Throwable getCause() {
        if(cause != CANCELLED) {
            return cause;
        } else {
            return null;
        }
    }

    public synchronized boolean isCancelled() {
        return cause == CANCELLED;
    }

    public void addListener(ChannelFutureListener listener) {
        if(listener == null) {
            throw new NullPointerException("listener");
        }

        boolean notifyNow = false;
        synchronized (this) {
            if(done) {
                notifyNow = true;
            } else {
                if(firstListener == null) {
                    firstListener = listener;
                } else {
                    if(otherListeners == null) {
                        otherListeners = new ArrayList<ChannelFutureListener>(1);
                    }
                    otherListeners.add(listener);
                }

                if(listener instanceof ChannelFutureProgressListener) {
                    if(progressListeners == null) {
                        progressListeners = new ArrayList<ChannelFutureProgressListener>(1);
                    }
                    progressListeners.add((ChannelFutureProgressListener) listener);
                }
            }
        }

        if(notifyNow) {
            notifyListener(listener);
        }
    }

    public void removeListener(ChannelFutureListener listener) {
        if(listener == null) {
            throw new NullPointerException("listener");
        }

        synchronized (this) {
            if(!done) {
                if(listener == firstListener) {
                    if(otherListeners != null && !otherListeners.isEmpty()) {
                        firstListener = otherListeners.remove(0);
                    } else {
                        firstListener = null;
                    }
                } else if(otherListeners != null) {
                    otherListeners.remove(listener);
                }

                if(listener instanceof ChannelFutureProgressListener) {
                    progressListeners.remove(listener);
                }
            }
        }
    }

    public ChannelFuture sync() throws InterruptedException {
        await();
        rethrowIfFailed();
        return this;
    }

    public ChannelFuture syncUninterruptibly() {
        awaitUninterruptibly();
        rethrowIfFailed();
        return this;
    }

    private void rethrowIfFailed() {
        Throwable cause = getCause();
        if(cause == null) {
            return;
        }
        if(cause instanceof RuntimeException) {
            throw (RuntimeException) cause;
        }
        if(cause instanceof Error) {
            throw (Error) cause;
        }
        // todo ChannelException(cause)
    }

    public ChannelFuture await() throws InterruptedException {
        if(Thread.interrupted()) {
            throw new InterruptedException();
        }

        synchronized(this) {
            while(!done) {
                checkDeadLock();
                waiters ++;
                try {
                    wait();
                } finally {
                    waiters --;
                }
            }
        }

        return this;
    }

    public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
        return await0(unit.toNanos(timeout), true);
    }

    public boolean await(long timeoutMillis) throws InterruptedException {
        return await0(MILLISECONDS.toNanos(timeoutMillis), true);
    }

    public ChannelFuture awaitUniterruptibly() {
        boolean interrupted = false;
        synchronized (this) {
            while(!done) {

            }
        }

        return this;
    }

    private static void checkDeadLock() {

    }

    private boolean await0(long timeoutNanos, boolean interruptable) throws InterruptedException {
        return false;
    }

    public ChannelFuture awaitUninterruptibly() {
        boolean interrupted = false;
        synchronized (this) {
            while(!done) {
                checkDeadLock();
                waiters ++;
                try {
                    wait();
                } catch (InterruptedException e) {
                    interrupted = true;
                } finally {
                    waiters --;
                }
            }
        }

        if(interrupted) {
            Thread.currentThread().interrupt();
        }

        return this;
    }

    public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
        try {
            return await0(unit.toNanos(timeout), false);
        } catch (InterruptedException e) {
            throw new InternalError();
        }
    }

    public boolean awaitUninterruptibly(long timoutMillis) {
        try {
            return await0(MILLISECONDS.toNanos(timoutMillis), false);
        } catch (InterruptedException e) {
            throw new InternalError();
        }
    }

    public boolean setSuccess() {
        synchronized (this) {
            if(done) {
                return false;
            }

            done = true;
            if(waiters > 0) {
                notifyAll();
            }
        }

        notifyAllListeners();
        return true;
    }

    public boolean setFailure(Throwable cause) {
        if(cause == null) {
            throw new NullPointerException("cause");
        }

        synchronized (this) {
            if(done) {
                return false;
            }

            this.cause = cause;
            done = true;
            if(waiters > 0) {
                notifyAll();
            }
        }

        notifyAllListeners();
        return true;
    }

    public boolean cancel() {
        if(!cancellable) {
            return false;
        }

        synchronized (this) {
            if(done) {
                return false;
            }

            cause = CANCELLED;
            done = true;
            if(waiters > 0) {
                notifyAll();
            }
        }

        notifyAllListeners();
        return true;
    }

    public boolean setProgress(long amount, long current, long total) {
        ChannelFutureProgressListener[] pListeners;
        synchronized (this) {
            if(done) {
                return false;
            }

            Collection<ChannelFutureProgressListener> progressListeners = this.progressListeners;
            if(progressListeners == null || progressListeners.isEmpty()) {
                return true;
            }

            pListeners = progressListeners.toArray(
                    new ChannelFutureProgressListener[progressListeners.size()]);

            for(ChannelFutureProgressListener listener : pListeners) {
                notifyProgressListener(listener, amount, current, total);
            }

            return true;
        }
    }

    private void notifyProgressListener(ChannelFutureProgressListener l,
                                        long amount, long current, long total) {
        try {
            l.operationProgressed(this, amount, current, total);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notifyAllListeners() {
        if(firstListener != null) {
            notifyListener(firstListener);
            firstListener = null;

            if(otherListeners != null) {
                for(ChannelFutureListener listener : otherListeners) {
                    notifyListener(listener);
                }
                otherListeners = null;
            }
        }
    }

    private void notifyListener(ChannelFutureListener listener) {
        try {
            listener.operationComplete(this);
        } catch (Throwable t) {

        }
    }

}
