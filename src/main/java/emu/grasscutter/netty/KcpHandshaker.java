package emu.grasscutter.netty;

import java.net.SocketAddress;
import java.nio.channels.SelectableChannel;
import java.util.List;

import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.nio.AbstractNioMessageChannel;

public class KcpHandshaker extends AbstractNioMessageChannel {

	protected KcpHandshaker(Channel parent, SelectableChannel ch, int readInterestOp) {
		super(parent, ch, readInterestOp);
	}

	@Override
	public ChannelConfig config() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ChannelMetadata metadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int doReadMessages(List<Object> buf) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void doFinishConnect() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected SocketAddress localAddress0() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected SocketAddress remoteAddress0() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void doBind(SocketAddress localAddress) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doDisconnect() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
