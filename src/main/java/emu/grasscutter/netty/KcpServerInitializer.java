package emu.grasscutter.netty;

import io.jpower.kcp.netty.UkcpChannel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

@SuppressWarnings("unused")
public class KcpServerInitializer extends ChannelInitializer<UkcpChannel> {
	
	@Override
	protected void initChannel(UkcpChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
	}

}
