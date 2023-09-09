package emu.grasscutter.net;

public interface KcpChannel {
    /**
     * Event fired when the client connects.
     *
     * @param tunnel The tunnel.
     */
    void onConnected(KcpTunnel tunnel);

    /**
     * Event fired when the client disconnects.
     */
    void onDisconnected();

    /**
     * Event fired when data is received from the client.
     *
     * @param bytes The data received.
     */
    void onMessage(byte[] bytes);
}
