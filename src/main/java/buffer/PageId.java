package buffer;

import storage.ContainerId;

public record PageId(ContainerId containerId, long blockNumber) {
}
