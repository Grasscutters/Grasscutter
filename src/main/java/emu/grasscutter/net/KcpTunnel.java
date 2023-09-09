package emu.grasscutter.net;

import java.net.InetSocketAddress;

public interface KcpTunnel {
    /**
     * @return The address of the client.
     */
    InetSocketAddress getAddress();

    /**
     * Sends bytes to the client.
     *
     * @param bytes The bytes to send.
     */
    void writeData(byte[] bytes);

    /**
     * Closes the connection.
     */
    void close();
}
