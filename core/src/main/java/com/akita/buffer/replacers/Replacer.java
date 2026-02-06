package com.akita.buffer.replacers;

import com.akita.buffer.BufferPoolManager;
import com.akita.buffer.FrameId;
import com.akita.buffer.PageId;

/**
 * Replacer interface to define a replacement policy for the {@link BufferPoolManager}
 */
public interface Replacer {
    /**
     *
     * @return The number of evictable frames in this replacer
     */
    int size();

    void setEvictable(FrameId frameId, boolean evictable);

    void recordAccess(FrameId frameId, PageId pageId);

    FrameId evict();

    void remove(FrameId frameId);
}
