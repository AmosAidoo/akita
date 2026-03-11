package com.akita.buffer;

import com.akita.buffer.guards.ReadPageGuard;
import com.akita.buffer.guards.WritePageGuard;
import com.akita.buffer.replacers.arc.ArcReplacer;
import com.akita.storage.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.CleanupMode;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;


import static org.assertj.core.api.Assertions.assertThat;

class BufferPoolManagerTest {
    @TempDir(cleanup = CleanupMode.ON_SUCCESS)
    private static Path tempDir;
    private static BlockManager blockManager;
    private static ContainerManager containerManager;
    private final int FRAMES = 10; // Number of frames

    @BeforeEach
    void setUp() {
        FileChannelVFS fileChannelVFS = FileChannelVFS.create(tempDir);
        blockManager = FileChannelBlockManager.create(fileChannelVFS);
        containerManager = FileChannelContainerManager.create(fileChannelVFS);
    }

    @Test
    void veryBasicTest() throws IOException, ExecutionException, InterruptedException {
        ContainerId containerId = containerManager.createContainer();
        Map<FrameId, Frame> frames = new HashMap<>();
        for (int i = 0; i < FRAMES; i++) {
            FrameId frameId = new FrameId(i);
            frames.put(frameId, Frame.create(frameId));
        }

        BufferPoolManager bpm = BufferPoolManager.create(
                FCFSDiskScheduler.create(Executors.newSingleThreadExecutor(), blockManager),
                ArcReplacer.create(FRAMES),
                frames,
                new HashMap<>()
        );

        PageId pageId = new PageId(containerId, 0);
        final String expected = "Hello world";

        // Check WritePageGuard for basic functionality
        try (WritePageGuard writePageGuard = bpm.writePage(pageId)) {
            writePageGuard.getData().put(expected.getBytes(StandardCharsets.UTF_8));

            byte[] dst = new byte[expected.length()];
            writePageGuard.getData().get(dst);
            assertThat(new String(dst, StandardCharsets.UTF_8)).isEqualTo(expected);
        }

        // Check ReadPageGuard for basic functionality
        try (ReadPageGuard readPageGuard = bpm.readPage(pageId)) {
            byte[] dst = new byte[expected.length()];
            readPageGuard.getData().get(dst);
            assertThat(new String(dst, StandardCharsets.UTF_8)).isEqualTo(expected);
        }

        // Check ReadPageGuard for basic functionality again
        try (ReadPageGuard readPageGuard = bpm.readPage(pageId)) {
            byte[] dst = new byte[expected.length()];
            readPageGuard.getData().get(dst);
            assertThat(new String(dst, StandardCharsets.UTF_8)).isEqualTo(expected);
        }

        assertThat(bpm.deletePage(pageId)).isTrue();
    }
}