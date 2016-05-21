package hu.unideb.inf.server.clientConnector;

import hu.unideb.inf.server.Main;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.channel.group.ChannelGroup;

public class ServerHandler extends ChannelInboundMessageHandlerAdapter<String> {

	private static final ChannelGroup channels = ClientService.channels;
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		//Channel incoming = ctx.channel();
		
		channels.add(ctx.channel());
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		//Channel incoming = ctx.channel();
		if(channels.contains(ctx.channel()))
			channels.remove(ctx.channel());
		else {
			Main.clientService.forceQuit(ctx.channel());
		}
	}
	
	public void messageReceived(ChannelHandlerContext arg0, String message) throws Exception {
		Channel incoming = arg0.channel();
		incoming.write(Main.clientService.newMessage(incoming, message)+"\n");
	}
}
