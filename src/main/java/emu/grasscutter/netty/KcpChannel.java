package emu.grasscutter.netty;

import emu.grasscutter.Grasscutter;
import io.jpower.kcp.netty.UkcpChannel;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public abstract class KcpChannel extends ChannelInboundHandlerAdapter {
	private UkcpChannel kcpChannel;
	private ChannelHandlerContext ctx;
	private boolean isActive;
	
    public UkcpChannel getChannel() {
		return kcpChannel;
	}
    
    public boolean isActive() {
    	return this.isActive;
    }

	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.kcpChannel = (UkcpChannel) ctx.channel();
        this.ctx = ctx;
        this.isActive = true;
       
        this.onConnect();
    }
	
	@Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		this.isActive = false;
		
        this.onDisconnect();
    }

	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	ByteBuf data = (ByteBuf) msg;
    	onMessage(ctx, data);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        close();
    }

    protected void send(byte[] data) {
    	if (!isActive()) {
    		return;
    	}
    	ByteBuf packet = Unpooled.wrappedBuffer(data);
    	kcpChannel.writeAndFlush(packet);
    }
    
    public void close() {
    	if (getChannel() != null) {
    		getChannel().close();
    	}
    }

    /*
    protected void logPacket(ByteBuffer buf) {
		ByteBuf b = Unpooled.wrappedBuffer(buf.array());
    	logPacket(b);
    } 
    */
    
    protected void logPacket(ByteBuf buf) {
    	Grasscutter.getLogger().info("Received: \n" + ByteBufUtil.prettyHexDump(buf));
    }
    
    // Events

	protected abstract void onConnect();

    protected abstract void onDisconnect();
    
    public abstract void onMessage(ChannelHandlerContext ctx, ByteBuf data);
}
