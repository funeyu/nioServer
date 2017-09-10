package com.fuheryu.core.handler;

/**
 * Created by fuheyu on 2017/9/9.
 */
public final class RingBuffer<E> {

    private final int bufferSize;

    private final Object[] entries;

    // 标识生产者所在的Sequence
    private final Sequence cursor;

    // 标识消费者最前的Sequence
    private final Sequence leading;

    RingBuffer(int size) {

        this.bufferSize = size;
        this.cursor = new Sequence();
        this.leading = new Sequence();
        entries = new Object[size];

    }

    public long getCurrentCursor() {

        return cursor.get();
    }

    private E getEntry(long l) {


        int index = (int)(l % bufferSize);
        return (E)entries[index];
    }

    /**
     * 获取RingBuffer上的条目
     * @return
     */
    public E haltForEntry() {

        long gotJobIndex = leading.skipAndGet(cursor);
        if(gotJobIndex == -1L) {
            return null;
        }

        return getEntry(gotJobIndex);
    }

    /**
     * 返回RingBuffer的生产者最新的Job 序列号
     * @return
     */
    public long getLeading() {

        return leading.get();
    }

    private void addEntry(E e) {

        long l = this.cursor.increase();
        System.out.println("addEntry:" + l + e.toString());
        int index = (int)(l % bufferSize);
        entries[index] = e;
    }

    /**
     * 往RingBuffer里添加条目
     * @param e
     */
    public boolean addHaltEntry(E e) {

        long leading = getLeading();
        long cursor = getCurrentCursor();

        // 标识添加job太快，workers也满负荷，这时候直接返回false
        if(cursor >= (leading + bufferSize)) {
            return false;
        }

        addEntry(e);
        return true;
    }

}
