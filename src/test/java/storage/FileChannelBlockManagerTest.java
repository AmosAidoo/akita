package storage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.CleanupMode;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class FileChannelBlockManagerTest {
    @TempDir(cleanup = CleanupMode.ON_SUCCESS)
    private static Path tempDir;
    private static FileChannelVFS fileChannelVFS;
    private static BlockManager blockManager;
    private static ContainerManager containerManager;

    @BeforeAll
    static void setUp() {
        fileChannelVFS = FileChannelVFS.create(tempDir);
        blockManager = FileChannelBlockManager.create(fileChannelVFS);
        containerManager = FileChannelContainerManager.create(fileChannelVFS);
    }

    @Test
    void allocatesCorrectBlockSize() throws IOException {
        ContainerId containerId = containerManager.createContainer();
        VFSFile file = fileChannelVFS.open(containerId);

        blockManager.allocateBlock(containerId, 0);
        assertThat(file.size()).isEqualTo(BlockManager.BLOCK_SIZE);

        blockManager.allocateBlock(containerId, 9);
        assertThat(file.size()).isEqualTo(10 * BlockManager.BLOCK_SIZE);
    }

//    @Test
//    void writesBlockToDiskWhenContainerExists() {}
//
//    @Test
//    void readsBlockFromDiskWhenContainerAndBlockExists() {}
//
//    @Test
//    void allocatesBlockImplicitlyWhenBlockDoesntExist() {}
}