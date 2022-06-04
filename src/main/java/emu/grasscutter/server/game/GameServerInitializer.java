package emu.grasscutter.server.game;

import emu.grasscutter.netty.KcpServerInitializer;
import io.jpower.kcp.netty.UkcpChannel;
import io.netty.channel.ChannelPipeline;

public class GameServerInitializer extends KcpServerInitializer {
	private GameServer server;
	
	public GameServerInitializer(GameServer server) {
		this.server = server;
	}
	
    @Override
    protected void initChannel(UkcpChannel ch) throws Exception {
        new GameSession(server,ch.pipeline());
    }
}
