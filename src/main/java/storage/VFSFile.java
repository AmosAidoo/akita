package storage;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface VFSFile {
    int read(ByteBuffer dst, long offset) throws IOException;

    int write(ByteBuffer src, long offset) throws IOException;

    long size() throws IOException;

    void close() throws IOException;
}
