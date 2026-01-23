package buffer;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Frame represents a single slot in the {@link BufferPoolManager}
 */
public class Frame {
    private final int FRAME_SIZE = 8192;
    private final int frameId;
    private boolean isDirty;
    private final AtomicInteger pinCount = new AtomicInteger(0);
    private final ByteBuffer data = ByteBuffer.allocate(FRAME_SIZE);

    private Frame(int frameId) {
        this.frameId = frameId;
    }

    public static Frame create(int frameId) {
        return new Frame(frameId);
    }
}
