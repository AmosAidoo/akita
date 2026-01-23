package buffer;

import java.nio.ByteBuffer;
import java.util.concurrent.Future;

public interface DiskScheduler {

    Future<ByteBuffer> schedulePageRead(PageId pageId);

    void schedulePageWrite(PageId pageId, ByteBuffer buffer);
}
