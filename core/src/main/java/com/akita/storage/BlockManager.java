package com.akita.storage;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * BlockManager specifies an API to read and write blocks to storage.
 *
 * @author Amos Aidoo
 */
public interface BlockManager {
    int BLOCK_SIZE = 8192;

    /**
     * Allocates a block of BLOCK_SIZE in the specified container
     *
     * @param containerId   identifier for container
     * @param blockNumber
     * @throws IOException
     */
    void allocateBlock(ContainerId containerId, long blockNumber) throws IOException;

    /**
     * Writes content of buffer into the specified container at blockNumber
     *
     * @param containerId   Identifier for container to write to
     * @param blockNumber   Target block to write buffer content to
     * @param buffer        The byte content to write
     * @throws IOException
     * @throws IllegalArgumentException
     */
    void writeBlock(ContainerId containerId, long blockNumber, ByteBuffer buffer) throws IOException, IllegalArgumentException;

    /**
     * Reads bytes from container at the specified block into the buffer
     *
     * @param containerId   Identifier for container to read from
     * @param blockNumber   Target block to read buffer content from
     * @param buffer        The byte content to read
     * @throws IOException
     * @throws IllegalArgumentException
     */
    void readBlock(ContainerId containerId, long blockNumber, ByteBuffer buffer) throws IOException, IllegalArgumentException;
}
