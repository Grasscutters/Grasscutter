package io.grasscutter.virtual;

import emu.grasscutter.net.KcpTunnel;
import java.net.InetSocketAddress;

public final class VirtualKcpTunnel implements KcpTunnel {
    @Override
    public InetSocketAddress getAddress() {
        return new InetSocketAddress(1000);
    }

    @Override
    public void writeData(byte[] bytes) {
        throw new UnsupportedOperationException("Cannot write to a virtual KCP tunnel");
    }

    @Override
    public void close() {
        System.exit(0);
    }
}
