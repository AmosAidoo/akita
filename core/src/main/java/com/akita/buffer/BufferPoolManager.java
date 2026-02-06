package com.akita.buffer;

import com.akita.buffer.replacers.Replacer;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * BufferPoolManager is responsible for fetching blocks from the storage
 * device with the {@link DiskScheduler}
 */
public class BufferPoolManager {
    private final ReentrantLock latch = new ReentrantLock();
    private final DiskScheduler diskScheduler;
    private final Replacer replacer;
    private final Queue<Frame> freeFrames;
    private final Map<PageId, Frame> pageTable;

    private BufferPoolManager(DiskScheduler diskScheduler, Replacer replacer, List<Frame> frames, Map<PageId, Frame> pageTable) {
        this.diskScheduler = diskScheduler;
        this.replacer = replacer;
        this.pageTable = pageTable;
        this.freeFrames = new LinkedList<>(frames);
    }

    public static BufferPoolManager create(DiskScheduler diskScheduler, Replacer replacer, List<Frame> frames, Map<PageId, Frame> pageTable) {
        return new BufferPoolManager(diskScheduler, replacer, frames, pageTable);
    }

    public Frame readPage(PageId pageId) throws InterruptedException {
        return null;
    }

    public void writePage(PageId pageId, ByteBuffer buffer) {

    }
}
