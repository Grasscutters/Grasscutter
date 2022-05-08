package emu.grasscutter.netty;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.Player;
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

        String message = "???";
        String metode = "ZERO";
        
        if(cause != null && cause.getMessage() != null && cause.getMessage().isBlank()) { 
          metode = "1";
          message = cause.getMessage();
        }else if(cause.getCause() != null && cause.getCause().getMessage() != null && cause.getCause().getMessage().isBlank()){
          metode = "2";
          message = cause.getCause().getMessage();
        } else {
          metode = "3";
          StringWriter sw = new StringWriter();
          cause.printStackTrace(new PrintWriter(sw));
          message = sw.toString();
        }

        if(message.matches("(.*)OutOfMemoryError(.*)")){
          Grasscutter.getLogger().info("Trying to exit program because memory is full");
          Map<Integer, Player> playersMap = Grasscutter.getGameServer().getPlayers();
          // Better exit by save data player and kick
          playersMap.values().forEach(player -> {
            Grasscutter.getLogger().info("Kick User: "+player.getUid());
            player.getSession().close();
          });
          // Bye          
          System.exit(0);
        }else{
          Grasscutter.getLogger().error("BIG PROBLEM (C"+metode+"): "+message);
          close();
        }
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
