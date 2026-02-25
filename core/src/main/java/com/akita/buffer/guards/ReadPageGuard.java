package com.akita.buffer.guards;

import com.akita.buffer.DiskScheduler;
import com.akita.buffer.Frame;
import com.akita.buffer.PageId;

import java.nio.ByteBuffer;

public class ReadPageGuard implements AutoCloseable {
    private final PageId pageId;
    private final Frame frame;

    private ReadPageGuard(PageId pageId, Frame frame) {
        this.pageId = pageId;
        this.frame = frame;
    }

    public static ReadPageGuard create(PageId pageId, Frame frame) {
        frame.getReadLatch().lock();
        frame.pin();
        return new ReadPageGuard(pageId, frame);
    }

    public PageId getPageId() {
        return pageId;
    }

    public ByteBuffer getData() {
        return frame.getReadOnlyData();
    }

    public boolean isDirty() {
        return frame.getIsDirty();
    }

    @Override
    public void close() {
        frame.unpin();
        frame.getReadLatch().unlock();
    }
}
