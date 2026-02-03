package buffer;

import java.nio.ByteBuffer;

/**
 * Frame represents a single slot in the {@link BufferPoolManager}
 */
public class Frame {
    private final int FRAME_SIZE = 8192;
    private final FrameId frameId;
    private boolean isDirty = false;
    private int pinCount = 0;
    private final ByteBuffer data = ByteBuffer.allocate(FRAME_SIZE);
    private PageId pageId = null;

    private Frame(FrameId frameId) {
        this.frameId = frameId;
    }

    public static Frame create(FrameId frameId) {
        return new Frame(frameId);
    }

    public void setIsDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }

    public ByteBuffer getData() {
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
        return pinCount == 0;
    }

    public void pin() {
        pinCount++;
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
