package com.akita.buffer;

import com.akita.storage.ContainerId;

public record PageId(ContainerId containerId, long blockNumber) {
}
