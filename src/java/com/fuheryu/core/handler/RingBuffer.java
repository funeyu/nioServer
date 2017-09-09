package com.fuheryu.core.handler;

/**
 * Created by fuheyu on 2017/9/9.
 */
public final class RingBuffer<E> {

    private static int INITIAL_VALUE = 0;

    private final int bufferSize;

    private final Object[] entries;

    private final Sequence sequence;

    RingBuffer(EventFactory<E> factory, int size, Sequence sequence) {

        this.bufferSize = size;
        this.sequence = sequence;
        entries = new Object[size];

    }

    public long getCurrentCursor() {

        return sequence.get();
    }

    public E getEntry(long l) {

        int index = (int)(l % bufferSize);
        return (E)entries[index];
    }


}
