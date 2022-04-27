package emu.grasscutter.netty;

import java.net.InetSocketAddress;

import emu.grasscutter.Grasscutter;
import io.jpower.kcp.netty.ChannelOptionHelper;
import io.jpower.kcp.netty.UkcpChannelOption;
import io.jpower.kcp.netty.UkcpServerChannel;
import io.netty.bootstrap.UkcpServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

@SuppressWarnings("rawtypes")
public class KcpServer extends Thread {
	private EventLoopGroup group;
	private UkcpServerBootstrap bootstrap;
	
	private ChannelInitializer serverInitializer;
	private InetSocketAddress address;
	 
	public KcpServer(InetSocketAddress address) {
    	this.address = address;
    	this.setName("Netty Server Thread");
    }
	
	public InetSocketAddress getAddress() {
    	return this.address;
    }

    public ChannelInitializer getServerInitializer() {
		return serverInitializer;
	}

	public void setServerInitializer(ChannelInitializer serverInitializer) {
		this.serverInitializer = serverInitializer;
	}

	@Override
	public void run() {
		if (getServerInitializer() == null) {
			this.setServerInitializer(new KcpServerInitializer());
		}
		
        try {
        	group = new NioEventLoopGroup();
            bootstrap = new UkcpServerBootstrap();
            bootstrap.group(group)
                    .channel(UkcpServerChannel.class)
                    .childHandler(this.getServerInitializer());
            ChannelOptionHelper
            	.nodelay(bootstrap, true, 20, 2, true)
            	.childOption(UkcpChannelOption.UKCP_MTU, 1200);
            
            // Start handler
            this.onStart();
            
            // Start the server.
            ChannelFuture f = bootstrap.bind(getAddress()).sync();
            
            // Start finish handler
            this.onStartFinish();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } catch (Exception exception) {
			Grasscutter.getLogger().error("Unable to start game server.", exception);
		} finally {
        	// Close
			finish();
        }
	}
	
	public void onStart() {

	}

	public void onStartFinish() {

	}
	
	private void finish() {
		try {
			group.shutdownGracefully();
		} catch (Exception e) {
			
		}
		Grasscutter.getLogger().info("Game Server closed");
	}
}


