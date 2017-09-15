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
     * 依次累加，获取一个待处理的index
     * @return
     */
    private long getIndex() {

        long current;
        do {
            current = leading.get();
            if(current >= cursor.get()) {
                return -1;
            }

        } while(!leading.compareAndSet(current, current + 1));

        return current + 1;
    }

    /**
     * 获取RingBuffer上的条目
     * @return
     */
    public E haltForEntry() {

        long gotJobIndex = getIndex();
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
        int index = (int)(l % bufferSize);
        entries[index] = e;
    }

    /**
     * 判断是生产者否超前一轮圈
     * @return
     */
    private boolean headOneRing() {

        long leading = getLeading();
        long cursor = getCurrentCursor();

        return cursor >= (leading + bufferSize);
    }

    /**
     * 往RingBuffer里添加条目，阻塞住如果RingBuffer上生产者超前消费者一圈
     * @param e
     */
    public void addHaltEntry(E e) {


        while(headOneRing()) {
            // 自旋等待
        }

        addEntry(e);
    }

}
