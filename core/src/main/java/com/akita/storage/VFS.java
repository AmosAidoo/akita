package com.akita.storage;

import java.io.IOException;

/**
 * VFS is a Virtual File System that abstract over the
 * underlying file system
 */
public interface VFS {
    /**
     * Opens a file
     */
    VFSFile open(ContainerId containerId, OpenMode ...openModes) throws IOException;
}
