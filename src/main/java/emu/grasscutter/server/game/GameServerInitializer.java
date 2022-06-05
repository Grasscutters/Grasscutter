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
        ChannelPipeline pipeline=null;
        if(ch!=null){
            pipeline = ch.pipeline();
        }
        new GameSession(server,pipeline);
    }
}
