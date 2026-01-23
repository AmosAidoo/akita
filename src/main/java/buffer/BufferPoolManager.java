package buffer;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * BufferPoolManager is responsible for fetching blocks from the storage
 * device with the {@link DiskScheduler}
 */
public class BufferPoolManager {
    private final long framesCount;
    private final DiskScheduler diskScheduler;
    private final Replacer replacer;
    private final CopyOnWriteArrayList<Frame> frames = new CopyOnWriteArrayList<>();
    private final ConcurrentMap<PageId, Integer> pageTable;

    private BufferPoolManager(int framesCount, DiskScheduler diskScheduler, Replacer replacer) {
        this.diskScheduler = diskScheduler;
        this.replacer = replacer;
        this.framesCount = framesCount;
        this.pageTable = new ConcurrentHashMap<>(framesCount);
    }

    public static BufferPoolManager create(int framesCount, DiskScheduler diskScheduler, Replacer replacer) {
        return new BufferPoolManager(framesCount, diskScheduler, replacer);
    }

    public void readPage(PageId pageId) {
        var future = diskScheduler.schedulePageRead(pageId);
    }

    public void writePage(PageId pageId, ByteBuffer buffer) {
        diskScheduler.schedulePageWrite(pageId, buffer);
    }
}
