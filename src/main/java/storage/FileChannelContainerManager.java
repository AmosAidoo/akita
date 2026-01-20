package storage;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class FileChannelContainerManager implements ContainerManager {
    private final FileChannelVFS vfs;

    private FileChannelContainerManager(FileChannelVFS vfs) {
        this.vfs = vfs;
    }

    public static FileChannelContainerManager create(FileChannelVFS vfs) {
        return new FileChannelContainerManager(vfs);
    }

    @Override
    public ContainerId createContainer() throws IOException {
        // TODO: Generate container id based on a global value
        ContainerId containerId = ContainerId.valueOf(ThreadLocalRandom.current().nextLong());
        vfs.open(containerId, OpenMode.READ, OpenMode.WRITE, OpenMode.CREATE);
        return containerId;
    }
}
