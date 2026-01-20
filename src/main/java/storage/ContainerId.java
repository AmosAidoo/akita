package storage;

import java.util.Objects;

/**
 * ContainerId represents the id for physical containers persisted on
 * a storage medium
 */
public class ContainerId {
    private final long value;

    private ContainerId(long value) {
        this.value = value;
    }

    public static ContainerId valueOf(long value) {
        return new ContainerId(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ContainerId that = (ContainerId) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
