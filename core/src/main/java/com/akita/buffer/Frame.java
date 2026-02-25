package com.akita.buffer;

import java.nio.ByteBuffer;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Frame represents a single slot in the {@link BufferPoolManager}
 */
public class Frame {
    private final ReentrantReadWriteLock readWriteLatch = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLatch = readWriteLatch.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLatch = readWriteLatch.writeLock();

    private final int FRAME_SIZE = 8192;
    private final FrameId frameId;
    private volatile boolean isDirty = false;
    private volatile Future<?> pendingWrite;
    private final AtomicInteger pinCount = new AtomicInteger(0);
    private final ByteBuffer data = ByteBuffer.allocate(FRAME_SIZE);
    private PageId pageId = null;

    private Frame(FrameId frameId) {
        this.frameId = frameId;
    }

    public static Frame create(FrameId frameId) {
        return new Frame(frameId);
    }

    public ReentrantReadWriteLock.ReadLock getReadLatch() {
        return readLatch;
    }

    public ReentrantReadWriteLock.WriteLock getWriteLatch() {
        return writeLatch;
    }

    public Future<?> getPendingWrite() {
        return pendingWrite;
    }
    public void setPendingWrite(Future<?> future) {
        pendingWrite = future;
    }

    public FrameId getFrameId() {
        return frameId;
    }

    public void setIsDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }

    public boolean getIsDirty() {
        return isDirty;
    }

    public ByteBuffer getReadOnlyData() {
        data.clear();
        return data.asReadOnlyBuffer();
    }

    public ByteBuffer getData() {
        data.clear();
        return data;
    }

    public PageId getPageId() {
        return pageId;
    }

    public Frame setPageId(PageId pageId) {
        this.pageId = pageId;
        return this;
    }

    public boolean isEvictable() {
        return pinCount.intValue() == 0;
    }

    public void pin() {
        pinCount.incrementAndGet();
    }

    public void unpin() {
        pinCount.decrementAndGet();
    }

    public int getPinCount() {
        return pinCount.get();
    }

    @Override
    public String toString() {
        return "Frame{" +
                "frameId=" + frameId +
                "isDirty=" + isDirty +
                ", pinCount=" + pinCount +
                ", data=" + data +
                ", pageId=" + pageId +
                '}';
    }
}
