package storage;

import java.io.IOException;
import java.nio.file.Path;

public class FileChannelVFS implements VFS {
    private final Path basePath;

    private FileChannelVFS(Path basePath) {
        this.basePath = basePath;
    }

    public static FileChannelVFS create(Path basePath) {
        return new FileChannelVFS(basePath);
    }

    @Override
    public FileChannelVFSFile open(ContainerId containerId, OpenMode... openModes) throws IOException {
        Path file = basePath.resolve(containerId.toString());
        return FileChannelVFSFile.create(file, openModes);
    }
}
