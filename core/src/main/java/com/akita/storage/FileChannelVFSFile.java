package com.akita.storage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class FileChannelVFSFile implements VFSFile {
    private final FileChannel channel;

    private FileChannelVFSFile(FileChannel channel) {
        this.channel = channel;
    }

    public static FileChannelVFSFile create(Path path, OpenMode ...openModes) throws IOException {
        var fcOpenModes = Arrays.stream(openModes).map((mode) -> switch (mode) {
            case READ -> StandardOpenOption.READ;
            case WRITE -> StandardOpenOption.WRITE;
            case CREATE -> StandardOpenOption.CREATE;
            case CREATE_NEW -> StandardOpenOption.CREATE_NEW;
        }).toArray(StandardOpenOption[]::new);
        return new FileChannelVFSFile(FileChannel.open(path, fcOpenModes));
    }

    @Override
    public int read(ByteBuffer dst, long offset) throws IOException {
        int nread;
        do {
            nread = channel.read(dst, offset);
        } while (nread != -1 && dst.hasRemaining());
        return nread;
    }

    @Override
    public int write(ByteBuffer src, long offset) throws IOException {
        int nwritten;
        do {
            nwritten = channel.write(src, offset);
        } while (src.hasRemaining());
        return nwritten;
    }

    @Override
    public long size() throws IOException {
        return channel.size();
    }

    @Override
    public void close() throws IOException {
        channel.close();
    }
}
