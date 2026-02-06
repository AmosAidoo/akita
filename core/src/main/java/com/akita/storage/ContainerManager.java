package com.akita.storage;

import java.io.IOException;

/**
 * ContainerManager specifies an API to create and manage physical containers
 * on some storage device
 *
 * @author Amos Aidoo
 */
public interface ContainerManager {
    /**
     * Creates a new container on a storage device
     *
     * @return id of newly created container
     */
    ContainerId createContainer() throws IOException;
}
