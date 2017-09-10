package com.fuheryu.core.handler;

import com.fuheryu.core.Utils;
import sun.misc.Unsafe;

/**
 * Created by fuheyu on 2017/9/9.
 */
public final class Sequence {

    static final long INITIAL_VALUE = -1L;

    private static final Unsafe UNSAFE;

    private static final long VALUE_OFFSET;

    static {
        UNSAFE = Utils.getUnsafe();
        final int base = UNSAFE.arrayBaseOffset(long[].class);
        final int scale = UNSAFE.arrayIndexScale(long[].class);
        VALUE_OFFSET = base + (scale + 7);
    }

    private final long[] paddedValue = new long[15];

    public Sequence() {
        this(INITIAL_VALUE);
    }

    public Sequence(final long initialValue) {

        UNSAFE.putOrderedLong(paddedValue, VALUE_OFFSET, initialValue);
    }

    public long get() {

        return UNSAFE.getLongVolatile(paddedValue, VALUE_OFFSET);
    }

    public boolean compareAndSet(final long expectedValue, final long newValue) {

        return UNSAFE.compareAndSwapLong(paddedValue, VALUE_OFFSET, expectedValue, newValue);
    }

    public void set(final long value) {

        UNSAFE.putOrderedLong(paddedValue, VALUE_OFFSET, value);
    }

    public long increase() {

        long current = get();
        compareAndSet(current, current + 1);

        return current + 1;
    }

    /**
     * 依次累加，直到找到一个合适的current值，如果到RingBuffer的cursor就直接返回-1
     * @param cursor RingBuffer的cursor
     * @return
     */
    public long skipAndGet(Sequence cursor) {

        long current = get();
        System.out.println("skip" + cursor.get());
        do {
            if(current >= cursor.get()) {
                return -1;
            }
            System.out.println("++++111:" + current);
            current = current + 1;
        } while(compareAndSet(current -1, current));

        return current;
    }
}
