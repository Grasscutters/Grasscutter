package emu.grasscutter.server.game.session;

import emu.grasscutter.net.KcpTunnel;
import io.netty.buffer.Unpooled;
import kcp.highway.Ukcp;
import lombok.*;

import java.net.InetSocketAddress;

@RequiredArgsConstructor
public final class GameSessionHandler implements KcpTunnel {
    @Getter private final Ukcp handle;

    @Override
    public InetSocketAddress getAddress() {
        return this.getHandle().user().getRemoteAddress();
    }

    @Override
    public void writeData(byte[] bytes) {
        var buffer = Unpooled.wrappedBuffer(bytes);
        this.getHandle().write(buffer);

        buffer.release();
    }

    @Override
    public void close() {
        this.getHandle().close();
    }
}
